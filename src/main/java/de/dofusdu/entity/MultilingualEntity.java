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

package de.dofusdu.entity;

import de.dofusdu.exceptions.LanguageNotFoundException;
import de.dofusdu.util.LanguageHelper;

import javax.persistence.*;

@Entity
@Table(indexes = {
        @Index(columnList = "nameEn", name = "idx_nameEn", unique = true)
})
@Inheritance(strategy = InheritanceType.JOINED) // use in hibernate like all subclasses have the attributes in protected.
public class MultilingualEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "nameEn", length = 2048)
    protected String nameEn;

    @Column(name = "nameDe", length = 2048)
    protected String nameDe;

    @Column(name = "nameFr", length = 2048)
    protected String nameFr;

    @Column(name = "nameIt", length = 2048)
    protected String nameIt;

    @Column(name = "nameEs", length = 2048)
    protected String nameEs;

    public MultilingualEntity() {
    }

    public MultilingualEntity(String name, String lang) {
        LanguageHelper.Language language = LanguageHelper.getLanguage(lang);
        switch (language) {
            case GERMAN: nameDe = name; break;
            case FRENCH: nameFr = name; break;
            case ITALIAN: nameIt = name; break;
            case ENGLISH: nameEn = name; break;
            case SPANISH: nameEs = name; break;
            default: {
                throw new LanguageNotFoundException(language);
            }
        }
    }

    public void setName(String name, String lang) {
        LanguageHelper.Language language = LanguageHelper.getLanguage(lang);

        switch (language) {
            case GERMAN: nameDe = name; break;
            case FRENCH: nameFr = name; break;
            case ITALIAN: nameIt = name; break;
            case ENGLISH: nameEn = name; break;
            case SPANISH: nameEs = name; break;
            default: {
                throw new LanguageNotFoundException(language);
            }
        }
    }

    public String getName(String lang) {
        LanguageHelper.Language language = LanguageHelper.getLanguage(lang);
        switch (language) {
            case GERMAN: return nameDe;
            case FRENCH: return nameFr;
            case ITALIAN: return nameIt;
            case ENGLISH: return nameEn;
            case SPANISH: return nameEs;
            default: {
                throw new LanguageNotFoundException(language);
            }
        }
    }

}
