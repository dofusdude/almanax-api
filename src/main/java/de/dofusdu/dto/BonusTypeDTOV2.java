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

package de.dofusdu.dto;

import de.dofusdu.entity.Bonus;

import javax.json.bind.annotation.JsonbProperty;

public class BonusTypeDTOV2 {
    @JsonbProperty("id")
    public String id;

    @JsonbProperty("name")
    public String name;

    public BonusTypeDTOV2() {
    }

    public static BonusTypeDTOV2 from(Bonus bonus, String language) {
        BonusTypeDTOV2 bonusTypeDTOV2 = new BonusTypeDTOV2();
        bonusTypeDTOV2.id = bonus.getType().getName("en").toLowerCase().replace(' ', '-').replace(",", "").strip();
        bonusTypeDTOV2.name = bonus.getType().getName(language);
        return bonusTypeDTOV2;
    }
}
