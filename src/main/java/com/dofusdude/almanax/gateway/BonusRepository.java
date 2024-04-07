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

import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.ExtractedResult;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.dofusdude.almanax.entity.Bonus;
import com.dofusdude.almanax.exceptions.LanguageNotFoundException;
import com.dofusdude.almanax.util.LanguageHelper;

@ApplicationScoped
public class BonusRepository {
    private EntityManager em;

    @Inject
    public BonusRepository(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public Optional<String> getClosestEntity(String name, String lang) {
        LanguageHelper.Language language = LanguageHelper.getLanguage(lang);
        TypedQuery<String> query;
        switch (language) {
            case ENGLISH: {
                query = this.em.createQuery("SELECT m.nameEn FROM Bonus m", String.class);
                break;
            }
            case GERMAN: {
                query = this.em.createQuery("SELECT m.nameDe FROM Bonus m", String.class);
                break;
            }
            case ITALIAN: {
                query = this.em.createQuery("SELECT m.nameIt FROM Bonus m", String.class);
                break;
            }
            case FRENCH: {
                query = this.em.createQuery("SELECT m.nameFr FROM Bonus m", String.class);
                break;
            }
            case SPANISH: {
                query = this.em.createQuery("SELECT m.nameEs FROM Bonus m", String.class);
                break;
            }
            default: {
                throw new LanguageNotFoundException(language);
            }
        }

        query.setLockMode(LockModeType.PESSIMISTIC_READ);

        List<String> names = new ArrayList<>();
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
    public Optional<Bonus> findByName(String name, String lang) {
        LanguageHelper.Language language = LanguageHelper.getLanguage(lang);
        TypedQuery<Bonus> query;
        switch (language) {
            case ENGLISH: {
                query = this.em.createQuery("SELECT b FROM Bonus b WHERE b.nameEn = :name", Bonus.class);
                break;
            }
            case GERMAN: {
                query = this.em.createQuery("SELECT b FROM Bonus b WHERE b.nameDe = :name", Bonus.class);
                break;
            }
            case ITALIAN: {
                query = this.em.createQuery("SELECT b FROM Bonus b WHERE b.nameIt = :name", Bonus.class);
                break;
            }
            case FRENCH: {
                query = this.em.createQuery("SELECT b FROM Bonus b WHERE b.nameFr = :name", Bonus.class);
                break;
            }
            case SPANISH: {
                query = this.em.createQuery("SELECT b FROM Bonus b WHERE b.nameEs = :name", Bonus.class);
                break;
            }
            default: {
                throw new LanguageNotFoundException(language);
            }
        }

        query.setLockMode(LockModeType.PESSIMISTIC_READ);
        query.setParameter("name", name);
        Bonus bonus;
        try {
            bonus = query.getSingleResult();
        } catch (NoResultException e) {
            return Optional.empty();
        }
        return Optional.of(bonus);
    }

    @Transactional
    public Bonus persistIfNotExistent(Bonus bonus, String language) {
        Optional<Bonus> byName = findByName(bonus.getName(language), language);

        if (byName.isPresent()) {
            return byName.get();
        }

        try {
            em.persist(bonus);
        } catch (Exception e) {
            Optional<String> closestEntity = getClosestEntity(bonus.getName(language), language);
            if (closestEntity.isEmpty()) {
                throw new NotFoundException();
            }
            byName = findByName(closestEntity.get(), language);
            if (byName.isEmpty()) {
                throw new NotFoundException();
            }
            return byName.get();
        }

        return bonus;
    }
}
