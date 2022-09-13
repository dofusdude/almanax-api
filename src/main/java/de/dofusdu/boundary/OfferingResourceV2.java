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

package de.dofusdu.boundary;

import de.dofusdu.boundary.responses.ApiResponse;
import de.dofusdu.boundary.responses.errors.ErrorProducer;
import de.dofusdu.dto.BonusTypeMapDTOV2;
import de.dofusdu.dto.CreateOfferingDTO;
import de.dofusdu.dto.OfferingDTOV2;
import de.dofusdu.exceptions.FirstDayNotEnglishException;
import de.dofusdu.gateway.OfferingRepository;
import de.dofusdu.memory.MemoryRepository;
import de.dofusdu.util.DateConverter;
import de.dofusdu.util.LanguageHelper;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/dofus2")
public class OfferingResourceV2 {

    private OfferingRepository offeringRepository;

    private final int version = 7;

    private final int defaultAhead = 7;

    @Inject
    @ConfigProperty(name = "almanax.max.ahead")
    Integer maxAhead;

    private final ErrorProducer errorProducer;

    @Inject
    @ConfigProperty(name = "admin.api.secret")
    String apiKey;

    @Inject
    @ConfigProperty(name = "timezone")
    String configTimezone;

    private Jsonb jsonb;

    private MemoryRepository memoryRepository;

    @Inject
    public OfferingResourceV2(OfferingRepository offeringRepository,
                              ErrorProducer errorProducer,
                              MemoryRepository memoryRepository) {
        this.offeringRepository = offeringRepository;
        this.errorProducer = errorProducer;
        this.memoryRepository = memoryRepository;
        jsonb = JsonbBuilder.create();
    }

    /**
     * ######################## API META ########################
     **/
    @GET
    @Path("meta/{language}/almanax/bonuses")
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Response bonusTypes(@PathParam("language") String language) {
        if (!LanguageHelper.validLanguage(language)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(errorProducer.languageNotFound())
                    .build();
        }

        return Response.ok(memoryRepository.getBonuses(language)).build();
    }


    /**
     * ######################## SINGLE DAY ########################
     **/
    @GET
    @Path("{language}/almanax/{date}")
    @Counted(name = "singleDayV2Count", description = "Single Day hits (date).")
    @Retry
    @CircuitBreaker
    @Timed(name = "singleDayV2Timing")
    public Response singleDayV2(@PathParam("language") String language,
                                @PathParam("date") String stringDate) {
        // Make date from string.
        LocalDate date;
        try {
            date = DateConverter.fromString(stringDate);
        } catch (DateTimeParseException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }

        // check existing language
        if (!LanguageHelper.validLanguage(language)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }


        Optional<OfferingDTOV2> offeringDTO = memoryRepository.getSingleDate(date, language);
        if (offeringDTO.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }

        return Response
                .ok(offeringDTO.get())
                .build();

    }


    private Optional<LocalDate> parseDate(String datestring) {
        try {
            LocalDate localDate = DateConverter.fromString(datestring);
            return Optional.of(localDate);
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    /**
     * ######################## DAYS RANGE ########################
     **/
    @GET
    @Path("{language}/almanax")
    @Transactional
    @Counted(name = "daysRangeCount", description = "Days range hits.")
    @Retry
    @CircuitBreaker
    @Timed(name = "daysRangeTiming")
    public Response daysRange(@PathParam("language") String language,
                              @QueryParam("timezone") String timezone,
                              @QueryParam("filter[bonus_type]") String bonusType,
                              @QueryParam("range[from]") String fromDateString,
                              @QueryParam("range[to]") String toDateString,
                              @DefaultValue("-1") @QueryParam("range[size]") int rangeSize) {
        if (!LanguageHelper.validLanguage(language)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }

        if (timezone != null && !timezone.isEmpty()) {
            try {
                LocalDate.now(ZoneId.of(timezone));
            } catch (Exception e) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .build();
            }
        }


        LocalDate fromDate = LocalDate.now(ZoneId.of(configTimezone));
        LocalDate toDate = fromDate.plusDays(defaultAhead);

        boolean givenFromDate = fromDateString != null && !fromDateString.isEmpty();
        boolean givenToDate = toDateString != null && !toDateString.isEmpty();
        boolean givenRangeSize = rangeSize > 0;

        if (givenRangeSize && givenFromDate && givenToDate) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }

        if (givenRangeSize && !givenFromDate && !givenToDate) {
            fromDate = LocalDate.now(ZoneId.of(configTimezone));
            toDate = fromDate.plusDays(rangeSize);
        } else {
            if (givenFromDate && givenToDate) {
                Optional<LocalDate> localDate = parseDate(fromDateString);
                if (localDate.isEmpty()) {
                    return Response.status(Response.Status.BAD_REQUEST)
                            .build();
                }
                fromDate = localDate.get();

                Optional<LocalDate> localDate1 = parseDate(toDateString);
                if (localDate1.isEmpty()) {
                    return Response.status(Response.Status.BAD_REQUEST)
                            .build();
                }
                toDate = localDate1.get();

                toDate = toDate.plusDays(1);
            }

            if (givenFromDate && !givenToDate) {
                Optional<LocalDate> localDate = parseDate(fromDateString);
                if (localDate.isEmpty()) {
                    return Response.status(Response.Status.BAD_REQUEST)
                            .build();
                }
                fromDate = localDate.get();

                toDate = fromDate.plusDays(defaultAhead);
            }

            if (!givenFromDate && givenToDate) {
                Optional<LocalDate> localDate = parseDate(toDateString);
                if (localDate.isEmpty()) {
                    return Response.status(Response.Status.BAD_REQUEST)
                            .build();
                }
                toDate = localDate.get().plusDays(1);
            }
        }

        if (givenRangeSize && givenFromDate) {
            toDate = fromDate.plusDays(rangeSize);
        }

        if (givenRangeSize && givenToDate) {
            Optional<LocalDate> localDate = parseDate(toDateString);
            if (localDate.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .build();
            }
            toDate = localDate.get().plusDays(1);
            fromDate = toDate.minusDays(rangeSize);
        }

        if (toDate.isBefore(fromDate)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }

        if (java.time.temporal.ChronoUnit.DAYS.between(fromDate, toDate) > maxAhead) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }

        Collection<OfferingDTOV2> offerings = new ArrayList<>();
        for (LocalDate date = fromDate; date.isBefore(toDate); date = date.plusDays(1)) {
            Optional<OfferingDTOV2> offeringDTO = memoryRepository.getSingleDate(date, language);
            if (offeringDTO.isPresent()) {
                offerings.add(offeringDTO.get());
            }
        }

        if (bonusType != null && !bonusType.isEmpty()) {
            Optional<BonusTypeMapDTOV2> bonusTypeOptional = memoryRepository.getBonuses(language).stream()
                    .filter(bonusType1 -> bonusType1.id.equals(bonusType))
                    .findFirst();

            if (bonusTypeOptional.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .build();
            }

            offerings = offerings.stream()
                    .filter(offeringDTO -> offeringDTO.bonus.type.id.equals(bonusType))
                    .collect(Collectors.toList());
        }
        return Response.ok(offerings).build();

    }

    @POST
    @Path("almanax")
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    @Counted(name = "createCount", description = "Hits on creation endpoint (only english).")
    @Timed(name = "createTiming")
    public Response create(@HeaderParam("Authorization") String apiKey, CreateOfferingDTO offeringDTO) {
        ApiResponse res = new ApiResponse(version, offeringDTO.language);
        errorProducer.setBase(res);

        if (apiKey == null || !apiKey.equals("Bearer " + this.apiKey)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(errorProducer.unautherized())
                    .build();
        }

        try {
            offeringRepository.persist(offeringDTO, offeringDTO.language, false);
        } catch (FirstDayNotEnglishException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE)
                    .entity(errorProducer.englishDayFirst())
                    .build();
        }

        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    @Path("almanax/translate")
    public Response updateTranslations(@HeaderParam("Authorization") String apiKey,
                                       CreateOfferingDTO offeringDTO) {
        // Prepare responses.
        ApiResponse response = new ApiResponse(2, offeringDTO.language);
        errorProducer.setBase(response);

        if (apiKey == null || !apiKey.equals("Bearer " + this.apiKey)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(errorProducer.unautherized())
                    .build();
        }

        try {
            offeringRepository.updateTranslation(offeringDTO);
        } catch (FirstDayNotEnglishException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE)
                    .entity(errorProducer.noEntryForDate())
                    .build();
        }

        return Response.ok().build();
    }

    @PUT
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    @Path("almanax")
    public Response update(@HeaderParam("Authorization") String apiKey,
                           CreateOfferingDTO offeringDTO) {
        // Prepare responses.
        ApiResponse response = new ApiResponse(version, offeringDTO.language);
        errorProducer.setBase(response);

        if (apiKey == null || !apiKey.equals("Bearer " + this.apiKey)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(errorProducer.unautherized())
                    .build();
        }

        boolean res;
        try {
            res = offeringRepository.update(offeringDTO);
        } catch (FirstDayNotEnglishException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE)
                    .entity(errorProducer.noEntryForDate())
                    .build();
        }

        return res ? Response.status(Response.Status.CREATED).build() : Response.ok().build();
    }
}