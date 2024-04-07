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

package com.dofusdude.almanax.boundary.responses.errors;

import jakarta.ws.rs.core.Response;
import java.time.LocalDate;

public class DateFormatError extends Error {
    public DateFormatError() {
        super(Response.Status.BAD_REQUEST.getStatusCode(), "Date not available.",
                "Format like this: 'YEAR-MONTH-DAY' so today is " + LocalDate.now().toString());
    }
}
