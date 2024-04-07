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

public class ItemObjectDTO {

    @JsonbProperty("ankama_id")
    public Integer ankamaId;

    @JsonbProperty("name")
    public String name;

    @JsonbProperty("ankama_url")
    public String ankamaUrl;

    @JsonbProperty("image_url")
    public String imageUrl;

    @JsonbProperty("image_url_local")
    public String imageUrlLocal;

    @JsonbProperty("item_url")
    public String itemUrl;

}
