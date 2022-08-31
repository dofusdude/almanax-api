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

import org.acme.openapi.api.AllItemsApi;
import org.acme.openapi.api.ConsumablesApi;
import org.acme.openapi.api.EquipmentApi;
import org.acme.openapi.api.ResourcesApi;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

@RequestScoped
public class EncObjectSwitch {

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


    public Object get(String url, String language) {
        Object itemObject = null;
        String[] split = url.split("/");
        Integer ankamaId = Integer.parseInt(split[split.length - 1]);

        if (ankamaId == null) {
            throw new NotFoundException();
        }

        String filterTypeName = null;
        if (url.contains("resources")) {
            itemObject = resourcesApi.getItemsResourcesSingle(language, ankamaId);
        }

        if (url.contains("consumables")) {
            itemObject = consumablesApi.getItemsConsumablesSingle(language, ankamaId);
        }

        if (url.contains("equipment")) {
            itemObject = equipmentApi.getItemsEquipmentSingle(language, ankamaId);
        }

        if (itemObject == null) {
            throw new NotFoundException();
        }

        return itemObject;
    }
}
