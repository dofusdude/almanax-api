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

import de.dofusdu.entity.Offering;

public class TributeDTOV2 {
    public ItemDTOV2 item;

    public int quantity;

    public TributeDTOV2() {
    }

    public static TributeDTOV2 from(Offering offering, ItemDTOV2 itemDTOV2) {
        TributeDTOV2 tributeDTOV2 = new TributeDTOV2();
        tributeDTOV2.quantity = offering.getItemQuantity();
        tributeDTOV2.item = itemDTOV2;
        return tributeDTOV2;
    }
}
