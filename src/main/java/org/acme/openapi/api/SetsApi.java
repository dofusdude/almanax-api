package org.acme.openapi.api;

import org.acme.openapi.model.EquipmentSet;
import org.acme.openapi.model.SetListEntry;
import org.acme.openapi.model.SetsListPaged;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import javax.enterprise.context.ApplicationScoped;

import io.quarkiverse.openapi.generator.annotations.GeneratedClass;
import io.quarkiverse.openapi.generator.annotations.GeneratedMethod;
import io.quarkiverse.openapi.generator.annotations.GeneratedParam;

/**
  * Dofusdude API
  * <p/>The last API for everything Dofus <span style=\"font-size:1.8rem;\">🤯</span> <h2>Main Features</h2> <ul> <li><span style=\"font-size:1.8rem;\">🥷</span> <b>seamless auto-update</b> load data in the background when a new Dofus version is released and serving it within 2 minutes with atomic data source switching. No downtime and no effects for the user, just always up-to-date.</li>  <li><span style=\"font-size:1.8rem;\">⚡</span> <b>blazingly fast</b> all data in-memory, aggressive caching over short time spans, HTTP/2 multiplexing, written in Go, optimized for low latency, hosted on bare metal in <span style=\"font-size:1.8rem;\">🇩🇪</span>.</li>  <li><span style=\"font-size:1.8rem;\">🗣️</span> <b>multilingual</b> supporting <em>en</em>, <em>fr</em>, <em>es</em>, <em>pt</em> including the dropped languages from the Dofus website <em>de</em> and <em>it</em>.</li>  <li><span style=\"font-size:1.8rem;\">🧠</span> <b>search by relevance</b> allowing typos in name and description, handled by language specific text analysis and indexing by the powerful <a href=\"https://www.meilisearch.com\">Meilisearch</a> written in Rust.</li>  <li><span style=\"font-size:1.8rem;\">🕵️</span> <b>complete</b> actual data from the game including items invisible to the encyclopedia like quest items.</li>  <li><span style=\"font-size:1.8rem;\">🖼️</span> <b>HD images</b> rendering vector graphics into PNGs up to 800x800 px in the background.</li>  </ul>   <h2>Current state</h2> <div style=\"display:flex;justify-content:space-around;flex-wrap:wrap\"> <ul style=\"font-size:1.6rem;\"> <li>Weapons ✅</li> <li>Equipment ✅</li> <li>Sets ✅</li> <li>Resources ✅</li> <li>Consumables ✅</li> <li>Pets ✅</li> <li>Mounts ✅</li> <li>Cosmetics/Ceremonial Items ✅</li> <li>Harnesses ✅</li> <li>Quest Items ✅</li> </ul>  <ul style=\"font-size:1.6rem;\"> <li>🏗️ Almanax ❌</li> <li>Monsters ❌</li> <li>Classes ❌</li> <li>Spells ❌</li> </ul> </div>   <h3>Maybes? I don't know what for <span style=\"font-size:1.8rem;\">🤷</span></h3> <ul> <li>Professions ❌ </li> <li>Sidekicks ❌</li> <li>Haven Bags ❌</li> <li>Map ❌</li> </ul>   <h2>Future</h2> I want this project to be useful and not just add plain categories no one needs. More and more features will be added to enhance the quality based on the needs of you, the developers. <br>Examples: <p><em>I need to know where I can drop the all the items I need to craft set X!</em></p> <p><em>Please get a detailed always up-to-date spell description so I can calculate the damage for my set builder site!</em></p> <p>Nearly everything can be done. But I want to make sure somebody also wants it. If you have anything or you are just interested in the project, join the <a href=\"https://discord.gg/3EtHskZD8h\">Discord</a>.</p>  <h3>Versioning</h3> <p>Updating an API is a hard problem. This is why we'll keep it simple:  Everything you see here on this site, you can use now and forever. Updates could introduce new fields, new paths or parameter but never break backwards compatibility, so no field or parameter will be deleted. Ever. </p> <p>There is one exception! <b>The API will <em>always</em> choose being up-to-date over everything else</b>. So if Ankama decides to drop languages from the game like they did with their website, the API will loose support for them, too. </p> <p> We can prevent this specific use case with a nice community but even then, it would be hidden behind a feature flag. </p>  <h2>Get started! 🥳</h2> <p>Scroll down and try it for yourself.</p> <p>If you are ready to use them in your project, think about <a href=\"https://github.com/OpenAPITools/openapi-generator\">generating a client <span style=\"font-size:1.8rem;\">🧙</span></a> from the <a href=\"https://dofusdu.de/swagger.yaml\">OpenAPI 3.0 Spec</a> to get started quickly.</p>   
  */
@Path("/{language}/sets")
@RegisterRestClient(baseUri="https://api.dofusdu.de/dofus2", configKey="dofusdude_yaml")
@GeneratedClass(value="dofusdude.yaml", tag = "Sets")
@ApplicationScoped
public interface SetsApi {

    /**
     * List Sets
     *
     * Retrieve a list of sets.
     *
     */
    @GET
    @Produces({"application/json"})
    @GeneratedMethod ("getSetsList")
    public SetsListPaged getSetsList(
        @GeneratedParam("language") @PathParam("language") String language, 
        @GeneratedParam("sort[level]") @QueryParam("sort[level]") String sortLevel, 
        @GeneratedParam("filter[min_highest_equipment_level]") @QueryParam("filter[min_highest_equipment_level]") Integer filterMinHighestEquipmentLevel, 
        @GeneratedParam("filter[max_highest_equipment_level]") @QueryParam("filter[max_highest_equipment_level]") Integer filterMaxHighestEquipmentLevel, 
        @GeneratedParam("page[size]") @QueryParam("page[size]") Integer pageSize, 
        @GeneratedParam("page[number]") @QueryParam("page[number]") Integer pageNumber
    );


    /**
     * Search Sets
     *
     * Search in all names and descriptions of sets with a query.
     *
     */
    @GET
    @Path("/search")
    @Produces({"application/json"})
    @GeneratedMethod ("getSetsSearch")
    public List<SetListEntry> getSetsSearch(
        @GeneratedParam("language") @PathParam("language") String language, 
        @GeneratedParam("query") @QueryParam("query") String query, 
        @GeneratedParam("filter[min_highest_equipment_level]") @QueryParam("filter[min_highest_equipment_level]") Integer filterMinHighestEquipmentLevel, 
        @GeneratedParam("filter[max_highest_equipment_level]") @QueryParam("filter[max_highest_equipment_level]") Integer filterMaxHighestEquipmentLevel
    );


    /**
     * Single Sets
     *
     * Retrieve a specific set with id.
     *
     */
    @GET
    @Path("/{ankama_id}")
    @Produces({"application/json"})
    @GeneratedMethod ("getSetsSingle")
    public EquipmentSet getSetsSingle(
        @GeneratedParam("language") @PathParam("language") String language, 
        @GeneratedParam("ankama_id") @PathParam("ankama_id") Integer ankamaId
    );


}