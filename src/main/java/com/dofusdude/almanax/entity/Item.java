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

package com.dofusdude.almanax.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Item extends MultilingualEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "pictureUrl", length = 1024)
    private String pictureUrl;

    // extras
    @Column(name = "url", length = 1024)
    private String url;

    public String getPicture() {
        return pictureUrl;
    }

    public Item(String name, String lang, String picture, String url) {
        super(name, lang);
        this.pictureUrl = picture;
        this.url = url;
    }

    public Item() {

    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

}
