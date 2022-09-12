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

import de.dofusdu.boundary.responses.ApiResponse;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

@RequestScoped
public class ErrorProducer {

    @Context
    UriInfo uriInfo;


    @ConfigProperty(name = "almanax.first.date")
    String firstDate;

    public ErrorProducer() {}

    private ApiResponse base;

    public void setBase(ApiResponse base) {
        this.base = base;
    }

    // --- Errors ---
    public DateNotFoundResponse dateNotFound() {
        return new DateNotFoundResponse(base, firstDate);
    }

    public EnglishDayFirstResponse englishDayFirst() {
        return new EnglishDayFirstResponse(base);
    }

    public NoEntryForDateResponse noEntryForDate() {
        return new NoEntryForDateResponse(base);
    }

    public UnautherizedResponse unautherized() {
        return new UnautherizedResponse(base);
    }

    public NoOfferingWithBonusResponse noOfferingWithBonus() {
        return new NoOfferingWithBonusResponse(base);
    }

    public LanguageNotFoundResponse languageNotFound() {
        return new LanguageNotFoundResponse(base, uriInfo);
    }

    public BonusTypeNotFoundResponse bonusTypeNotFound() {
        return new BonusTypeNotFoundResponse(base, uriInfo);
    }

    public DateFormatResponse dateFormat() {
        return new DateFormatResponse(base);
    }

    public RangeTooBigResponse rangeTooBig() {
        return new RangeTooBigResponse(base);
    }

    public TimezoneResponse timezone() {
        return new TimezoneResponse(base);
    }
}
