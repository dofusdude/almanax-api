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

import com.dofusdude.client.model.Images;

import jakarta.json.bind.annotation.JsonbProperty;

public class ItemDTOV2 {
    @JsonbProperty("ankama_id")
    public Integer ankamaId;

    public String subtype;

    public String name;

    @JsonbProperty("image_urls")
    public ImageUrlsDTO imageUrls;

    public ItemDTOV2() {
    }

    public ItemDTOV2(Integer ankamaId, String subtype, String name, Images image_urls) {
        this.ankamaId = ankamaId;
        this.subtype = subtype;
        this.name = name;
        this.imageUrls = ImageUrlsDTO.from(image_urls);
    }
}
