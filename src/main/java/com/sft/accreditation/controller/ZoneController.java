/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sft.accreditation.controller;

import com.jmoordb.core.model.Search;
import com.jmoordb.core.util.ConsoleUtil;
import com.jmoordb.core.util.DocumentUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.sft.model.History;
import com.sft.model.Zone;
import com.sft.repository.HistoryRepository;
import com.sft.repository.ZoneRepository;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.eclipse.microprofile.metrics.MetricUnits;
 
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

/**
 *
 * @author avbravo
 */
@Path("zone")
@Tag(name = "Información del zone", description = "End-point para entidad Zone")
@RolesAllowed({"admin"})
public class ZoneController {

    
    // <editor-fold defaultstate="collapsed" desc="Inject">
    @Inject
    ZoneRepository zoneRepository;
    
 
     @Inject
HistoryRepository historyRepository;
  

// </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="findAll">
    @GET
    @RolesAllowed({"admin"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Timed(name = "zoneesFindAll",
            description = "Monitorea el tiempo en que se obtiene la lista de todos los zonees",
            unit = MetricUnits.MILLISECONDS, absolute = true)
    @Operation(summary = "Obtiene todos los zonees", description = "Retorna todos los zonees disponibles")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @APIResponse(responseCode = "200", description = "Los zonees")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "Los zonees", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Collection.class, readOnly = true, description = "los zonees", required = true, name = "zonees")))
    public List<Zone> findAll() {
        
        return zoneRepository.findAll();
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Zone findByIdzone">
    @GET
    @RolesAllowed({"admin"})
    @Path("{idzone}")
    @Operation(summary = "Busca un zone por el idzone", description = "Busqueda de zone por idzone")
    @APIResponse(responseCode = "200", description = "El zone")
    @APIResponse(responseCode = "404", description = "Cuando no existe el idzone")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El zone", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Zone.class)))
    public Zone findByIdzone(
            @Parameter(description = "El idzone", required = true, example = "1", schema = @Schema(type = SchemaType.NUMBER)) @PathParam("idzone") Long idzone) {

 

        return zoneRepository.findByPk(idzone).orElseThrow(
                () -> new WebApplicationException("No hay zone con idzone " + idzone, Response.Status.NOT_FOUND));

    }
// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="List<Zone> lookup(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size)">

    @GET
    @Path("lookup")
    @RolesAllowed({"admin"})
    @Operation(summary = "Busca un appconfiguration", description = "Busqueda de user por search")
    @APIResponse(responseCode = "200", description = "Zone")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Zone.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public List<Zone> lookup(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size) {
        List<Zone> suggestions = new ArrayList<>();
        try {

        Search search = DocumentUtil.convertForLookup(filter, sort, page, size);
        suggestions = zoneRepository.lookup(search);

        } catch (Exception e) {
       
          MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + "error: " + e.getLocalizedMessage());
        }

        return suggestions;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Response save">
    @POST
    @RolesAllowed({"admin"})
    @Operation(summary = "Inserta un nuevo zone", description = "Inserta un nuevo zone")
    @APIResponse(responseCode = "201", description = "Cuanoo se crea un  zone")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response save(
            @RequestBody(description = "Crea un nuevo zone.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Zone.class))) Zone zone) {


         Optional<Zone> zoneOptional=zoneRepository.save(zone);
        if(zoneOptional.isPresent()){
            saveHistory(zone);
               return Response.status(201).entity(zoneOptional.get()).build();
        }else{
              return Response.status(400).entity("Error " + zoneRepository.getJmoordbException().getLocalizedMessage()).build();
        }
 
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Response update">

    @PUT
    @RolesAllowed({"admin"})
    @Operation(summary = "Inserta un nuevo zone", description = "Inserta un nuevo zone")
    @APIResponse(responseCode = "201", description = "Cuanoo se crea un  zone")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response update(
            @RequestBody(description = "Crea un nuevo zone.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Zone.class))) Zone zone) {


        if(zoneRepository.update(zone)){
            saveHistory(zone);
               return Response.status(201).entity(zone).build();
        }else{
              return Response.status(400).entity("Error " + zoneRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Response delete">
    @DELETE
    @RolesAllowed({"admin"})
    @Path("{idzone}")
    @Operation(summary = "Elimina un zone por  idzone", description = "Elimina un zone por su idzone")
    @APIResponse(responseCode = "200", description = "Cuando elimina el zone")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response delete(
            @Parameter(description = "El elemento idzone", required = true, example = "1", schema = @Schema(type = SchemaType.NUMBER)) @PathParam("idzone") Long idzone) {
       if(zoneRepository.deleteByPk(idzone) ==0L){
              return Response.status(201).entity(Boolean.TRUE).build();
        }else{
            return Response.status(400).entity("Error " + zoneRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
    // </editor-fold>
    
     // <editor-fold defaultstate="collapsed" desc="Long count(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size)">

    @GET
    @Path("count")
    @RolesAllowed({"admin"})
    @Operation(summary = "Cuenta ", description = "Cuenta zone")
    @APIResponse(responseCode = "200", description = "contador")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Zone.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public Long count(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size) {
       Long result = 0L;
        try {

        Search search = DocumentUtil.convertForLookup(filter, sort, page, size);
        result = zoneRepository.count(search);

        } catch (Exception e) {
       
          MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + "error: " + e.getLocalizedMessage());
        }

        return result;
    }

    // </editor-fold>
         // <editor-fold defaultstate="collapsed" desc="private void saveHistory(Zone zone)">
    
    private void saveHistory(Zone zone){
        try {
                History history = new History.Builder()                 
               .collection("zone")
                    .idcollection(zone.getIdzone().toString())
                    .database("accreditation")
                    .data(zone.toString())
                    .actionHistory(zone.getActionHistory().get(zone.getActionHistory().size()-1)                  )
                     .build();
            historyRepository.save(history);
        } catch (Exception e) {
           ConsoleUtil.error("saveHistory() "+e.getLocalizedMessage());
        }
    }
     
    
// </editor-fold>
}
