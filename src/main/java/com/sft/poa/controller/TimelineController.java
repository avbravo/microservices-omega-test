/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sft.poa.controller;

import com.jmoordb.core.model.Search;
import com.jmoordb.core.util.ConsoleUtil;
import com.jmoordb.core.util.DocumentUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.sft.model.Timeline;
import com.sft.repository.TimelineRepository;
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
import java.util.Date;
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
@Path("timeline")
@Tag(name = "Información del timeline", description = "End-point para entidad Timeline")
  @RolesAllowed({"admin"})
public class TimelineController {

    
    // <editor-fold defaultstate="collapsed" desc="Inject">
    @Inject
    TimelineRepository timelineRepository;
    
 

 

// </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="findAll">
    @GET
      @RolesAllowed({"admin"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Timed(name = "timelineFindAll",
            description = "Monitorea el tiempo en que se obtiene la lista de todos los timeline",
            unit = MetricUnits.MILLISECONDS, absolute = true)
    @Operation(summary = "Obtiene todos los timeline", description = "Retorna todos los timeline disponibles")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @APIResponse(responseCode = "200", description = "Los timeline")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "Los timeline", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Collection.class, readOnly = true, description = "los timeline", required = true, name = "timeline")))
    public List<Timeline> findAll() {
        
        return timelineRepository.findAll();
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Timeline findByIdtimeline">
    @GET
      @RolesAllowed({"admin"})
    @Path("{idtimeline}")
    @Operation(summary = "Busca un timeline por el idtimeline", description = "Busqueda de timeline por idtimeline")
    @APIResponse(responseCode = "200", description = "El timeline")
    @APIResponse(responseCode = "404", description = "Cuando no existe el idtimeline")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El timeline", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Timeline.class)))
    public Timeline findByIdtimeline(
            @Parameter(description = "El idtimeline", required = true, example = "1", schema = @Schema(type = SchemaType.NUMBER)) @PathParam("idtimeline") Long idtimeline) {
     

        return timelineRepository.findByPk(idtimeline).orElseThrow(
                () -> new WebApplicationException("No hay timeline con idtimeline " + idtimeline, Response.Status.NOT_FOUND));

    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Response save">
    @POST
      @RolesAllowed({"admin"})

    @Operation(summary = "Inserta un nuevo timeline", description = "Inserta un nuevo timeline")
    @APIResponse(responseCode = "201", description = "Cuanoo se crea un  timeline")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response save(
            @RequestBody(description = "Crea un nuevo timeline.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Timeline.class))) Timeline timeline) {
        Optional<Timeline> timelineOptional=timelineRepository.save(timeline);
        if(timelineOptional.isPresent()){
               return Response.status(201).entity(timelineOptional.get()).build();
        }else{
              return Response.status(400).entity("Error " + timelineRepository.getJmoordbException().getLocalizedMessage()).build();
        }
 
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Response update">

    @PUT
      @RolesAllowed({"admin"})
    @Operation(summary = "Inserta un nuevo timeline", description = "Inserta un nuevo timeline")
    @APIResponse(responseCode = "201", description = "Cuanoo se crea un  timeline")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response update(
            @RequestBody(description = "Crea un nuevo timeline.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Timeline.class))) Timeline timeline) {


        if(timelineRepository.update(timeline)){
               return Response.status(201).entity(timeline).build();
        }else{
              return Response.status(400).entity("Error " + timelineRepository.getJmoordbException().getLocalizedMessage()).build();
        }
        
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Response delete">
    @DELETE
    @RolesAllowed({"admin"})
    @Path("{idtimeline}")
    @Operation(summary = "Elimina un timeline por  idtimeline", description = "Elimina un timeline por su idtimeline")
    @APIResponse(responseCode = "200", description = "Cuando elimina el timeline")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response delete(
            @Parameter(description = "El elemento idtimeline", required = true, example = "1", schema = @Schema(type = SchemaType.NUMBER)) @PathParam("idtimeline") Long idtimeline) {
        if(timelineRepository.deleteByPk(idtimeline) ==0L){
              return Response.status(201).entity(Boolean.TRUE).build();
        }else{
            return Response.status(400).entity("Error " + timelineRepository.getJmoordbException().getLocalizedMessage()).build();
        }
      
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="List<Timeline> lookup(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size)">

    @GET
    @Path("lookup")
    @RolesAllowed({"admin"})
    @Operation(summary = "Busca un timeline", description = "Busqueda de timeline por search")
    @APIResponse(responseCode = "200", description = "Timeline")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Timeline.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public List<Timeline> lookup(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size) {
        List<Timeline> suggestions = new ArrayList<>();
        try {

            
            
                            
        Search search = DocumentUtil.convertForLookup(filter, sort, page, size);
        suggestions = timelineRepository.lookup(search);

        } catch (Exception e) {
       
          MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + "error: " + e.getLocalizedMessage());
        }

        return suggestions;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Long count(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size)">

    @GET
    @Path("count")
    @RolesAllowed({"admin"})
    @Operation(summary = "Cuenta ", description = "Cuenta timeline")
    @APIResponse(responseCode = "200", description = "contador")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Timeline.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public Long count(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size) {
       Long result = 0L;
        try {

        Search search = DocumentUtil.convertForLookup(filter, sort, page, size);
        result = timelineRepository.count(search);

        } catch (Exception e) {
       
          MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + "error: " + e.getLocalizedMessage());
        }

        return result;
    }

    // </editor-fold>
    
}
