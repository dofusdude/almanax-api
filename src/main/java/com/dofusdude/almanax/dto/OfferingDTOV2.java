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

public class OfferingDTOV2 {
    public String date;

    public BonusDTOV2 bonus;

    @JsonbProperty("tribute")
    public TributeDTOV2 tribute;

    @JsonbProperty("reward_kamas")
    public Integer rewardKamas;

    public OfferingDTOV2() {
    }

    public static OfferingDTOV2 from(Offering offering, String language, ItemDTOV2 itemObject) {
        if (offering == null) {
            return null;
        }
        OfferingDTOV2 offeringDTO = new OfferingDTOV2();
        offeringDTO.date = DateConverter.toLocalDate(offering.getDate()).toString();
        offeringDTO.bonus = BonusDTOV2.from(offering.getBonus(), language);
        offeringDTO.tribute = TributeDTOV2.from(offering, itemObject);
        offeringDTO.rewardKamas = offering.getRewardKamas();
        return offeringDTO;
    }

    public LocalDate getDate() {
        return DateConverter.fromString(date);
    }

    public boolean isSameByContent(OfferingDTOV2 other) {
        return this.bonus.description.equals(other.bonus.description) &&
                this.bonus.type.id.equals(other.bonus.type.id) &&
                this.tribute.quantity == other.tribute.quantity &&
                this.tribute.item.ankamaId.equals(other.tribute.item.ankamaId) &&
                this.rewardKamas.equals(other.rewardKamas);
    }
}
