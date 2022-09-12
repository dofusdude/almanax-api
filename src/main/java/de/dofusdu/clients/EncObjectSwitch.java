/*
 * Copyright 2022 Christopher Sieh (stelzo@steado.de)
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

import de.dofusdu.dto.ItemDTOV2;
import de.dofusdu.dto.ItemObjectDTO;
import org.acme.openapi.api.AllItemsApi;
import org.acme.openapi.api.ConsumablesApi;
import org.acme.openapi.api.EquipmentApi;
import org.acme.openapi.api.ResourcesApi;
import org.acme.openapi.model.Resource;
import org.acme.openapi.model.Weapon;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

@ApplicationScoped
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


    public ItemObjectDTO get(String url, String language) {
        ItemObjectDTO itemObject = null;
        String[] split = url.split("/");
        Integer ankamaId = Integer.parseInt(split[split.length - 1].trim());
        String ankamaEnGeneral = "https://www.dofus.com/en/mmorpg/encyclopedia";

        if (url.contains("resources")) {
            Resource resource = resourcesApi.getItemsResourcesSingle(language, ankamaId);
            itemObject = new ItemObjectDTO();
            itemObject.ankamaId = resource.getAnkamaId();
            itemObject.name = resource.getName();
            itemObject.imageUrl = resource.getImageUrls().getSd() == null ? resource.getImageUrls().getIcon() : resource.getImageUrls().getSd();
            itemObject.ankamaUrl = ankamaEnGeneral;
            itemObject.imageUrlLocal = itemObject.imageUrl;
            itemObject.itemUrl = url;
        }

        if (url.contains("consumables")) {
            Resource resource = consumablesApi.getItemsConsumablesSingle(language, ankamaId);
            itemObject = new ItemObjectDTO();
            itemObject.ankamaId = resource.getAnkamaId();
            itemObject.name = resource.getName();
            itemObject.imageUrl = resource.getImageUrls().getSd() == null ? resource.getImageUrls().getIcon() : resource.getImageUrls().getSd();
            itemObject.ankamaUrl = ankamaEnGeneral;
            itemObject.imageUrlLocal = itemObject.imageUrl;
            itemObject.itemUrl = url;
        }

        if (url.contains("equipment") || url.contains("weapons") || url.contains("pets")) {
            Weapon weapon = equipmentApi.getItemsEquipmentSingle(language, ankamaId);
            itemObject = new ItemObjectDTO();
            itemObject.ankamaId = weapon.getAnkamaId();
            itemObject.name = weapon.getName();
            itemObject.imageUrl = weapon.getImageUrls().getSd() == null ? weapon.getImageUrls().getIcon() : weapon.getImageUrls().getSd();
            itemObject.ankamaUrl = ankamaEnGeneral;
            itemObject.imageUrlLocal = itemObject.imageUrl;
            itemObject.itemUrl = url;
        }

        if (itemObject == null) {
            throw new NotFoundException();
        }

        return itemObject;
    }

    public ItemDTOV2 getV2(String url, String language) {
        ItemDTOV2 itemObject = null;
        String[] split = url.split("/");
        Integer ankamaId = Integer.parseInt(split[split.length - 1].trim());

        if (url.contains("resources")) {
            Resource resource = resourcesApi.getItemsResourcesSingle(language, ankamaId);
            itemObject = new ItemDTOV2(resource.getAnkamaId(), "resources", resource.getName(), resource.getImageUrls());
        }

        if (url.contains("consumables")) {
            Resource resource = consumablesApi.getItemsConsumablesSingle(language, ankamaId);
            itemObject = new ItemDTOV2(resource.getAnkamaId(), "consumables", resource.getName(), resource.getImageUrls());
        }

        if (url.contains("equipment") || url.contains("weapons") || url.contains("pets")) {
            Weapon weapon = equipmentApi.getItemsEquipmentSingle(language, ankamaId);
            itemObject = new ItemDTOV2(weapon.getAnkamaId(), "equipment", weapon.getName(), weapon.getImageUrls());
        }

        if (itemObject == null) {
            throw new NotFoundException();
        }

        return itemObject;
    }
}
