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

package de.dofusdu.boundary.responses;

import de.dofusdu.dto.ItemPositionDTO;

import java.util.List;

public class ItemsResponse extends ApiResponse {
    public List<ItemPositionDTO> data;

    public ItemsResponse(Integer version, String language) {
        super(version, language);
    }

    public ItemsResponse(ApiResponse o, List<ItemPositionDTO> list) {
        super(o);
        this.data = list;
    }

    public ItemsResponse() {
        super();
    }

    public ItemsResponse(ApiResponse o) {
        super(o);
    }

}
