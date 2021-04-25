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

package de.dofusdu.clients;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class EncObjectSwitch {

    @Inject
    @RestClient
    ItemSearch itemSearch;

    public Object get(String url, String language) {
        Object itemObject = null;
        String[] split = url.split("/");
        if (url.contains("resources")) {
            itemObject = itemSearch.getResource(language, Long.valueOf(split[split.length-1]));
        }
        if (url.contains("consumables")) {
            itemObject = itemSearch.getConsumable(language, Long.valueOf(split[split.length-1]));
        }
        if (url.contains("equipment")) {
            itemObject = itemSearch.getEquipment(language, Long.valueOf(split[split.length-1]));
        }
        if (url.contains("weapons")) {
            itemObject = itemSearch.getWeapon(language, Long.valueOf(split[split.length-1]));
        }
        if (url.contains("pets")) {
            itemObject = itemSearch.getPet(language, Long.valueOf(split[split.length-1]));
        }
        return itemObject;
    }
}
