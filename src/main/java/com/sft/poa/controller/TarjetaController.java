/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sft.poa.controller;

import com.jmoordb.core.model.Search;
import com.jmoordb.core.util.ConsoleUtil;
import com.jmoordb.core.util.DocumentUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.sft.model.History;
import com.sft.model.Tarjeta;
import com.sft.repository.HistoryRepository;
import com.sft.repository.TarjetaRepository;
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
@Path("tarjeta")
@Tag(name = "Información del tarjeta", description = "End-point para entidad Tarjeta")
@RolesAllowed({"admin"})
public class TarjetaController {

    // <editor-fold defaultstate="collapsed" desc="Inject">
    @Inject
    TarjetaRepository tarjetaRepository;
 @Inject
HistoryRepository historyRepository;
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="findAll">
    @GET
    @RolesAllowed({"admin"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Timed(name = "tarjetaesFindAll",
            description = "Monitorea el tiempo en que se obtiene la lista de todos los tarjetaes",
            unit = MetricUnits.MILLISECONDS, absolute = true)
    @Operation(summary = "Obtiene todos los tarjetaes", description = "Retorna todos los tarjetaes disponibles")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @APIResponse(responseCode = "200", description = "Los tarjetaes")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "Los tarjetaes", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Collection.class, readOnly = true, description = "los tarjetaes", required = true, name = "tarjetaes")))
    public List<Tarjeta> findAll() {

        return tarjetaRepository.findAll();
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Tarjeta findByIdtarjeta">
    @GET
    @RolesAllowed({"admin"})
    @Path("{idtarjeta}")
    @Operation(summary = "Busca un tarjeta por el idtarjeta", description = "Busqueda de tarjeta por idtarjeta")
    @APIResponse(responseCode = "200", description = "El tarjeta")
    @APIResponse(responseCode = "404", description = "Cuando no existe el idtarjeta")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El tarjeta", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Tarjeta.class)))
    public Tarjeta findByIdtarjeta(
            @Parameter(description = "El idtarjeta", required = true, example = "1", schema = @Schema(type = SchemaType.NUMBER)) @PathParam("idtarjeta") Long idtarjeta) {

        return tarjetaRepository.findByPk(idtarjeta).orElseThrow(
                () -> new WebApplicationException("No hay tarjeta con idtarjeta " + idtarjeta, Response.Status.NOT_FOUND));

    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Response save">
    @POST
    @RolesAllowed({"admin"})
    @Operation(summary = "Inserta un nuevo tarjeta", description = "Inserta un nuevo tarjeta")
    @APIResponse(responseCode = "201", description = "Cuanoo se crea un  tarjeta")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response save(
            @RequestBody(description = "Crea un nuevo tarjeta.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tarjeta.class))) Tarjeta tarjeta) {

        Optional<Tarjeta> tarjetaOptional = tarjetaRepository.save(tarjeta);
        if (tarjetaOptional.isPresent()) {
            saveHistory(tarjeta);
            
         
            return Response.status(201).entity(tarjetaOptional.get()).build();
        } else {
            return Response.status(400).entity("Error " + tarjetaRepository.getJmoordbException().getLocalizedMessage()).build();
        }

    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Response update">

    @PUT
    @RolesAllowed({"admin"})
    @Operation(summary = "Inserta un nuevo tarjeta", description = "Inserta un nuevo tarjeta")
    @APIResponse(responseCode = "201", description = "Cuanoo se crea un  tarjeta")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response update(
            @RequestBody(description = "Crea un nuevo tarjeta.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tarjeta.class))) Tarjeta tarjeta) {

        if (tarjetaRepository.update(tarjeta)) {
              saveHistory(tarjeta);
            return Response.status(201).entity(tarjeta).build();
        } else {
            return Response.status(400).entity("Error " + tarjetaRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Response delete">
    @DELETE
    @RolesAllowed({"admin"})
    @Path("{idtarjeta}")
    @Operation(summary = "Elimina un tarjeta por  idtarjeta", description = "Elimina un tarjeta por su idtarjeta")
    @APIResponse(responseCode = "200", description = "Cuando elimina el tarjeta")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response delete(
            @Parameter(description = "El elemento idtarjeta", required = true, example = "1", schema = @Schema(type = SchemaType.NUMBER)) @PathParam("idtarjeta") Long idtarjeta) {

        if (tarjetaRepository.deleteByPk(idtarjeta) == 0L) {
            return Response.status(201).entity(Boolean.TRUE).build();
        } else {
            return Response.status(400).entity("Error " + tarjetaRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="List<Tarjeta> lookup(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size)">
    @GET
    @Path("lookup")
    @RolesAllowed({"admin"})
    @Operation(summary = "Busca un tarjeta", description = "Busqueda de tarjeta por search")
    @APIResponse(responseCode = "200", description = "Tarjeta")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Tarjeta.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public List<Tarjeta> lookup(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size) {
        List<Tarjeta> suggestions = new ArrayList<>();
        try {

            Search search = DocumentUtil.convertForLookup(filter, sort, page, size);
            suggestions = tarjetaRepository.lookup(search);

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
    @Operation(summary = "Cuenta ", description = "Cuenta tarjeta")
    @APIResponse(responseCode = "200", description = "contador")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Tarjeta.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public Long count(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size) {
        Long result = 0L;
        try {

            Search search = DocumentUtil.convertForLookup(filter, sort, page, size);
            result = tarjetaRepository.count(search);

        } catch (Exception e) {

            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + "error: " + e.getLocalizedMessage());
        }

        return result;
    }

    // </editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="private void saveHistory(Tarjeta tarjeta)">
    
    private void saveHistory(Tarjeta tarjeta){
        try {
                History history = new History.Builder()                 
               .collection("tarjeta")
                    .idcollection(tarjeta.getIdtarjeta().toString())
                    .database("sft")
                    .data(tarjeta.toString())
                    .actionHistory(tarjeta.getActionHistory().get(tarjeta.getActionHistory().size()-1)                  )
                     .build();
            historyRepository.save(history);
        } catch (Exception e) {
           ConsoleUtil.error("saveHistory() "+e.getLocalizedMessage());
        }
    }
     
    
// </editor-fold>
}
