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

package com.dofusdude.almanax.boundary.responses;

import java.util.List;
import java.util.Objects;

import com.dofusdude.almanax.dto.OfferingDTO;

public class OfferingResponse extends ApiResponse {
    public List<OfferingDTO> data;

    public OfferingResponse(Integer version, String language) {
        super(version, language);
    }

    public OfferingResponse(ApiResponse o, List<OfferingDTO> list) {
        super(o);
        data = list;
    }

    public OfferingResponse() {
    }

    public OfferingResponse(ApiResponse o) {
        super(o);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        OfferingResponse that = (OfferingResponse) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), data);
    }
}
