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

public class BonusTypeMapDTOV2 {
    public String id;
    public String name;

    public BonusTypeMapDTOV2() {
    }

    public BonusTypeMapDTOV2(String name, String id) {
        this.name = name;
        this.id = id;
    }
}
