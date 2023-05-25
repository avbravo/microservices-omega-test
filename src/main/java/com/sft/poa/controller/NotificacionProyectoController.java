/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sft.poa.controller;

import com.jmoordb.core.model.Search;
import com.jmoordb.core.util.ConsoleUtil;
import com.jmoordb.core.util.DocumentUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.sft.model.NotificacionProyecto;
import com.sft.repository.HistoryRepository;
import com.sft.repository.NotificacionProyectoRepository;
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
@Path("notificacionProyecto")
@Tag(name = "Información del notificacionProyecto", description = "End-point para entidad NotificacionProyecto")
  @RolesAllowed({"admin"})
public class NotificacionProyectoController {

    
    // <editor-fold defaultstate="collapsed" desc="Inject">
    @Inject
    NotificacionProyectoRepository notificacionProyectoRepository;
    
      @Inject
HistoryRepository historyRepository;

 

// </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="findAll">
    @GET
      @RolesAllowed({"admin"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Timed(name = "notificacionProyectoFindAll",
            description = "Monitorea el tiempo en que se obtiene la lista de todos los notificacionProyecto",
            unit = MetricUnits.MILLISECONDS, absolute = true)
    @Operation(summary = "Obtiene todos los notificacionProyecto", description = "Retorna todos los notificacionProyecto disponibles")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @APIResponse(responseCode = "200", description = "Los notificacionProyecto")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "Los notificacionProyecto", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Collection.class, readOnly = true, description = "los notificacionProyecto", required = true, name = "notificacionProyecto")))
    public List<NotificacionProyecto> findAll() {
      
        return notificacionProyectoRepository.findAll();
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="NotificacionProyecto findByIdnotificacionProyecto">
    @GET
      @RolesAllowed({"admin"})
    @Path("{idnotificacionProyecto}")
    @Operation(summary = "Busca un notificacionProyecto por el idnotificacionProyecto", description = "Busqueda de notificacionProyecto por idnotificacionProyecto")
    @APIResponse(responseCode = "200", description = "El notificacionProyecto")
    @APIResponse(responseCode = "404", description = "Cuando no existe el idnotificacionProyecto")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El notificacionProyecto", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = NotificacionProyecto.class)))
    public NotificacionProyecto findByIdnotificacionProyecto(
            @Parameter(description = "El idnotificacionProyecto", required = true, example = "1", schema = @Schema(type = SchemaType.NUMBER)) @PathParam("idnotificacionProyecto") Long idnotificacionProyecto) {
     

        return notificacionProyectoRepository.findByPk(idnotificacionProyecto).orElseThrow(
                () -> new WebApplicationException("No hay notificacionProyecto con idnotificacionProyecto " + idnotificacionProyecto, Response.Status.NOT_FOUND));

    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Response save">
    @POST
      @RolesAllowed({"admin"})
    @Operation(summary = "Inserta un nuevo notificacionProyecto", description = "Inserta un nuevo notificacionProyecto")
    @APIResponse(responseCode = "201", description = "Cuanoo se crea un  notificacionProyecto")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response save(
            @RequestBody(description = "Crea un nuevo notificacionProyecto.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotificacionProyecto.class))) NotificacionProyecto notificacionProyecto) {
        Optional<NotificacionProyecto> notificacionProyectoOptional=notificacionProyectoRepository.save(notificacionProyecto);
        if(notificacionProyectoOptional.isPresent()){
               return Response.status(201).entity(notificacionProyectoOptional.get()).build();
        }else{
              return Response.status(400).entity("Error " + notificacionProyectoRepository.getJmoordbException().getLocalizedMessage()).build();
        }
 
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Response update">

    @PUT
      @RolesAllowed({"admin"})
    @Operation(summary = "Inserta un nuevo notificacionProyecto", description = "Inserta un nuevo notificacionProyecto")
    @APIResponse(responseCode = "201", description = "Cuanoo se crea un  notificacionProyecto")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response update(
            @RequestBody(description = "Crea un nuevo notificacionProyecto.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotificacionProyecto.class))) NotificacionProyecto notificacionProyecto) {


        if(notificacionProyectoRepository.update(notificacionProyecto)){
               return Response.status(201).entity(notificacionProyecto).build();
        }else{
              return Response.status(400).entity("Error " + notificacionProyectoRepository.getJmoordbException().getLocalizedMessage()).build();
        }
        
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Response delete">
    @DELETE
    @RolesAllowed({"admin"})
    @Path("{idnotificacionProyecto}")
    @Operation(summary = "Elimina un notificacionProyecto por  idnotificacionProyecto", description = "Elimina un notificacionProyecto por su idnotificacionProyecto")
    @APIResponse(responseCode = "200", description = "Cuando elimina el notificacionProyecto")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response delete(
            @Parameter(description = "El elemento idnotificacionProyecto", required = true, example = "1", schema = @Schema(type = SchemaType.NUMBER)) @PathParam("idnotificacionProyecto") Long idnotificacionProyecto) {
        if(notificacionProyectoRepository.deleteByPk(idnotificacionProyecto) ==0L){
              return Response.status(201).entity(Boolean.TRUE).build();
        }else{
            return Response.status(400).entity("Error " + notificacionProyectoRepository.getJmoordbException().getLocalizedMessage()).build();
        }
      
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="List<NotificacionProyecto> lookup(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size)">

    @GET
    @Path("lookup")
    @RolesAllowed({"admin"})
    @Operation(summary = "Busca un notificacionProyecto", description = "Busqueda de notificacionProyecto por search")
    @APIResponse(responseCode = "200", description = "NotificacionProyecto")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = NotificacionProyecto.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public List<NotificacionProyecto> lookup(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size) {
        List<NotificacionProyecto> suggestions = new ArrayList<>();
        try {

            
            
                            
        Search search = DocumentUtil.convertForLookup(filter, sort, page, size);
        suggestions = notificacionProyectoRepository.lookup(search);

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
    @Operation(summary = "Cuenta ", description = "Cuenta notificacionProyecto")
    @APIResponse(responseCode = "200", description = "contador")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = NotificacionProyecto.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public Long count(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size) {
       Long result = 0L;
        try {

        Search search = DocumentUtil.convertForLookup(filter, sort, page, size);
        result = notificacionProyectoRepository.count(search);

        } catch (Exception e) {
       
          MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + "error: " + e.getLocalizedMessage());
        }

        return result;
    }

    // </editor-fold>
    
}
