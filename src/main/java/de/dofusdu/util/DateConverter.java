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

package de.dofusdu.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;

public class DateConverter {

    public static LocalDate toLocalDate(Date d) {
        return d.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static Date toDate(LocalDate d) {
        return java.sql.Date.valueOf(d);
    }

    public static LocalDate fromString(String s) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatter = formatter.withLocale(Locale.ENGLISH);
        return LocalDate.parse(s, formatter);
    }

    public static LocalDate[] range(LocalDate start, LocalDate end) {
        long numOfDaysBetween = java.time.temporal.ChronoUnit.DAYS.between(start, end);
        LocalDate[] range = new LocalDate[(int) numOfDaysBetween + 1];
        for (int i = 0; i < range.length; i++) {
            range[i] = start.plusDays(i);
        }
        return range;
    }
}
