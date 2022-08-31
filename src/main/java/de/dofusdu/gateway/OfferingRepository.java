/*
 * Copyright 2021 Christopher Sieh (stelzo@steado.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.dofusdu.gateway;

import de.dofusdu.clients.EncObjectSwitch;
import de.dofusdu.dto.CreateOfferingDTO;
import de.dofusdu.dto.OfferingDTO;
import de.dofusdu.entity.Bonus;
import de.dofusdu.entity.BonusType;
import de.dofusdu.entity.Item;
import de.dofusdu.entity.Offering;
import de.dofusdu.exceptions.BonusTypeForDayNotFoundException;
import de.dofusdu.exceptions.BonusTypeNotFoundException;
import de.dofusdu.exceptions.FirstDayNotEnglishException;
import de.dofusdu.util.DateConverter;
import de.dofusdu.util.LanguageHelper;

import org.acme.openapi.api.AllItemsApi;
import org.acme.openapi.api.ConsumablesApi;
import org.acme.openapi.api.EquipmentApi;
import org.acme.openapi.api.ResourcesApi;
import org.acme.openapi.model.ItemsListEntryTyped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestScoped
public class OfferingRepository {

    private final EntityManager em;

    private final BonusTypeRepository bonusTypeRepository;
    private final BonusRepository bonusRepository;
    private final ItemRepository itemRepository;
    private EncObjectSwitch encObjectSwitch;

    @ConfigProperty(name = "search.score.threshold")
    int threshold;

    @Inject
    @RestClient
    AllItemsApi allItemsApi;

    @Inject
    @RestClient
    EquipmentApi equipmentApi;

    @Inject
    @RestClient
    ResourcesApi resourcesApi;

    @Inject
    @RestClient
    ConsumablesApi consumablesApi;

    @Inject
    public OfferingRepository(EntityManager em,
                              BonusTypeRepository bonusTypeRepository,
                              BonusRepository bonusRepository,
                              ItemRepository itemRepository,
                              EncObjectSwitch encObjectSwitch,
                              @RestClient AllItemsApi allItemsApi,
                              @RestClient EquipmentApi equipmentApi,
                              @RestClient ResourcesApi resourcesApi,
                              @RestClient ConsumablesApi consumablesApi) {
        this.em = em;
        this.bonusTypeRepository = bonusTypeRepository;
        this.bonusRepository = bonusRepository;
        this.itemRepository = itemRepository;
        this.allItemsApi = allItemsApi;
        this.equipmentApi = equipmentApi;
        this.resourcesApi = resourcesApi;
        this.consumablesApi = consumablesApi;
        this.encObjectSwitch = encObjectSwitch;
    }


    @Transactional
    public Offering persist(Offering offering, String language) throws BonusTypeForDayNotFoundException {
        // ensure bonusType exists
        BonusType bonusType = bonusTypeRepository.persistIfNotExistent(offering.getBonus().getType(), language);

        // ensure bonus exists
        Bonus nBonus = offering.getBonus();
        nBonus.setType(bonusType);
        Bonus bonus = bonusRepository.persistIfNotExistent(nBonus, language);

        offering.setBonus(bonus);

        // ensure item exists
        Item nItem = offering.getItem();
        Item item = itemRepository.persistIfNotExistent(nItem, language);

        offering.setItem(item);

        // persist now
        em.persist(offering);
        return offering;
    }

    @Transactional
    public void updateTranslation(CreateOfferingDTO offeringDTO) {
        Optional<Offering> alreadyInsertedOffering = singleFromDate(offeringDTO.getDate());

        if (alreadyInsertedOffering.isEmpty()) {
            throw new FirstDayNotEnglishException();
        }

        Offering existingOffering = alreadyInsertedOffering.get();

        /* other languages can be different bonus, english stays
         + so when a language has completely different one, they need the english bonustype
         */
        existingOffering.getItem().setName(offeringDTO.item, offeringDTO.language);
        existingOffering.getBonus().setName(offeringDTO.bonus, offeringDTO.language);
        existingOffering.getBonus().getType().setName(offeringDTO.bonusType, offeringDTO.language);

    }

    @Transactional(value = Transactional.TxType.SUPPORTS)
    public boolean offeringChanged(CreateOfferingDTO newOffering, Offering persistentOffering) {
        String lang = LanguageHelper.getString(LanguageHelper.Language.ENGLISH);
        if (!newOffering.item.equals(persistentOffering.getItem().getName(lang))) {
            return true; // item
        }

        if (!newOffering.bonus.equals(persistentOffering.getBonus().getName(lang))) {
            return true; // bonus
        }

        if (!newOffering.bonusType.equals(persistentOffering.getBonus().getType().getName(lang))) {
            return true; // bonustype
        }

        if (!newOffering.itemQuantity.equals(persistentOffering.getItemQuantity())) {
            return true; // itemQuantity
        }

        return false;
    }

    @Transactional
    public boolean update(CreateOfferingDTO offeringDTO) {
        if (!offeringDTO.language.equals(LanguageHelper.getString(LanguageHelper.Language.ENGLISH))) {
            return false;
        }

        Optional<Offering> alreadyInsertedOffering = singleFromDate(offeringDTO.getDate());
        if (alreadyInsertedOffering.isEmpty()) {
            throw new FirstDayNotEnglishException();
        }
        Offering existingOffering = alreadyInsertedOffering.get();

        boolean changed = this.offeringChanged(offeringDTO, existingOffering);
        if (!changed) {
            return false; // nothing changed so nothing to do here
        }

        // delete the old entries and create new
        this.persist(offeringDTO, LanguageHelper.getString(LanguageHelper.Language.ENGLISH), true);
        return true; // parser must now insert the translations
    }

    /**
     * Only persist if the offering is not the same as last year. If there is nothing from last year, persist it.
     *
     * @param offeringDTO
     */
    @Transactional
    public void persist(CreateOfferingDTO offeringDTO, String language, boolean recreate) {
        List<ItemsListEntryTyped> res;
        String resUrl = "";
        try {
            res = allItemsApi.getItemsAllSearch(offeringDTO.language, offeringDTO.item, null, null, null);
            if (res.isEmpty()) {
                throw new NotFoundException();
            }

            ItemsListEntryTyped item = res.get(0);
            resUrl = "https://api.dofusdu.de/dofus2/" + offeringDTO.language + "/items/" + item.getItemSubtype() + "/" + item.getAnkamaId().toString(); // no need for full url but keep consistent
            offeringDTO.itemPicture = item.getImageUrls().getSd() == null ? item.getImageUrls().getIcon() : item.getImageUrls().getSd(); // override the ankama linked picture
            
        } catch (Exception e) {
            throw new NotFoundException();
        }

        Offering wantsToBeOffering = offeringDTO.toOffering(language, resUrl);
        Optional<Offering> alreadyInsertedOffering = singleFromDate(offeringDTO.getDate());
        if (!recreate) {
            if (alreadyInsertedOffering.isPresent()) {
                throw new FirstDayNotEnglishException();
            }
        } else {
            if (alreadyInsertedOffering.isEmpty()) {
                throw new NotFoundException();
            }

            em.remove(alreadyInsertedOffering.get());
            em.flush();
        }

        persist(wantsToBeOffering, offeringDTO.language);
    }

    @Transactional
    public Optional<String> urlAliasOnDay(LocalDate date) {
        TypedQuery<String> query = this.em.createQuery("SELECT t.urlAlias FROM Offering o INNER JOIN o.bonus b INNER JOIN b.type t WHERE o.date = :date", String.class);
        query.setLockMode(LockModeType.PESSIMISTIC_READ);
        query.setParameter("date", DateConverter.toDate(date));
        String urlAlias;
        try {
            urlAlias = query.getSingleResult();
        } catch (NoResultException e) {
            return Optional.empty();
        }
        return Optional.of(urlAlias);
    }

    public Optional<Offering> singleFromDate(LocalDate date) {
        TypedQuery<Offering> query = this.em.createQuery("SELECT o FROM Offering o WHERE o.date = :date", Offering.class);
        query.setLockMode(LockModeType.PESSIMISTIC_READ);
        query.setParameter("date", DateConverter.toDate(date));
        Offering offering;
        try {
            offering = query.getSingleResult();
        } catch (NoResultException e) {
            return Optional.empty();
        }
        return Optional.of(offering);
    }

    @Transactional
    public Optional<OfferingDTO> singleDTOFromDate(LocalDate date, String language) {

        Optional<Offering> offering = singleFromDate(date);
        if (offering.isEmpty()) {
            return Optional.empty();
        }
        OfferingDTO res = OfferingDTO.from(offering.get(), language, encObjectSwitch.get(offering.get().getItem().getUrl(), language));
        if (res == null) {
            return Optional.empty();
        }

        return Optional.of(res);
    }

    @Transactional
    public List<Offering> nextOfferingWithBonus(LocalDate startDate, LocalDate endDate, String bonusUrlAlias, Integer maxResults) {
        Optional<BonusType> bonusType = bonusTypeRepository.findByAlias(bonusUrlAlias);
        if (bonusType.isEmpty()) {
            throw new BonusTypeNotFoundException(bonusUrlAlias, bonusTypeRepository.getAllAlias());
        }

        TypedQuery<Offering> query = this.em.createQuery("SELECT o FROM Offering o INNER JOIN o.bonus b WHERE (o.date BETWEEN :startDate AND :endDate) AND (b.type = :bonusType) ORDER BY o.date", Offering.class);
        query.setLockMode(LockModeType.PESSIMISTIC_READ);
        query.setParameter("startDate", DateConverter.toDate(startDate));
        query.setParameter("endDate", DateConverter.toDate(endDate));
        query.setParameter("bonusType", bonusType.get());
        if (maxResults != null) {
            query.setMaxResults(maxResults);
        }

        List<Offering> offerings;
        try {
            offerings = query.getResultList();
        } catch (NoResultException e) {
            return List.of();
        }

        return offerings;
    }

    @Transactional
    public Optional<Offering> nextOfferingWithBonus(LocalDate startDate, String bonusUrlAlias) {
        List<Offering> offerings = nextOfferingWithBonus(startDate, startDate.plusYears(1), bonusUrlAlias, 1);
        if (offerings.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(offerings.get(0));
    }


    @Transactional
    public Optional<OfferingDTO> nextOfferingWithBonusDTO(LocalDate startDate, String bonusUrlAlias, String language) {
        Optional<Offering> offeringDTO = nextOfferingWithBonus(startDate, bonusUrlAlias);
        if (offeringDTO.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(OfferingDTO.from(offeringDTO.get(), language, encObjectSwitch.get(offeringDTO.get().getItem().getUrl(), language)));
    }

    @Transactional
    public List<Offering> nextOfferingsWithBonus(LocalDate startDate, LocalDate endDate, String bonusUrlAlias) {
        return nextOfferingWithBonus(startDate, endDate, bonusUrlAlias, null);
    }

    @Transactional
    public List<OfferingDTO> nextOfferingsWithBonusDTO(LocalDate startDate, LocalDate endDate, String bonusUrlAlias, String language) {
        return nextOfferingsWithBonus(startDate, endDate, bonusUrlAlias).stream().map(offering -> OfferingDTO.from(offering, language, encObjectSwitch.get(offering.getItem().getUrl(), language))).collect(Collectors.toList());
    }

    @Transactional
    public List<Offering> listFromDateRange(LocalDate startDate, LocalDate endDate) {
        TypedQuery<Offering> query = this.em.createQuery("SELECT o FROM Offering o WHERE (o.date BETWEEN :startDate AND :endDate)", Offering.class);
        query.setLockMode(LockModeType.PESSIMISTIC_READ);
        query.setParameter("startDate", DateConverter.toDate(startDate));
        query.setParameter("endDate", DateConverter.toDate(endDate));

        List<Offering> offerings;
        try {
            offerings = query.getResultList();
        } catch (NoResultException e) {
            return List.of();
        }

        return offerings;
    }

    @Transactional
    public List<Offering> listFromDateRangeWithBonus(LocalDate startDate, LocalDate endDate, String bonusUrlAlias) {
        Optional<BonusType> bonusType = bonusTypeRepository.findByAlias(bonusUrlAlias);
        if (bonusType.isEmpty()) {
            throw new BonusTypeNotFoundException(bonusUrlAlias, bonusTypeRepository.getAllAlias());
        }
        TypedQuery<Offering> query = this.em.createQuery("SELECT o FROM Offering o INNER JOIN o.bonus b WHERE (o.date BETWEEN :startDate AND :endDate) AND (b.type = :bonusType)", Offering.class);
        query.setLockMode(LockModeType.PESSIMISTIC_READ);
        query.setParameter("startDate", DateConverter.toDate(startDate));
        query.setParameter("endDate", DateConverter.toDate(endDate));
        query.setParameter("bonusType", bonusType.get());

        List<Offering> offerings;
        try {
            offerings = query.getResultList();
        } catch (NoResultException e) {
            return List.of();
        }

        return offerings;
    }

    @Transactional
    public List<OfferingDTO> listFromDateRangeDTO(LocalDate startDate, LocalDate endDate, String language) {
        return listFromDateRange(startDate, endDate).stream().map(offering -> OfferingDTO.from(offering, language, encObjectSwitch.get(offering.getItem().getUrl(), language))).collect(Collectors.toList());
    }

    @Transactional
    public List<OfferingDTO> listFromDateRangeWithBonusDTO(LocalDate startDate, LocalDate endDate, String bonusType, String language) {
        return listFromDateRangeWithBonus(startDate, endDate, bonusType).stream().map(offering -> OfferingDTO.from(offering, language, encObjectSwitch.get(offering.getItem().getUrl(), language))).collect(Collectors.toList());
    }
}
