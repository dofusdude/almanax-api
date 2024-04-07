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

package com.dofusdude.almanax.util;

import java.util.Collection;
import java.util.List;

import com.dofusdude.almanax.dto.LanguageMapDTO;

public class LanguageHelper {

    public enum Language {
        GERMAN,
        FRENCH,
        ITALIAN,
        ENGLISH,
        SPANISH,
        NOT_FOUND
    }

    public static Collection<String> allLanguages() {
        return List.of(getString(Language.ENGLISH), getString(Language.FRENCH), getString(Language.GERMAN),
                getString(Language.ITALIAN), getString(Language.SPANISH));
    }

    public static Collection<LanguageMapDTO> allLanguagesDto() {
        return List.of(new LanguageMapDTO("english", "en"),
                new LanguageMapDTO("french", "fr"),
                new LanguageMapDTO("italian", "it"),
                new LanguageMapDTO("spanish", "es"),
                new LanguageMapDTO("german", "de"));
    }

    public static String getString(Language language) {
        switch (language) {
            case GERMAN:
                return "de";
            case FRENCH:
                return "fr";
            case ITALIAN:
                return "it";
            case ENGLISH:
                return "en";
            case SPANISH:
                return "es";
            default:
                return "--";
        }
    }

    public static Language getLanguage(String languageCode) {
        if (languageCode.equals("de"))
            return Language.GERMAN;
        if (languageCode.equals("fr"))
            return Language.FRENCH;
        if (languageCode.equals("it"))
            return Language.ITALIAN;
        if (languageCode.equals("en"))
            return Language.ENGLISH;
        if (languageCode.equals("es"))
            return Language.SPANISH;
        return Language.NOT_FOUND;
    }

    public static boolean validLanguage(String language) {
        return getLanguage(language) != Language.NOT_FOUND;
    }

}
