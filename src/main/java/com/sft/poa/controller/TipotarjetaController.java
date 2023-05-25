/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sft.poa.controller;

import com.sft.accreditation.controller.*;
import com.jmoordb.core.model.Search;
import com.jmoordb.core.util.ConsoleUtil;
import com.jmoordb.core.util.DocumentUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.sft.model.History;
import com.sft.model.Tipotarjeta;
import com.sft.model.Tipotarjeta;
import com.sft.repository.HistoryRepository;
import com.sft.repository.TipotarjetaRepository;
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
@Path("tipotarjeta")
@Tag(name = "Información del tipotarjeta", description = "End-point para entidad Tipotarjeta")
@RolesAllowed({"admin"})
public class TipotarjetaController {

    
    // <editor-fold defaultstate="collapsed" desc="Inject">
    @Inject
    TipotarjetaRepository tipotarjetaRepository;
    
      @Inject
HistoryRepository historyRepository;


// </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="findAll">
    @GET
    @RolesAllowed({"admin"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
        
    @Timed(name = "tipotarjetaesFindAll",
            description = "Monitorea el tiempo en que se obtiene la lista de todos los tipotarjetaes",
            unit = MetricUnits.MILLISECONDS, absolute = true)
    @Operation(summary = "Obtiene todos los tipotarjetaes", description = "Retorna todos los tipotarjetaes disponibles")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @APIResponse(responseCode = "200", description = "Los tipotarjetaes")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "Los tipotarjetaes", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Collection.class, readOnly = true, description = "los tipotarjetaes", required = true, name = "tipotarjetaes")))
    public List<Tipotarjeta> findAll() {
       
        return tipotarjetaRepository.findAll();
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Tipotarjeta findByIdtipotarjeta">
    @GET
    @RolesAllowed({"admin"})
    @Path("{idtipotarjeta}")
    @Operation(summary = "Busca un tipotarjeta por el idtipotarjeta", description = "Busqueda de tipotarjeta por idtipotarjeta")
    @APIResponse(responseCode = "200", description = "El tipotarjeta")
    @APIResponse(responseCode = "404", description = "Cuando no existe el idtipotarjeta")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El tipotarjeta", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Tipotarjeta.class)))
    public Tipotarjeta findByIdtipotarjeta(
            @Parameter(description = "El idtipotarjeta", required = true, example = "1", schema = @Schema(type = SchemaType.NUMBER)) @PathParam("idtipotarjeta") Long idtipotarjeta) {



        return tipotarjetaRepository.findByPk(idtipotarjeta).orElseThrow(
                () -> new WebApplicationException("No hay tipotarjeta con idtipotarjeta " + idtipotarjeta, Response.Status.NOT_FOUND));

    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Tipotarjeta findByTipotarjetaname">
    @GET
    @RolesAllowed({"admin"})
    @Path("tipotarjetaname")
    @Operation(summary = "Busca un tipotarjeta por el tipotarjetanam", description = "Busqueda de tipotarjeta por idtipotarjeta")
    @APIResponse(responseCode = "200", description = "El tipotarjeta")
    @APIResponse(responseCode = "404", description = "Cuando no existe el tipotarjetaname")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El tipotarjeta", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Tipotarjeta.class)))
    
   
    public Tipotarjeta findByTipotarjeta(@Parameter(description = "El tipotarjetaname", required = true, example = "1", schema = @Schema(type = SchemaType.STRING)) @QueryParam("tipotarjeta") final String tipotarjeta) {



        return tipotarjetaRepository.findByTipotarjeta(tipotarjeta).orElseThrow(
                () -> new WebApplicationException("No hay tipotarjeta con tipotarjeta " + tipotarjeta, Response.Status.NOT_FOUND));

    }
//// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="List<Tipotarjeta> lookup(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size)">

    @GET
    @Path("lookup")
    @RolesAllowed({"admin"})
    @Operation(summary = "Busca un tipotarjeta", description = "Busqueda de tipotarjeta por search")
    @APIResponse(responseCode = "200", description = "Tipotarjeta")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Tipotarjeta.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public List<Tipotarjeta> lookup(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size) {
        List<Tipotarjeta> suggestions = new ArrayList<>();
        try {

        Search search = DocumentUtil.convertForLookup(filter, sort, page, size);
        suggestions = tipotarjetaRepository.lookup(search);

        } catch (Exception e) {
       
          MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + "error: " + e.getLocalizedMessage());
        }

        return suggestions;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Response save">
    @POST
    @RolesAllowed({"admin"})
    @Operation(summary = "Inserta un nuevo tipotarjeta", description = "Inserta un nuevo tipotarjeta")
    @APIResponse(responseCode = "201", description = "Cuanoo se crea un  tipotarjeta")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response save(
            @RequestBody(description = "Crea un nuevo tipotarjeta.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tipotarjeta.class))) Tipotarjeta tipotarjeta) {

  Optional<Tipotarjeta> tipotarjetaOptional=tipotarjetaRepository.save(tipotarjeta);
        if(tipotarjetaOptional.isPresent()){
            saveHistory(tipotarjeta);
               return Response.status(201).entity(tipotarjetaOptional.get()).build();
        }else{
              return Response.status(400).entity("Error " + tipotarjetaRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Response update">

    @PUT
    @RolesAllowed({"admin"})
    @Operation(summary = "Inserta un nuevo tipotarjeta", description = "Inserta un nuevo tipotarjeta")
    @APIResponse(responseCode = "201", description = "Cuanoo se crea un  tipotarjeta")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response update(
            @RequestBody(description = "Crea un nuevo tipotarjeta.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tipotarjeta.class))) Tipotarjeta tipotarjeta) {


         if(tipotarjetaRepository.update(tipotarjeta)){
             saveHistory(tipotarjeta);
               return Response.status(201).entity(tipotarjeta).build();
        }else{
              return Response.status(400).entity("Error " + tipotarjetaRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Response delete">
    @DELETE
    @RolesAllowed({"admin"})
    @Path("{idtipotarjeta}")
    @Operation(summary = "Elimina un tipotarjeta por  idtipotarjeta", description = "Elimina un tipotarjeta por su idtipotarjeta")
    @APIResponse(responseCode = "200", description = "Cuando elimina el tipotarjeta")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response delete(
            @Parameter(description = "El elemento idtipotarjeta", required = true, example = "1", schema = @Schema(type = SchemaType.NUMBER)) @PathParam("idtipotarjeta") Long idtipotarjeta) {
        if(tipotarjetaRepository.deleteByPk(idtipotarjeta) ==0L){
              return Response.status(201).entity(Boolean.TRUE).build();
        }else{
            return Response.status(400).entity("Error " + tipotarjetaRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
    // </editor-fold>
    
     // <editor-fold defaultstate="collapsed" desc="Long count(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size)">

    @GET
    @Path("count")
    @RolesAllowed({"admin"})
    @Operation(summary = "Cuenta ", description = "Cuenta tipotarjeta")
    @APIResponse(responseCode = "200", description = "contador")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Tipotarjeta.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public Long count(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size) {
       Long result = 0L;
        try {

        Search search = DocumentUtil.convertForLookup(filter, sort, page, size);
        result = tipotarjetaRepository.count(search);

        } catch (Exception e) {
       
          MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + "error: " + e.getLocalizedMessage());
        }

        return result;
    }

    // </editor-fold>
     // <editor-fold defaultstate="collapsed" desc="private void saveHistory(Tipotarjeta tipotarjeta)">
    
    private void saveHistory(Tipotarjeta tipotarjeta){
        try {
                History history = new History.Builder()                 
               .collection("tipotarjeta")
                    .idcollection(tipotarjeta.getIdtipotarjeta().toString())
                                .database("accreditation")
                    .data(tipotarjeta.toString())
                    .actionHistory(tipotarjeta.getActionHistory().get(tipotarjeta.getActionHistory().size()-1)                  )
                     .build();
            historyRepository.save(history);
        } catch (Exception e) {
           ConsoleUtil.error("saveHistory() "+e.getLocalizedMessage());
        }
    }
     
    
// </editor-fold>
}
