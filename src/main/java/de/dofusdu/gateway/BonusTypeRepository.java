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

import de.dofusdu.dto.BonusTypeMapDTO;
import de.dofusdu.entity.BonusType;
import de.dofusdu.exceptions.LanguageNotFoundException;
import de.dofusdu.util.LanguageHelper;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.ExtractedResult;
import org.hibernate.jpa.QueryHints;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@RequestScoped
public class BonusTypeRepository {

    private EntityManager em;

    @Inject
    public BonusTypeRepository(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public Optional<String> getClosestEntity(String name, String lang) {
        LanguageHelper.Language language = LanguageHelper.getLanguage(lang);
        TypedQuery<String> query;
        switch (language) {
            case ENGLISH: {
                query = this.em.createQuery("SELECT m.nameEn FROM BonusType m", String.class);
                break;
            }
            case GERMAN: {
                query = this.em.createQuery("SELECT m.nameDe FROM BonusType m", String.class);
                break;
            }
            case ITALIAN: {
                query = this.em.createQuery("SELECT m.nameIt FROM BonusType m", String.class);
                break;
            }
            case FRENCH: {
                query = this.em.createQuery("SELECT m.nameFr FROM BonusType m", String.class);
                break;
            }
            case SPANISH: {
                query = this.em.createQuery("SELECT m.nameEs FROM BonusType m", String.class);
                break;
            }
            default: {
                throw new LanguageNotFoundException(language);
            }
        }

        query.setLockMode(LockModeType.PESSIMISTIC_READ);

        List<String> names;
        String res;
        try {
            names = query.getResultList();

            ExtractedResult extractedResult = FuzzySearch.extractOne(name, names);
            res = extractedResult.getString();

        } catch (NoResultException e) {
            return Optional.empty();
        }
        return Optional.of(res);
    }

    @Transactional
    public Collection<BonusTypeMapDTO> bonusTypesDto(String lang) {
        LanguageHelper.Language language = LanguageHelper.getLanguage(lang);
        TypedQuery<BonusType> query = this.em.createQuery("SELECT DISTINCT b FROM BonusType b", BonusType.class);
        query.setLockMode(LockModeType.PESSIMISTIC_READ);
        query.setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false);
        List<BonusType> bonusTypes;
        try {
            bonusTypes = query.getResultList();
        } catch (NoResultException e) {
            return List.of();
        }

        return bonusTypes.stream().map(bonusType -> new BonusTypeMapDTO(bonusType.getName(lang), bonusType.getName("en").toLowerCase().replace(' ', '-'))).collect(Collectors.toList());
    }

    @Transactional
    public Optional<BonusType> findByName(String name, String lang) {
        LanguageHelper.Language language = LanguageHelper.getLanguage(lang);
        TypedQuery<BonusType> query;
        switch (language) {
            case ENGLISH: {
                query = this.em.createQuery("SELECT b FROM BonusType b WHERE b.nameEn = :name", BonusType.class);
                break;
            }
            case GERMAN: {
                query = this.em.createQuery("SELECT b FROM BonusType b WHERE b.nameDe = :name", BonusType.class);
                break;
            }
            case ITALIAN: {
                query = this.em.createQuery("SELECT b FROM BonusType b WHERE b.nameIt = :name", BonusType.class);
                break;
            }
            case FRENCH: {
                query = this.em.createQuery("SELECT b FROM BonusType b WHERE b.nameFr = :name", BonusType.class);
                break;
            }
            case SPANISH: {
                query = this.em.createQuery("SELECT b FROM BonusType b WHERE b.nameEs = :name", BonusType.class);
                break;
            }
            default: {
                throw new LanguageNotFoundException(language);
            }
        }

        query.setLockMode(LockModeType.PESSIMISTIC_READ);
        query.setParameter("name", name);
        BonusType bonusType;
        try {
            bonusType = query.getSingleResult();
        } catch (NoResultException e) {
            return Optional.empty();
        }
        return Optional.of(bonusType);
    }

    @Transactional
    public Optional<BonusType> findEnByDate(Date date) {
        TypedQuery<BonusType> query = this.em.createQuery("SELECT bt FROM Offering o INNER JOIN o.bonus b INNER JOIN b.type bt WHERE o.date = :date", BonusType.class);
        query.setLockMode(LockModeType.PESSIMISTIC_READ);
        query.setParameter("date", date);
        BonusType bonusType;
        try {
            bonusType = query.getSingleResult();
        } catch (NoResultException e) {
            return Optional.empty();
        }
        return Optional.of(bonusType);
    }

    @Transactional
    public Optional<BonusType> findByAlias(String alias) {
        TypedQuery<BonusType> query = this.em.createQuery("SELECT b FROM BonusType b WHERE b.urlAlias = :alias", BonusType.class);
        query.setLockMode(LockModeType.PESSIMISTIC_READ);
        query.setParameter("alias", alias);
        BonusType bonusType;
        try {
            bonusType = query.getSingleResult();
        } catch (NoResultException e) {
            return Optional.empty();
        }
        return Optional.of(bonusType);
    }

    @Transactional
    public List<String> getAllAlias() {
        TypedQuery<BonusType> query = this.em.createQuery("SELECT b FROM BonusType b", BonusType.class);
        query.setLockMode(LockModeType.PESSIMISTIC_READ);
        return query.getResultList().stream().map(BonusType::getUrlAlias).collect(Collectors.toList());
    }

    @Transactional
    public BonusType persistIfNotExistent(BonusType bonusType, String language) {
        Optional<BonusType> byAlias = findByAlias(bonusType.getUrlAlias());

        if (byAlias.isPresent()) {
            return byAlias.get();
        }

        try {
            em.persist(bonusType);
        } catch (Exception e) {
            Optional<String> closestEntity = getClosestEntity(bonusType.getName(language), language);
            if (closestEntity.isEmpty()) {
                throw new NotFoundException();
            }
            Optional<BonusType> byName = findByName(closestEntity.get(), language);
            if (byName.isEmpty()) {
                throw new NotFoundException();
            }
            return byName.get();
        }

        return bonusType;
    }


}
