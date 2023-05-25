/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sft.poa.controller;

import com.jmoordb.core.model.Search;
import com.jmoordb.core.util.ConsoleUtil;
import com.jmoordb.core.util.DocumentUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.sft.model.Flujo;
import com.sft.model.History;
import com.sft.repository.FlujoRepository;
import com.sft.repository.HistoryRepository;
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
@Path("/flujo")
@Tag(name="flujo", description ="administra el flujo" )
public class FlujoController {
    
    // <editor-fold defaultstate="collapsed" desc="Inject">
    @Inject
    FlujoRepository flujoRepository;
 @Inject
HistoryRepository historyRepository;
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="findAll">
    @GET
    @RolesAllowed({"admin"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Timed(name = "flujoFindAll",
            description = "Monitorea el tiempo en que se obtiene la lista de todos los flujos",
            unit = MetricUnits.MILLISECONDS, absolute = true)
    @Operation(summary = "Obtiene todos los flujos", description = "Retorna todos los flujos disponibles")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @APIResponse(responseCode = "200", description = "Los flujos")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "Los flujos", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Collection.class, readOnly = true, description = "los flujos", required = true, name = "flujos")))
    public List<Flujo> findAll() {

        return flujoRepository.findAll();
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Flujo findByIdflujo">
    @GET
    @RolesAllowed({"admin"})
    @Path("{idflujo}")
    @Operation(summary = "Busca un flujo por el idflujo", description = "Busqueda de flujo por idflujo")
    @APIResponse(responseCode = "200", description = "El flujo")
    @APIResponse(responseCode = "404", description = "Cuando no existe el idflujo")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El flujo", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Flujo.class)))
    public Flujo findByIdflujo(
            @Parameter(description = "El idflujo", required = true, example = "1", schema = @Schema(type = SchemaType.NUMBER)) @PathParam("idflujo") Long idflujo) {

        return flujoRepository.findByPk(idflujo).orElseThrow(
                () -> new WebApplicationException("No hay flujo con idflujo " + idflujo, Response.Status.NOT_FOUND));

    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Response save">
    @POST
    @RolesAllowed({"admin"})
    @Operation(summary = "Inserta un nuevo flujo", description = "Inserta un nuevo flujo")
    @APIResponse(responseCode = "201", description = "Cuanoo se crea un  flujo")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response save(
            @RequestBody(description = "Crea un nuevo flujo.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Flujo.class))) Flujo flujo) {

        Optional<Flujo> flujoOptional = flujoRepository.save(flujo);
        if (flujoOptional.isPresent()) {
            saveHistory(flujo);
            
         
            return Response.status(201).entity(flujoOptional.get()).build();
        } else {
            return Response.status(400).entity("Error " + flujoRepository.getJmoordbException().getLocalizedMessage()).build();
        }

    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Response update">

    @PUT
    @RolesAllowed({"admin"})
    @Operation(summary = "Inserta un nuevo flujo", description = "Inserta un nuevo flujo")
    @APIResponse(responseCode = "201", description = "Cuanoo se crea un  flujo")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response update(
            @RequestBody(description = "Crea un nuevo flujo.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Flujo.class))) Flujo flujo) {

        if (flujoRepository.update(flujo)) {
              saveHistory(flujo);
            return Response.status(201).entity(flujo).build();
        } else {
            return Response.status(400).entity("Error " + flujoRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Response delete">
    @DELETE
    @RolesAllowed({"admin"})
    @Path("{idflujo}")
    @Operation(summary = "Elimina un flujo por  idflujo", description = "Elimina un flujo por su idflujo")
    @APIResponse(responseCode = "200", description = "Cuando elimina el flujo")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response delete(
            @Parameter(description = "El elemento idflujo", required = true, example = "1", schema = @Schema(type = SchemaType.NUMBER)) @PathParam("idflujo") Long idflujo) {

        if (flujoRepository.deleteByPk(idflujo) == 0L) {
            return Response.status(201).entity(Boolean.TRUE).build();
        } else {
            return Response.status(400).entity("Error " + flujoRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="List<Flujo> lookup(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size)">
    @GET
    @Path("lookup")
    @RolesAllowed({"admin"})
    @Operation(summary = "Busca un flujo", description = "Busqueda de flujo por search")
    @APIResponse(responseCode = "200", description = "Flujo")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Flujo.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public List<Flujo> lookup(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size) {
        List<Flujo> suggestions = new ArrayList<>();
        try {

            Search search = DocumentUtil.convertForLookup(filter, sort, page, size);
            suggestions = flujoRepository.lookup(search);

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
    @Operation(summary = "Cuenta ", description = "Cuenta flujo")
    @APIResponse(responseCode = "200", description = "contador")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Flujo.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public Long count(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size) {
        Long result = 0L;
        try {

            Search search = DocumentUtil.convertForLookup(filter, sort, page, size);
            result = flujoRepository.count(search);

        } catch (Exception e) {

            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + "error: " + e.getLocalizedMessage());
        }

        return result;
    }

    // </editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="private void saveHistory(Flujo flujo)">
    
    private void saveHistory(Flujo flujo){
        try {
                History history = new History.Builder()                 
               .collection("flujo")
                    .idcollection(flujo.getIdflujo().toString())
                    .database("sft")
                    .data(flujo.toString())
                    .actionHistory(flujo.getActionHistory().get(flujo.getActionHistory().size()-1)                  )
                     .build();
            historyRepository.save(history);
        } catch (Exception e) {
           ConsoleUtil.error("saveHistory() "+e.getLocalizedMessage());
        }
    }
     
    
// </editor-fold>
}
