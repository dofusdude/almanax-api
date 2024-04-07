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

package com.dofusdude.almanax.exceptions;

import java.util.List;

public class BonusTypeNotFoundException extends RuntimeException {

    private List<String> allBonusTypes;

    public BonusTypeNotFoundException(String bonusUrlAlias, List<String> allBonusTypes) {
        super("BonusType '" + bonusUrlAlias + "' not found.");
        this.allBonusTypes = allBonusTypes;
    }

    public String getAction() {
        StringBuilder allBonusTypeText = new StringBuilder();
        for (String btype : allBonusTypes) {
            allBonusTypeText.append(btype);
            allBonusTypeText.append("|");
        }
        allBonusTypeText.deleteCharAt(allBonusTypeText.toString().length() - 1);

        return "Available BonusTypes: " + allBonusTypeText.toString();
    }
}
