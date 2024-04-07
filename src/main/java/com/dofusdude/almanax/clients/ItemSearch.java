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

package com.dofusdude.almanax.clients;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.dofusdude.almanax.dto.SearchResult;
import com.dofusdude.almanax.dto.encyclopedia.*;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "itemsearch")
public interface ItemSearch {

        @GET
        @Path("/dofus/{language}/search/{search_string}")
        SearchResult searchItem(@PathParam("language") String language,
                        @PathParam("search_string") String searchString,
                        @QueryParam("threshold") int threshold);

        @GET
        @Path("/dofus/{language}/resources/{ankama_id}")
        ResourceDTO getResource(@PathParam("language") String language,
                        @PathParam("ankama_id") Long ankamaId);

        @GET
        @Path("/dofus/{language}/consumables/{ankama_id}")
        ConsumableDTO getConsumable(@PathParam("language") String language,
                        @PathParam("ankama_id") Long ankamaId);

        @GET
        @Path("/dofus/{language}/equipment/{ankama_id}")
        EquipmentDTO getEquipment(@PathParam("language") String language,
                        @PathParam("ankama_id") Long ankamaId);

        @GET
        @Path("/dofus/{language}/weapons/{ankama_id}")
        WeaponDTO getWeapon(@PathParam("language") String language,
                        @PathParam("ankama_id") Long ankamaId);

        @GET
        @Path("/dofus/{language}/pets/{ankama_id}")
        PetDTO getPet(@PathParam("language") String language,
                        @PathParam("ankama_id") Long ankamaId);
}
