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

import org.acme.openapi.model.ImageUrls;

public class ImageUrlsDTO {
    /**
     * All images except icon are rendered in the background which can take some time (up to hours if all data is completely generated from scratch). Because of this, they can be null if they are not yet rendered.
     **/
    public String icon;
    /**
     * All images except icon are rendered in the background which can take some time (up to hours if all data is completely generated from scratch). Because of this, they can be null if they are not yet rendered.
     **/
    public String sd;
    /**
     * All images except icon are rendered in the background which can take some time (up to hours if all data is completely generated from scratch). Because of this, they can be null if they are not yet rendered.
     **/
    public String hq;
    /**
     * All images except icon are rendered in the background which can take some time (up to hours if all data is completely generated from scratch). Because of this, they can be null if they are not yet rendered.
     **/
    public String hd;

    public ImageUrlsDTO() {
    }

    public static ImageUrlsDTO from(ImageUrls imageUrls) {
        ImageUrlsDTO imageUrlsDTO = new ImageUrlsDTO();
        imageUrlsDTO.icon = imageUrls.getIcon();
        imageUrlsDTO.sd = imageUrls.getSd();
        imageUrlsDTO.hq = imageUrls.getHq();
        imageUrlsDTO.hd = imageUrls.getHd();
        return imageUrlsDTO;
    }
}
