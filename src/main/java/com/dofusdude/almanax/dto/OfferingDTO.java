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

package com.dofusdude.almanax.dto;

import jakarta.json.bind.annotation.JsonbProperty;
import java.time.LocalDate;

import com.dofusdude.almanax.entity.Offering;
import com.dofusdude.almanax.util.DateConverter;

public class OfferingDTO {
    public String date;

    @JsonbProperty("item_quantity")
    public Integer itemQuantity;

    public BonusDTO bonus;

    @JsonbProperty("item_name")
    public String itemName;

    @JsonbProperty("item")
    public ItemObjectDTO itemObject;

    @JsonbProperty("item_url")
    public String item_url;

    @JsonbProperty("enc_mapped")
    public boolean encMapped;

    @JsonbProperty("reward_kamas")
    public Integer rewardKamas;

    public static OfferingDTO from(Offering offering, String language, ItemObjectDTO itemObject) {
        if (offering == null) {
            return null;
        }
        OfferingDTO offeringDTO = new OfferingDTO();
        BonusDTO bonus = new BonusDTO();
        bonus.name = offering.getBonus().getName(language);
        bonus.type = offering.getBonus().getType().getName(language);
        offeringDTO.bonus = bonus;
        offeringDTO.date = DateConverter.toLocalDate(offering.getDate()).toString();
        offeringDTO.itemQuantity = offering.getItemQuantity();
        offeringDTO.itemName = offering.getItem().getName(language);
        offeringDTO.encMapped = itemObject != null;
        offeringDTO.item_url = offeringDTO.encMapped ? offering.getItem().getUrl().replace("/en/", "/" + language + "/")
                : null;
        offeringDTO.rewardKamas = offering.getRewardKamas();
        if (itemObject == null) {
            offeringDTO.itemObject = null;
        } else {
            offeringDTO.itemObject = itemObject;
            offeringDTO.itemName = itemObject.name;
        }

        return offeringDTO;
    }

    public LocalDate getDate() {
        return DateConverter.fromString(date);
    }
}
