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

package com.dofusdude.almanax.boundary;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameters;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.dofusdude.almanax.boundary.responses.*;
import com.dofusdude.almanax.boundary.responses.errors.ErrorProducer;
import com.dofusdude.almanax.dto.ItemPositionDTO;
import com.dofusdude.almanax.dto.OfferingDTO;
import com.dofusdude.almanax.exceptions.BonusTypeNotFoundException;
import com.dofusdude.almanax.gateway.BonusTypeRepository;
import com.dofusdude.almanax.gateway.OfferingRepository;
import com.dofusdude.almanax.util.DateConverter;
import com.dofusdude.almanax.util.LanguageHelper;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.*;

@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/dofus")
public class OfferingResourceV1 {

        private OfferingRepository offeringRepository;
        private BonusTypeRepository bonusTypeRepository;

        private final Integer version = 1;
        private final ErrorProducer errorProducer;

        @ConfigProperty(name = "timezone")
        String configTimezone;

        @Inject
        public OfferingResourceV1(OfferingRepository offeringRepository,
                        ErrorProducer errorProducer,
                        BonusTypeRepository bonusTypeRepository) {
                this.offeringRepository = offeringRepository;
                this.errorProducer = errorProducer;
                this.bonusTypeRepository = bonusTypeRepository;
        }

        /** ######################## API META ######################## **/

        @Tag(name = "API Meta", description = "Help for your requests.")
        @Operation(summary = "All available languagecodes.")
        @APIResponses({
                        @APIResponse(responseCode = "200", description = "OK", content = {
                                        @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(oneOf = com.dofusdude.almanax.boundary.responses.BonusTypesResponse.class))
                        })
        })
        @GET
        @Path("languages")
        public LanguagesResponse languages() {
                ApiResponse response = new ApiResponse(version, "en");
                errorProducer.setBase(response);
                return new LanguagesResponse(response, LanguageHelper.allLanguagesDto());
        }

        @Tag(name = "API Meta")
        @Operation(summary = "All available bonuses in English.")
        @APIResponses({
                        @APIResponse(responseCode = "200", description = "OK", content = {
                                        @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(oneOf = com.dofusdude.almanax.boundary.responses.BonusTypesResponse.class))
                        })
        })
        @GET
        @Path("bonuses")
        @Transactional(Transactional.TxType.REQUIRES_NEW)
        public Response bonusTypesDefault() {
                return bonusTypes("en");
        }

        @Tag(name = "API Meta")
        @Operation(summary = "Get all available bonuses.")
        @Parameters({
                        @Parameter(name = "language", in = ParameterIn.PATH, example = "en", description = "Language as code of length 2.")
        })
        @APIResponses({
                        @APIResponse(responseCode = "200", description = "OK", content = {
                                        @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(oneOf = com.dofusdude.almanax.boundary.responses.BonusTypesResponse.class))
                        }),
                        @APIResponse(responseCode = "404", description = "Not Found", content = {
                                        @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(oneOf = com.dofusdude.almanax.boundary.responses.errors.LanguageNotFoundResponse.class))
                        })
        })
        @GET
        @Path("bonuses/{language}")
        @Transactional(Transactional.TxType.REQUIRES_NEW)
        public Response bonusTypes(@PathParam("language") String language) {
                ApiResponse response = new ApiResponse(version, language);
                errorProducer.setBase(response);

                // check existing language
                if (!LanguageHelper.validLanguage(language)) {
                        return Response.status(Response.Status.NOT_FOUND)
                                        .entity(errorProducer.languageNotFound())
                                        .build();
                }

                BonusTypesResponse bonusTypesResponse = new BonusTypesResponse(response,
                                bonusTypeRepository.bonusTypesDto(language));

                return Response.ok(bonusTypesResponse).build();
        }

        /** ######################## SINGLE DAY ######################## **/
        @Tag(name = "Single Day", description = "Offering for a date.")
        @Parameters({
                        @Parameter(name = "language", in = ParameterIn.PATH, example = "en", description = "Language as code of length 2."),
                        @Parameter(name = "date", in = ParameterIn.PATH, example = "2021-01-01", description = "Date formatted like yyyy-mm-dd")
        })
        @APIResponses({
                        @APIResponse(responseCode = "200", description = "OK", content = {
                                        @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(oneOf = com.dofusdude.almanax.boundary.responses.SingleOfferingResponse.class))
                        }),
                        @APIResponse(responseCode = "400", description = "Bad Request", content = {
                                        @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(oneOf = com.dofusdude.almanax.boundary.responses.errors.DateFormatResponse.class))
                        }),
                        @APIResponse(responseCode = "404", description = "Not Found", content = {
                                        @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(oneOf = com.dofusdude.almanax.boundary.responses.errors.LanguageNotFoundResponse.class))
                        })
        })
        @GET
        @Path("{language}/{date}")
        @Counted(name = "singleDayRelaisCount", description = "Single Day hits (date) without versioning.")
        @Retry
        @CircuitBreaker
        @Timed(name = "singleDayRelaisTiming")
        public Response singleDay(@PathParam("language") String language,
                        @PathParam("date") String stringDate) {
                return singleDayV1(language, stringDate);
        }

        @Tag(name = "Version 1.0 stable", description = "Never changing endpoints. Prefer using without /v1 if you can and adapt to changes.")
        @Operation(summary = "Offering for a date.")
        @Parameters({
                        @Parameter(name = "language", in = ParameterIn.PATH, example = "en", description = "Language as code of length 2."),
                        @Parameter(name = "date", in = ParameterIn.PATH, example = "2021-01-01", description = "Date formatted like yyyy-mm-dd")
        })
        @APIResponses({
                        @APIResponse(responseCode = "200", description = "OK", content = {
                                        @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(oneOf = com.dofusdude.almanax.boundary.responses.SingleOfferingResponse.class))
                        }),
                        @APIResponse(responseCode = "400", description = "Bad Request", content = {
                                        @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(oneOf = com.dofusdude.almanax.boundary.responses.errors.DateFormatResponse.class))
                        }),
                        @APIResponse(responseCode = "404", description = "Not Found", content = {
                                        @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(oneOf = com.dofusdude.almanax.boundary.responses.errors.LanguageNotFoundResponse.class))
                        }),
        })
        @GET
        @Path("v1/{language}/{date}")
        @Counted(name = "singleDayV1Count", description = "Single Day hits (date) with versioning.")
        @Retry
        @CircuitBreaker
        @Timed(name = "singleDayV1Timing")
        // @CacheResult(cacheName = "singleDayV1")
        public Response singleDayV1(@PathParam("language") String language,
                        @PathParam("date") String stringDate) {
                // Prepare responses.
                ApiResponse response = new ApiResponse(version, language);
                SingleOfferingResponse res = new SingleOfferingResponse(response);
                errorProducer.setBase(response);

                // Make date from string.
                LocalDate date;
                try {
                        date = DateConverter.fromString(stringDate);
                } catch (DateTimeParseException e) {
                        return Response.status(Response.Status.BAD_REQUEST)
                                        .entity(errorProducer.dateFormat())
                                        .build();
                }

                // check existing language
                if (!LanguageHelper.validLanguage(language)) {
                        return Response.status(Response.Status.NOT_FOUND)
                                        .entity(errorProducer.languageNotFound())
                                        .build();
                }

                // Get data.
                Optional<OfferingDTO> offeringDTO = offeringRepository.singleDTOFromDate(date, language);
                if (offeringDTO.isEmpty()) {
                        return Response.status(Response.Status.NOT_FOUND)
                                        .entity(errorProducer.dateNotFound())
                                        .build();
                }

                res.data = offeringDTO.get();
                return Response
                                .ok(res)
                                .build();
        }

        /** ######################## DAYS AHEAD RAW ######################## **/
        @Tag(name = "Days ahead", description = "Based on current date, see the future offerings.")
        @Operation(summary = "All offerings for given count of future days from now.")
        @Parameters({
                        @Parameter(name = "language", in = ParameterIn.PATH, example = "en", description = "Language as code of length 2."),
                        @Parameter(name = "days_ahead", in = ParameterIn.PATH, example = "1", description = "Number of days ahead. 0 = today, 1 = tomorrow ..."),
                        @Parameter(name = "timezone", in = ParameterIn.QUERY, description = "Default is Europe/Paris. Insert something random to see all available.")
        })
        @APIResponses({
                        @APIResponse(responseCode = "200", description = "OK", content = {
                                        @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(oneOf = com.dofusdude.almanax.boundary.responses.OfferingResponse.class))
                        }),
                        @APIResponse(responseCode = "404", description = "Not Found", content = {
                                        @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(oneOf = {
                                                        com.dofusdude.almanax.boundary.responses.errors.DateNotFoundResponse.class,
                                                        com.dofusdude.almanax.boundary.responses.errors.LanguageNotFoundResponse.class,
                                                        com.dofusdude.almanax.boundary.responses.errors.TimezoneResponse.class
                                        }))
                        })
        })
        @GET
        @Path("{language}/ahead/{days_ahead}")
        @Counted(name = "daysAheadRawRelaisCount", description = "Days ahead hits without versioning.")
        @Retry
        @CircuitBreaker
        @Timed(name = "daysAheadRawRelaisTiming")
        public Response daysAheadRaw(@PathParam("days_ahead") Integer daysAhead,
                        @PathParam("language") String language,
                        @QueryParam("timezone") String timezone) {
                return daysAheadRawV1(daysAhead, language, timezone);
        }

        @Tag(name = "Version 1.0 stable")
        @Operation(summary = "All offerings for given count of future days from now.")
        @Parameters({
                        @Parameter(name = "language", in = ParameterIn.PATH, example = "en", description = "Language as code of length 2."),
                        @Parameter(name = "days_ahead", in = ParameterIn.PATH, example = "1", description = "Number of days ahead. 0 = today, 1 = tomorrow ..."),
                        @Parameter(name = "timezone", in = ParameterIn.QUERY, description = "Default is Europe/Paris. Insert something random to see all available.")
        })
        @GET
        @APIResponses({
                        @APIResponse(responseCode = "200", description = "OK", content = {
                                        @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(oneOf = com.dofusdude.almanax.boundary.responses.OfferingResponse.class))
                        }),
                        @APIResponse(responseCode = "404", description = "Not Found", content = {
                                        @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(oneOf = {
                                                        com.dofusdude.almanax.boundary.responses.errors.DateNotFoundResponse.class,
                                                        com.dofusdude.almanax.boundary.responses.errors.LanguageNotFoundResponse.class,
                                                        com.dofusdude.almanax.boundary.responses.errors.TimezoneResponse.class
                                        }))
                        })
        })
        @Path("v1/{language}/ahead/{days_ahead}")
        @Transactional
        @Counted(name = "daysAheadRawV1Count", description = "Days ahead hits with versioning.")
        @Retry
        @CircuitBreaker
        @Timed(name = "daysAheadRawV1Timing")
        public Response daysAheadRawV1(@PathParam("days_ahead") Integer daysAhead,
                        @PathParam("language") String language,
                        @QueryParam("timezone") String timezone) {
                // Prepare responses.
                ApiResponse response = new ApiResponse(version, language);
                OfferingResponse res = new OfferingResponse(response);
                errorProducer.setBase(response);

                // check existing language
                if (!LanguageHelper.validLanguage(language)) {
                        return Response.status(Response.Status.NOT_FOUND)
                                        .entity(errorProducer.languageNotFound())
                                        .build();
                }

                // Get data.
                LocalDate startDate;
                if (timezone == null || timezone.isEmpty()) {
                        startDate = LocalDate.now(ZoneId.of(configTimezone));
                } else {
                        try {
                                startDate = LocalDate.now(ZoneId.of(timezone));
                        } catch (Exception e) {
                                return Response.status(Response.Status.BAD_REQUEST)
                                                .entity(errorProducer.timezone())
                                                .build();
                        }
                }
                LocalDate endDate = startDate.plusDays(daysAhead);

                List<OfferingDTO> offerings = offeringRepository.listFromDateRangeDTO(startDate, endDate, language);
                if (offerings.isEmpty()) {
                        return Response.status(Response.Status.NOT_FOUND)
                                        .entity(errorProducer.dateNotFound())
                                        .build();
                }

                res.data = offerings;
                return Response.ok(res).build();
        }

        /**
         * ######################## DAYS AHEAD BONUS FILTER ########################
         **/
        @Tag(name = "Days ahead")
        @Operation(summary = "Only show offerings with the given bonus type.")
        @Parameters({
                        @Parameter(name = "language", in = ParameterIn.PATH, example = "en", description = "Language as code of length 2."),
                        @Parameter(name = "days_ahead", in = ParameterIn.PATH, example = "1", description = "Number of days ahead. 0 = today, 1 = tomorrow ..."),
                        @Parameter(name = "bonus_type", in = ParameterIn.PATH, example = "full-of-life", description = "Bonustype to filter. always english in lowercase and '-' instead of ' ' for readability."),
                        @Parameter(name = "timezone", in = ParameterIn.QUERY, description = "Default is Europe/Paris. Insert something random to see all available.")
        })
        @APIResponses({
                        @APIResponse(responseCode = "200", description = "OK", content = {
                                        @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(oneOf = com.dofusdude.almanax.boundary.responses.OfferingResponse.class))
                        }),
                        @APIResponse(responseCode = "404", description = "Not Found", content = {
                                        @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(oneOf = {
                                                        com.dofusdude.almanax.boundary.responses.errors.BonusTypeNotFoundResponse.class,
                                                        com.dofusdude.almanax.boundary.responses.errors.DateNotFoundResponse.class,
                                                        com.dofusdude.almanax.boundary.responses.errors.TimezoneResponse.class
                                        }))
                        })
        })
        @GET
        @Path("{language}/ahead/{days_ahead}/bonus/{bonus_type}")
        @Counted(name = "daysAheadBonusFilterRelaisCount", description = "Days ahead with bonus filter, without versioning.")
        @Retry
        @CircuitBreaker
        @Timed(name = "daysAheadBonusFilterRelaisTiming")
        public Response daysAheadBonusFilter(@PathParam("days_ahead") Integer daysAhead,
                        @PathParam("language") String language,
                        @PathParam("bonus_type") String bonusType,
                        @QueryParam("timezone") String timezone) {
                return daysAheadBonusFilterV1(daysAhead, language, bonusType, timezone);
        }

        @Tag(name = "Version 1.0 stable")
        @Operation(summary = "Only show offerings with the given bonus type.")
        @Parameters({
                        @Parameter(name = "language", in = ParameterIn.PATH, example = "en", description = "Language as code of length 2."),
                        @Parameter(name = "days_ahead", in = ParameterIn.PATH, example = "1", description = "Number of days ahead. 0 = today, 1 = tomorrow ..."),
                        @Parameter(name = "bonus_type", in = ParameterIn.PATH, example = "full-of-life", description = "Bonustype to filter. always english in lowercase and '-' instead of ' ' for readability."),
                        @Parameter(name = "timezone", in = ParameterIn.QUERY, description = "Default is Europe/Paris. Insert something random to see all available.")
        })
        @APIResponses({
                        @APIResponse(responseCode = "200", description = "OK", content = {
                                        @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(oneOf = com.dofusdude.almanax.boundary.responses.OfferingResponse.class))
                        }),
                        @APIResponse(responseCode = "404", description = "Not Found", content = {
                                        @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(oneOf = {
                                                        com.dofusdude.almanax.boundary.responses.errors.BonusTypeNotFoundResponse.class,
                                                        com.dofusdude.almanax.boundary.responses.errors.DateNotFoundResponse.class,
                                                        com.dofusdude.almanax.boundary.responses.errors.TimezoneResponse.class
                                        }))
                        })
        })
        @GET
        @Path("v1/{language}/ahead/{days_ahead}/bonus/{bonus_type}")
        @Counted(name = "daysAheadBonusFilterV1Count", description = "Days ahead with bonus filter, with versioning.")
        @Retry
        @CircuitBreaker
        @Timed(name = "daysAheadBonusFilterV1Timing")
        public Response daysAheadBonusFilterV1(@PathParam("days_ahead") Integer daysAhead,
                        @PathParam("language") String language,
                        @PathParam("bonus_type") String bonusType,
                        @QueryParam("timezone") String timezone) {
                // Prepare responses.
                ApiResponse response = new ApiResponse(version, language);
                OfferingResponse res = new OfferingResponse(response);
                errorProducer.setBase(response);

                // Get data.
                LocalDate startDate;
                if (timezone == null || timezone.isEmpty()) {
                        startDate = LocalDate.now(ZoneId.of(configTimezone));
                } else {
                        try {
                                startDate = LocalDate.now(ZoneId.of(timezone));
                        } catch (Exception e) {
                                return Response.status(Response.Status.BAD_REQUEST)
                                                .entity(errorProducer.timezone())
                                                .build();
                        }
                }
                LocalDate endDate = startDate.plusDays(daysAhead);

                // check existing language
                if (!LanguageHelper.validLanguage(language)) {
                        return Response.status(Response.Status.NOT_FOUND)
                                        .entity(errorProducer.languageNotFound())
                                        .build();
                }

                List<OfferingDTO> offerings;
                try {
                        offerings = offeringRepository.listFromDateRangeWithBonusDTO(startDate, endDate, bonusType,
                                        language);
                        if (offerings.isEmpty()) {
                                return Response.status(Response.Status.NOT_FOUND)
                                                .entity(errorProducer.dateNotFound())
                                                .build();
                        }
                } catch (BonusTypeNotFoundException e) {
                        return Response.status(Response.Status.NOT_FOUND)
                                        .entity(errorProducer.bonusTypeNotFound())
                                        .build();
                }

                res.data = offerings;
                return Response.ok(res).build();
        }

        /** ######################## DAYS AHEAD ITEMS ######################## **/
        @Tag(name = "Days ahead")
        @Operation(summary = "Get only the needed items.")
        @Parameters({
                        @Parameter(name = "language", in = ParameterIn.PATH, example = "en", description = "Language as code of length 2."),
                        @Parameter(name = "days_ahead", in = ParameterIn.PATH, example = "1", description = "Number of days ahead. 0 = today, 1 = tomorrow ..."),
                        @Parameter(name = "timezone", in = ParameterIn.QUERY, description = "Default is Europe/Paris. Insert something random to see all available.")
        })
        @APIResponses({
                        @APIResponse(responseCode = "200", description = "OK", content = {
                                        @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(oneOf = com.dofusdude.almanax.boundary.responses.ItemsResponse.class))
                        }),
                        @APIResponse(responseCode = "404", description = "Not Found", content = {
                                        @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(oneOf = {
                                                        com.dofusdude.almanax.boundary.responses.errors.LanguageNotFoundResponse.class,
                                                        com.dofusdude.almanax.boundary.responses.errors.DateNotFoundResponse.class,
                                                        com.dofusdude.almanax.boundary.responses.errors.TimezoneResponse.class
                                        }))
                        })
        })
        @GET
        @Path("{language}/ahead/{days_ahead}/items")
        @Counted(name = "daysAheadItemsRelaisCount", description = "Days ahead with items and without versioning.")
        @Retry
        @CircuitBreaker
        @Timed(name = "daysAheadItemsRelaisTiming")
        public Response daysAheadItems(@PathParam("days_ahead") Integer daysAhead,
                        @PathParam("language") String language,
                        @QueryParam("timezone") String timezone) {
                return daysAheadItemsV1(daysAhead, language, timezone);
        }

        @Tag(name = "Version 1.0 stable")
        @Operation(summary = "Get only the needed items.")
        @Parameters({
                        @Parameter(name = "language", in = ParameterIn.PATH, example = "en", description = "Language as code of length 2."),
                        @Parameter(name = "days_ahead", in = ParameterIn.PATH, example = "1", description = "Number of days ahead. 0 = today, 1 = tomorrow ..."),
                        @Parameter(name = "timezone", in = ParameterIn.QUERY, description = "Default is Europe/Paris. Insert something random to see all available.")
        })
        @APIResponses({
                        @APIResponse(responseCode = "200", description = "OK", content = {
                                        @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(oneOf = com.dofusdude.almanax.boundary.responses.ItemsResponse.class))
                        }),
                        @APIResponse(responseCode = "404", description = "Not Found", content = {
                                        @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(oneOf = {
                                                        com.dofusdude.almanax.boundary.responses.errors.LanguageNotFoundResponse.class,
                                                        com.dofusdude.almanax.boundary.responses.errors.DateNotFoundResponse.class,
                                                        com.dofusdude.almanax.boundary.responses.errors.TimezoneResponse.class
                                        }))
                        })
        })
        @GET
        @Path("v1/{language}/ahead/{days_ahead}/items")
        @Counted(name = "daysAheadItemsV1Count", description = "Days ahead with items and with versioning.")
        @Retry
        @CircuitBreaker
        @Timed(name = "daysAheadItemsV1Timing")
        public Response daysAheadItemsV1(@PathParam("days_ahead") Integer daysAhead,
                        @PathParam("language") String language,
                        @QueryParam("timezone") String timezone) {
                // Prepare responses.
                ApiResponse response = new ApiResponse(version, language);
                ItemsResponse res = new ItemsResponse(response);
                errorProducer.setBase(response);

                // Get data.
                LocalDate startDate;
                if (timezone == null || timezone.isEmpty()) {
                        startDate = LocalDate.now(ZoneId.of(configTimezone));
                } else {
                        try {
                                startDate = LocalDate.now(ZoneId.of(timezone));
                        } catch (Exception e) {
                                return Response.status(Response.Status.BAD_REQUEST)
                                                .entity(errorProducer.timezone())
                                                .build();
                        }
                }
                LocalDate endDate = startDate.plusDays(daysAhead);

                // check existing language
                if (!LanguageHelper.validLanguage(language)) {
                        return Response.status(Response.Status.NOT_FOUND)
                                        .entity(errorProducer.languageNotFound())
                                        .build();
                }

                List<OfferingDTO> offerings = offeringRepository.listFromDateRangeDTO(startDate, endDate, language);
                if (offerings.isEmpty()) {
                        return Response.status(Response.Status.NOT_FOUND)
                                        .entity(errorProducer.dateNotFound())
                                        .build();
                }

                Map<String, Integer> itemMap = new HashMap<>();
                List<ItemPositionDTO> items = new ArrayList<>();
                offerings.forEach(el -> {
                        // already in map
                        if (itemMap.containsKey(el.itemObject.name)) {
                                itemMap.replace(el.itemObject.name, itemMap.get(el.itemObject.name) + el.itemQuantity);
                        } else {
                                // new in map
                                itemMap.put(el.itemObject.name, el.itemQuantity);
                        }
                });

                // distinct elements
                itemMap.keySet().forEach(item -> {
                        ItemPositionDTO itemPosition = new ItemPositionDTO();
                        itemPosition.name = item;
                        itemPosition.quantity = itemMap.get(item);
                        items.add(itemPosition);
                });

                res.data = items;
                return Response.ok(res).build();
        }

        /** ######################## NEXT BONUS TYPE ######################## **/
        @Tag(name = "Next Bonus", description = "Find the next offering from now with a specific bonus.")
        @Parameters({
                        @Parameter(name = "language", in = ParameterIn.PATH, example = "en", description = "Language as code of length 2."),
                        @Parameter(name = "bonus_type", in = ParameterIn.PATH, example = "full-of-life", description = "Bonustype to filter. always english in lowercase and '-' instead of ' ' for readability."),
                        @Parameter(name = "timezone", in = ParameterIn.QUERY, description = "Default is Europe/Paris. Insert something random to see all available.")
        })
        @APIResponses({
                        @APIResponse(responseCode = "200", description = "OK", content = {
                                        @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(oneOf = com.dofusdude.almanax.boundary.responses.SingleOfferingResponse.class))
                        }),
                        @APIResponse(responseCode = "404", description = "Not Found", content = {
                                        @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(oneOf = {
                                                        com.dofusdude.almanax.boundary.responses.errors.LanguageNotFoundResponse.class,
                                                        com.dofusdude.almanax.boundary.responses.errors.BonusTypeNotFoundResponse.class,
                                                        com.dofusdude.almanax.boundary.responses.errors.NoOfferingWithBonusResponse.class,
                                                        com.dofusdude.almanax.boundary.responses.errors.TimezoneResponse.class
                                        }))
                        })
        })
        @GET
        @Path("{language}/bonus/{bonus_type}/next")
        @Counted(name = "nextBonusTypeRelaisCount", description = "Next day with specific bonus without versioning.")
        public Response nextBonusType(@PathParam("bonus_type") String bonusType,
                        @PathParam("language") String language,
                        @Context UriInfo uriInfo,
                        @QueryParam("timezone") String timezone) {
                return nextBonusTypeV1(bonusType, language, uriInfo, timezone);
        }

        @Tag(name = "Version 1.0 stable")
        @Operation(summary = "Find the next offering from now with a specific bonus.")
        @Parameters({
                        @Parameter(name = "language", in = ParameterIn.PATH, example = "en", description = "Language as code of length 2."),
                        @Parameter(name = "bonus_type", in = ParameterIn.PATH, example = "full-of-life", description = "Bonustype to filter. always english in lowercase and '-' instead of ' ' for readability."),
                        @Parameter(name = "timezone", in = ParameterIn.QUERY, description = "Default is Europe/Paris. Insert something random to see all available.")
        })
        @APIResponses({
                        @APIResponse(responseCode = "200", description = "OK", content = {
                                        @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(oneOf = com.dofusdude.almanax.boundary.responses.SingleOfferingResponse.class))
                        }),
                        @APIResponse(responseCode = "404", description = "Not Found", content = {
                                        @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(oneOf = {
                                                        com.dofusdude.almanax.boundary.responses.errors.LanguageNotFoundResponse.class,
                                                        com.dofusdude.almanax.boundary.responses.errors.BonusTypeNotFoundResponse.class,
                                                        com.dofusdude.almanax.boundary.responses.errors.NoOfferingWithBonusResponse.class,
                                                        com.dofusdude.almanax.boundary.responses.errors.TimezoneResponse.class
                                        }))
                        })
        })
        @GET
        @Path("v1/{language}/bonus/{bonus_type}/next")
        @Counted(name = "nextBonusTypeV1Count", description = "Next day with specific bonus with versioning.")
        @Retry
        @CircuitBreaker
        @Timed(name = "nextBonusTypeV1Timing")
        public Response nextBonusTypeV1(@PathParam("bonus_type") String bonusType,
                        @PathParam("language") String language,
                        @Context UriInfo uriInfo,
                        @QueryParam("timezone") String timezone) {
                // Prepare responses.
                ApiResponse response = new ApiResponse(version, language);
                SingleOfferingResponse res = new SingleOfferingResponse(response);
                errorProducer.setBase(response);

                // check existing language
                if (!LanguageHelper.validLanguage(language)) {
                        return Response.status(Response.Status.NOT_FOUND)
                                        .entity(errorProducer.languageNotFound())
                                        .build();
                }

                // Get data.
                LocalDate startDate;
                if (timezone == null || timezone.isEmpty()) {
                        startDate = LocalDate.now(ZoneId.of(configTimezone));
                } else {
                        try {
                                startDate = LocalDate.now(ZoneId.of(timezone));
                        } catch (Exception e) {
                                return Response.status(Response.Status.BAD_REQUEST)
                                                .entity(errorProducer.timezone())
                                                .build();
                        }
                }

                Optional<OfferingDTO> offering;
                try {
                        offering = offeringRepository.nextOfferingWithBonusDTO(startDate, bonusType, language);
                        if (offering.isEmpty()) {
                                return Response.status(Response.Status.NOT_FOUND)
                                                .entity(errorProducer.noOfferingWithBonus())
                                                .build();
                        }
                } catch (BonusTypeNotFoundException e) {
                        return Response.status(Response.Status.NOT_FOUND)
                                        .entity(errorProducer.bonusTypeNotFound())
                                        .build();
                }

                res.data = offering.get();
                return Response.ok(res).build();
        }

}
