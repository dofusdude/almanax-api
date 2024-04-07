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

package com.dofusdude.almanax.gateway;

import com.dofusdude.client.ApiClient;
import com.dofusdude.client.Configuration;
import com.dofusdude.client.model.ItemsListEntryTyped;
import com.dofusdude.client.api.GameApi;
import com.dofusdude.almanax.clients.EncObjectSwitch;
import com.dofusdude.almanax.dto.CreateOfferingDTO;
import com.dofusdude.almanax.dto.OfferingDTO;
import com.dofusdude.almanax.dto.OfferingDTOV2;
import com.dofusdude.almanax.entity.Bonus;
import com.dofusdude.almanax.entity.BonusType;
import com.dofusdude.almanax.entity.Item;
import com.dofusdude.almanax.entity.Offering;
import com.dofusdude.almanax.exceptions.BonusTypeForDayNotFoundException;
import com.dofusdude.almanax.exceptions.BonusTypeNotFoundException;
import com.dofusdude.almanax.exceptions.FirstDayNotEnglishException;
import com.dofusdude.almanax.util.DateConverter;
import com.dofusdude.almanax.util.LanguageHelper;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class OfferingRepository {

    private final EntityManager em;

    private final BonusTypeRepository bonusTypeRepository;
    private final BonusRepository bonusRepository;
    private final ItemRepository itemRepository;
    private EncObjectSwitch encObjectSwitch;

    @ConfigProperty(name = "search.score.threshold")
    int threshold;

    @Inject
    public OfferingRepository(EntityManager em,
            BonusTypeRepository bonusTypeRepository,
            BonusRepository bonusRepository,
            ItemRepository itemRepository,
            EncObjectSwitch encObjectSwitch) {
        this.em = em;
        this.bonusTypeRepository = bonusTypeRepository;
        this.bonusRepository = bonusRepository;
        this.itemRepository = itemRepository;
        this.encObjectSwitch = encObjectSwitch;
    }

    private Offering persist(Offering offering, String language) throws BonusTypeForDayNotFoundException {
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

        /*
         * other languages can be different bonus, english stays
         * + so when a language has completely different one, they need the english
         * bonustype
         */
        existingOffering.getItem().setName(offeringDTO.item, offeringDTO.language);
        existingOffering.getBonus().setName(offeringDTO.bonus, offeringDTO.language);
        existingOffering.getBonus().getType().setName(offeringDTO.bonusType, offeringDTO.language);

    }

    private boolean offeringChanged(CreateOfferingDTO newOffering, Offering persistentOffering) {
        String lang = LanguageHelper.getString(LanguageHelper.Language.ENGLISH);
        if (!newOffering.item.equals(persistentOffering.getItem().getName(lang))) {
            return true;
        }

        if (!newOffering.bonus.equals(persistentOffering.getBonus().getName(lang))) {
            return true;
        }

        if (!newOffering.bonusType.equals(persistentOffering.getBonus().getType().getName(lang))) {
            return true;
        }

        if (!newOffering.itemQuantity.equals(persistentOffering.getItemQuantity())) {
            return true;
        }

        if (!newOffering.rewardKamas.equals(persistentOffering.getRewardKamas())) {
            return true;
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
     * Only persist if the offering is not the same as last year. If there is
     * nothing from last year, persist it.
     *
     * @param offeringDTO
     */
    @Transactional
    public void persist(CreateOfferingDTO offeringDTO, String language, boolean recreate) {
        String resUrl;
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        GameApi apiInstance = new GameApi(defaultClient);

        try {
            List<ItemsListEntryTyped> items = apiInstance.getItemsAllSearch(language, "dofus2",
                    offeringDTO.item,
                    null, null, null, null);

            int i = 0;
            ItemsListEntryTyped firstItem = items.get(0);
            while (i < items.size() && items.get(i).getItemSubtype().equals("quest")
                    && items.get(i).getName().equals(firstItem.getName())) {
                i++;
            }

            ItemsListEntryTyped item = items.get(i);
            resUrl = defaultClient.getBasePath() + "/dofus2/" + offeringDTO.language + "/items/" + item.getItemSubtype()
                    + "/"
                    + Integer.toString(item.getAnkamaId());
            offeringDTO.itemPicture = item.getImageUrls().getSd() == null ? item.getImageUrls().getIcon()
                    : item.getImageUrls().getSd(); // override the ankama linked picture

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
        TypedQuery<String> query = this.em.createQuery(
                "SELECT t.urlAlias FROM Offering o INNER JOIN o.bonus b INNER JOIN b.type t WHERE o.date = :date",
                String.class);
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
        TypedQuery<Offering> query = this.em.createQuery("SELECT o FROM Offering o WHERE o.date = :date",
                Offering.class);
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
    public Optional<OfferingDTOV2> singleDTOV2FromDate(LocalDate date, String language) {
        Optional<Offering> offering = singleFromDate(date);
        if (offering.isEmpty()) {
            return Optional.empty();
        }

        OfferingDTOV2 res = OfferingDTOV2.from(offering.get(), language,
                encObjectSwitch.getV2(offering.get().getItem().getUrl(), language));
        if (res == null) {
            return Optional.empty();
        }

        // Optional<Offering> offeringLastYear = singleFromDate(date.minusYears(1));

        return Optional.of(res);
    }

    @Transactional
    public Optional<OfferingDTO> singleDTOFromDate(LocalDate date, String language) {

        Optional<Offering> offering = singleFromDate(date);
        if (offering.isEmpty()) {
            return Optional.empty();
        }
        OfferingDTO res = OfferingDTO.from(offering.get(), language,
                encObjectSwitch.get(offering.get().getItem().getUrl(), language));
        if (res == null) {
            return Optional.empty();
        }

        return Optional.of(res);
    }

    private List<Offering> nextOfferingWithBonus(LocalDate startDate, LocalDate endDate, String bonusUrlAlias,
            Integer maxResults) {
        Optional<BonusType> bonusType = bonusTypeRepository.findByAlias(bonusUrlAlias);
        if (bonusType.isEmpty()) {
            throw new BonusTypeNotFoundException(bonusUrlAlias, bonusTypeRepository.getAllAlias());
        }

        TypedQuery<Offering> query = this.em.createQuery(
                "SELECT o FROM Offering o INNER JOIN o.bonus b WHERE (o.date BETWEEN :startDate AND :endDate) AND (b.type = :bonusType) ORDER BY o.date",
                Offering.class);
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

    private Optional<Offering> nextOfferingWithBonus(LocalDate startDate, String bonusUrlAlias) {
        List<Offering> offerings = nextOfferingWithBonus(startDate, startDate.plusYears(1), bonusUrlAlias, 1);
        if (offerings.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(offerings.get(0));
    }

    @Transactional
    public Optional<OfferingDTOV2> nextOfferingWithBonusDTOV2(LocalDate startDate, String bonusUrlAlias,
            String language) {
        Optional<Offering> offeringDTO = nextOfferingWithBonus(startDate, bonusUrlAlias);
        if (offeringDTO.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(OfferingDTOV2.from(offeringDTO.get(), language,
                encObjectSwitch.getV2(offeringDTO.get().getItem().getUrl(), language)));
    }

    @Transactional
    public Optional<OfferingDTO> nextOfferingWithBonusDTO(LocalDate startDate, String bonusUrlAlias, String language) {
        Optional<Offering> offeringDTO = nextOfferingWithBonus(startDate, bonusUrlAlias);
        if (offeringDTO.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(OfferingDTO.from(offeringDTO.get(), language,
                encObjectSwitch.get(offeringDTO.get().getItem().getUrl(), language)));
    }

    @Transactional
    public List<Offering> nextOfferingsWithBonus(LocalDate startDate, LocalDate endDate, String bonusUrlAlias) {
        return nextOfferingWithBonus(startDate, endDate, bonusUrlAlias, null);
    }

    @Transactional
    public List<OfferingDTO> nextOfferingsWithBonusDTO(LocalDate startDate, LocalDate endDate, String bonusUrlAlias,
            String language) {
        return nextOfferingsWithBonus(startDate, endDate, bonusUrlAlias).stream().map(offering -> OfferingDTO
                .from(offering, language, encObjectSwitch.get(offering.getItem().getUrl(), language)))
                .collect(Collectors.toList());
    }

    private List<Offering> listFromDateRange(LocalDate startDate, LocalDate endDate) {
        TypedQuery<Offering> query = this.em
                .createQuery("SELECT o FROM Offering o WHERE (o.date BETWEEN :startDate AND :endDate)", Offering.class);
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

    private List<Offering> listFromDateRangeWithBonus(LocalDate startDate, LocalDate endDate, String bonusUrlAlias) {
        Optional<BonusType> bonusType = bonusTypeRepository.findByAlias(bonusUrlAlias);
        if (bonusType.isEmpty()) {
            throw new BonusTypeNotFoundException(bonusUrlAlias, bonusTypeRepository.getAllAlias());
        }
        TypedQuery<Offering> query = this.em.createQuery(
                "SELECT o FROM Offering o INNER JOIN o.bonus b WHERE (o.date BETWEEN :startDate AND :endDate) AND (b.type = :bonusType)",
                Offering.class);
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
        return listFromDateRange(startDate, endDate).stream().map(offering -> OfferingDTO.from(offering, language,
                encObjectSwitch.get(offering.getItem().getUrl(), language))).collect(Collectors.toList());
    }

    @Transactional
    public List<OfferingDTOV2> listFromDateRangeDTOV2(LocalDate startDate, LocalDate endDate, String language) {
        return listFromDateRange(startDate, endDate).stream().map(offering -> OfferingDTOV2.from(offering, language,
                encObjectSwitch.getV2(offering.getItem().getUrl(), language))).collect(Collectors.toList());
    }

    @Transactional
    public List<OfferingDTOV2> listFromDateRangeWithBonusDTOV2(LocalDate startDate, LocalDate endDate, String bonusType,
            String language) {
        return listFromDateRangeWithBonus(startDate, endDate, bonusType).stream().map(offering -> OfferingDTOV2
                .from(offering, language, encObjectSwitch.getV2(offering.getItem().getUrl(), language)))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<OfferingDTO> listFromDateRangeWithBonusDTO(LocalDate startDate, LocalDate endDate, String bonusType,
            String language) {
        return listFromDateRangeWithBonus(startDate, endDate, bonusType).stream().map(offering -> OfferingDTO
                .from(offering, language, encObjectSwitch.get(offering.getItem().getUrl(), language)))
                .collect(Collectors.toList());
    }
}
