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

import com.dofusdude.almanax.entity.Item;
import com.dofusdude.almanax.exceptions.LanguageNotFoundException;
import com.dofusdude.almanax.util.LanguageHelper;

@ApplicationScoped
public class ItemRepository {

    private EntityManager em;

    @Inject
    public ItemRepository(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public Optional<String> getClosestEntity(String name, String lang) {
        LanguageHelper.Language language = LanguageHelper.getLanguage(lang);
        TypedQuery<String> query;
        switch (language) {
            case ENGLISH: {
                query = this.em.createQuery("SELECT m.nameEn FROM Item m", String.class);
                break;
            }
            case GERMAN: {
                query = this.em.createQuery("SELECT m.nameDe FROM Item m", String.class);
                break;
            }
            case ITALIAN: {
                query = this.em.createQuery("SELECT m.nameIt FROM Item m", String.class);
                break;
            }
            case FRENCH: {
                query = this.em.createQuery("SELECT m.nameFr FROM Item m", String.class);
                break;
            }
            case SPANISH: {
                query = this.em.createQuery("SELECT m.nameEs FROM Item m", String.class);
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
    public Optional<Item> findByName(String name, String lang) {
        LanguageHelper.Language language = LanguageHelper.getLanguage(lang);
        TypedQuery<Item> query;
        switch (language) {
            case ENGLISH: {
                query = this.em.createQuery("SELECT b FROM Item b WHERE b.nameEn = :name", Item.class);
                break;
            }
            case GERMAN: {
                query = this.em.createQuery("SELECT b FROM Item b WHERE b.nameDe = :name", Item.class);
                break;
            }
            case ITALIAN: {
                query = this.em.createQuery("SELECT b FROM Item b WHERE b.nameIt = :name", Item.class);
                break;
            }
            case FRENCH: {
                query = this.em.createQuery("SELECT b FROM Item b WHERE b.nameFr = :name", Item.class);
                break;
            }
            case SPANISH: {
                query = this.em.createQuery("SELECT b FROM Item b WHERE b.nameEs = :name", Item.class);
                break;
            }
            default: {
                throw new LanguageNotFoundException(language);
            }
        }

        query.setLockMode(LockModeType.PESSIMISTIC_READ);
        query.setParameter("name", name);
        Item item;
        try {
            item = query.getSingleResult();
        } catch (NoResultException e) {
            return Optional.empty();
        }
        return Optional.of(item);
    }

    @Transactional
    public Item persistIfNotExistent(Item item, String language) {
        Optional<Item> byName = findByName(item.getName(language), language);

        if (byName.isPresent()) {
            return byName.get();
        }

        try {
            em.persist(item);
        } catch (Exception e) {
            Optional<String> closestEntity = getClosestEntity(item.getName(language), language);
            if (closestEntity.isEmpty()) {
                throw new NotFoundException();
            }
            byName = findByName(closestEntity.get(), language);
            if (byName.isEmpty()) {
                throw new NotFoundException();
            }
            return byName.get();
        }
        return item;
    }

    @Transactional
    public void merge(Item item) {
        em.merge(item);
    }
}
