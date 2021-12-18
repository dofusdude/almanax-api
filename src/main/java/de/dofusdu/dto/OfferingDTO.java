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

package de.dofusdu.dto;

import de.dofusdu.dto.encyclopedia.ItemNoEncDTO;
import de.dofusdu.entity.Offering;
import de.dofusdu.util.DateConverter;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import java.time.LocalDate;
import java.util.Objects;

public class OfferingDTO {
    public String date;

    @JsonbProperty("item_quantity")
    public Integer itemQuantity;

    public BonusDTO bonus;

    @JsonbTransient // do not serialize
    public String itemName;

    @JsonbProperty("item")
    public Object itemObject;

    @JsonbProperty("item_url")
    public String item_url;

    @JsonbProperty("enc_mapped")
    public boolean encMapped;

    public static OfferingDTO from(Offering offering, String language, Object itemObject) {
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
        offeringDTO.item_url = offeringDTO.encMapped ? offering.getItem().getUrl().replace("/en/", "/" + language + "/") : null;
        offeringDTO.itemObject = offeringDTO.encMapped ? itemObject : new ItemNoEncDTO(offeringDTO.itemName);

        return offeringDTO;
    }

    public LocalDate getDate() {
        return DateConverter.fromString(date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OfferingDTO that = (OfferingDTO) o;
        return Objects.equals(date, that.date) && Objects.equals(itemQuantity, that.itemQuantity) && Objects.equals(bonus, that.bonus) && Objects.equals(itemName, that.itemName) && Objects.equals(itemObject, that.itemObject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, itemQuantity, bonus, itemName, itemObject);
    }
}
