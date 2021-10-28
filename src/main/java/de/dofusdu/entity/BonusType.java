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

import de.dofusdu.util.LanguageHelper;

import javax.persistence.*;

@Entity
@Table(indexes = {
        @Index(columnList = "urlalias", name = "idx_urlalias", unique = true)
})
public class BonusType extends MultilingualEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "urlalias",
            nullable = false, length = 1024) // always english first to get urlalias
    private String urlAlias;

    public BonusType(String name, String lang) {
        super(name, lang);
        LanguageHelper.Language language = LanguageHelper.getLanguage(lang);
        if (language == LanguageHelper.Language.ENGLISH) {
            urlAlias = generateUrlAlias(name);
        }
    }

    public static String generateUrlAlias(String name) {
        return name.toLowerCase().replace(' ', '-').strip();
    }

    public BonusType() {
    }

    public String getUrlAlias() {
        return urlAlias;
    }
}
