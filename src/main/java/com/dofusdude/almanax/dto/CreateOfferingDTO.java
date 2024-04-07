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

package com.dofusdude.almanax.dto;

import jakarta.json.bind.annotation.JsonbProperty;
import java.time.LocalDate;

import com.dofusdude.almanax.entity.Bonus;
import com.dofusdude.almanax.entity.BonusType;
import com.dofusdude.almanax.entity.Item;
import com.dofusdude.almanax.entity.Offering;
import com.dofusdude.almanax.util.DateConverter;

public class CreateOfferingDTO {

    public String date;
    @JsonbProperty("item_quantity")
    public Integer itemQuantity;
    public String item;

    @JsonbProperty("description")
    public String bonus;
    @JsonbProperty("bonus")
    public String bonusType;
    @JsonbProperty("item_picture_url")
    public String itemPicture;

    @JsonbProperty("reward_kamas")
    public Integer rewardKamas;

    public String language;

    public Offering toOffering(String language, String url) {
        return new Offering(DateConverter.toDate(DateConverter.fromString(date)),
                itemQuantity,
                new Bonus(bonus, language, new BonusType(bonusType, language)),
                new Item(item, language, itemPicture, url),
                rewardKamas);
    }

    public LocalDate getDate() {
        return DateConverter.fromString(date);
    }
}
