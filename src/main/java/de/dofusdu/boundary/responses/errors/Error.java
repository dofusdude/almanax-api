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

package de.dofusdu.boundary.responses.errors;

import java.util.Objects;

public class Error {
    public int code;
    public String text;
    public String action;

    public Error(int code, String text, String action) {
        this.code = code;
        this.text = text;
        this.action = action;
    }

    public Error() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Error error = (Error) o;
        return code == error.code && Objects.equals(text, error.text) && Objects.equals(action, error.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, text, action);
    }
}
