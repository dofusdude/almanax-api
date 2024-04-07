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

package com.dofusdude.almanax.clients;

import com.dofusdude.client.api.ConsumablesApi;
import com.dofusdude.client.api.EquipmentApi;
import com.dofusdude.client.api.ResourcesApi;
import com.dofusdude.client.model.Resource;
import com.dofusdude.client.model.Weapon;
import com.dofusdude.client.ApiClient;
import com.dofusdude.client.Configuration;
import com.dofusdude.almanax.dto.ItemDTOV2;
import com.dofusdude.almanax.dto.ItemObjectDTO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class EncObjectSwitch {

    public ItemObjectDTO get(String url, String language) {
        ItemObjectDTO itemObject = null;
        String[] split = url.split("/");
        Integer ankamaId = Integer.parseInt(split[split.length - 1].trim());
        String ankamaEnGeneral = "https://www.dofus.com/en/mmorpg/encyclopedia";
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        String game = "dofus2";

        if (url.contains("resources")) {
            ResourcesApi resourcesApi = new ResourcesApi(defaultClient);
            try {
                Resource resource = resourcesApi.getItemsResourcesSingle(language, ankamaId, game);
                itemObject = new ItemObjectDTO();
                itemObject.ankamaId = resource.getAnkamaId();
                itemObject.name = resource.getName();
                itemObject.imageUrl = resource.getImageUrls().getSd() == null ? resource.getImageUrls().getIcon()
                        : resource.getImageUrls().getSd();
                itemObject.ankamaUrl = ankamaEnGeneral;
                itemObject.imageUrlLocal = itemObject.imageUrl;
                itemObject.itemUrl = url;
            } catch (Exception e) {
                throw new NotFoundException();
            }
        }

        if (url.contains("consumables")) {
            ConsumablesApi resourcesApi = new ConsumablesApi(defaultClient);
            try {
                Resource resource = resourcesApi.getItemsConsumablesSingle(language, ankamaId, game);
                itemObject = new ItemObjectDTO();
                itemObject.ankamaId = resource.getAnkamaId();
                itemObject.name = resource.getName();
                itemObject.imageUrl = resource.getImageUrls().getSd() == null ? resource.getImageUrls().getIcon()
                        : resource.getImageUrls().getSd();
                itemObject.ankamaUrl = ankamaEnGeneral;
                itemObject.imageUrlLocal = itemObject.imageUrl;
                itemObject.itemUrl = url;
            } catch (Exception e) {
                throw new NotFoundException();
            }
        }

        if (url.contains("equipment") || url.contains("weapons") || url.contains("pets")) {
            EquipmentApi equipmentApi = new EquipmentApi(defaultClient);
            try {
                Weapon weapon = equipmentApi.getItemsEquipmentSingle(language, ankamaId, game);
                itemObject = new ItemObjectDTO();
                itemObject.ankamaId = weapon.getAnkamaId();
                itemObject.name = weapon.getName();
                itemObject.imageUrl = weapon.getImageUrls().getSd() == null ? weapon.getImageUrls().getIcon()
                        : weapon.getImageUrls().getSd();
                itemObject.ankamaUrl = ankamaEnGeneral;
                itemObject.imageUrlLocal = itemObject.imageUrl;
                itemObject.itemUrl = url;
            } catch (Exception e) {
                throw new NotFoundException();
            }
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
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        String game = "dofus2";

        if (url.contains("resources")) {
            try {
                Resource resource = new ResourcesApi(defaultClient).getItemsResourcesSingle(language, ankamaId, game);
                itemObject = new ItemDTOV2(resource.getAnkamaId(), "resources", resource.getName(),
                        resource.getImageUrls());
            } catch (Exception e) {
                throw new NotFoundException();
            }
        }

        if (url.contains("consumables")) {
            try {
                Resource resource = new ConsumablesApi(defaultClient).getItemsConsumablesSingle(language, ankamaId,
                        game);
                itemObject = new ItemDTOV2(resource.getAnkamaId(), "consumables", resource.getName(),
                        resource.getImageUrls());
            } catch (Exception e) {
                throw new NotFoundException();
            }
        }

        if (url.contains("equipment") || url.contains("weapons") || url.contains("pets")) {
            try {
                Weapon weapon = new EquipmentApi(defaultClient).getItemsEquipmentSingle(language, ankamaId, game);
                itemObject = new ItemDTOV2(weapon.getAnkamaId(), "equipment", weapon.getName(), weapon.getImageUrls());
            } catch (Exception e) {
                throw new NotFoundException();
            }
        }

        if (itemObject == null) {
            throw new NotFoundException();
        }

        return itemObject;
    }
}
