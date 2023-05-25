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
import com.sft.model.Objetivo;

import com.sft.repository.HistoryRepository;
import com.sft.repository.ObjetivoRepository;
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
@Path("objetivo")
@Tag(name = "Información del objetivo", description = "End-point para entidad Objetivo")
@RolesAllowed({"admin"})
public class ObjetivoController {

    
    // <editor-fold defaultstate="collapsed" desc="Inject">
    @Inject
    ObjetivoRepository objetivoRepository;
    
      @Inject
HistoryRepository historyRepository;


// </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="findAll">
    @GET
    @RolesAllowed({"admin"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Timed(name = "objetivoesFindAll",
            description = "Monitorea el tiempo en que se obtiene la lista de todos los objetivoes",
            unit = MetricUnits.MILLISECONDS, absolute = true)
    @Operation(summary = "Obtiene todos los objetivoes", description = "Retorna todos los objetivoes disponibles")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @APIResponse(responseCode = "200", description = "Los objetivoes")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "Los objetivoes", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Collection.class, readOnly = true, description = "los objetivoes", required = true, name = "objetivoes")))
    public List<Objetivo> findAll() {
        
       
        return objetivoRepository.findAll();
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Objetivo findByIdobjetivo">
    @GET
    @RolesAllowed({"admin"})
    @Path("{idobjetivo}")
    @Operation(summary = "Busca un objetivo por el idobjetivo", description = "Busqueda de objetivo por idobjetivo")
    @APIResponse(responseCode = "200", description = "El objetivo")
    @APIResponse(responseCode = "404", description = "Cuando no existe el idobjetivo")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El objetivo", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Objetivo.class)))
    public Objetivo findByIdobjetivo(
            @Parameter(description = "El idobjetivo", required = true, example = "1", schema = @Schema(type = SchemaType.NUMBER)) @PathParam("idobjetivo") Long idobjetivo) {

      

        return objetivoRepository.findByPk(idobjetivo).orElseThrow(
                () -> new WebApplicationException("No hay objetivo con idobjetivo " + idobjetivo, Response.Status.NOT_FOUND));

    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Response save">
    @POST
    @RolesAllowed({"admin"})

    @Operation(summary = "Inserta un nuevo objetivo", description = "Inserta un nuevo objetivo")
    @APIResponse(responseCode = "201", description = "Cuanoo se crea un  objetivo")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response save(
            @RequestBody(description = "Crea un nuevo objetivo.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Objetivo.class))) Objetivo objetivo) {


        Optional<Objetivo> objetivoOptional=objetivoRepository.save(objetivo);
        if(objetivoOptional.isPresent()){
            saveHistory(objetivo);
               return Response.status(201).entity(objetivoOptional.get()).build();
        }else{
              return Response.status(400).entity("Error " + objetivoRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Response update">

    @PUT
    @RolesAllowed({"admin"})
    @Operation(summary = "Inserta un nuevo objetivo", description = "Inserta un nuevo objetivo")
    @APIResponse(responseCode = "201", description = "Cuanoo se crea un  objetivo")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response update(
            @RequestBody(description = "Crea un nuevo objetivo.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Objetivo.class))) Objetivo objetivo) {


          if(objetivoRepository.update(objetivo)){
              saveHistory(objetivo);
               return Response.status(201).entity(objetivo).build();
        }else{
              return Response.status(400).entity("Error " + objetivoRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Response delete">
    @DELETE
    @RolesAllowed({"admin"})
    @Path("{idobjetivo}")
    @Operation(summary = "Elimina un objetivo por  idobjetivo", description = "Elimina un objetivo por su idobjetivo")
    @APIResponse(responseCode = "200", description = "Cuando elimina el objetivo")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response delete(
            @Parameter(description = "El elemento idobjetivo", required = true, example = "1", schema = @Schema(type = SchemaType.NUMBER)) @PathParam("idobjetivo") Long idobjetivo) {

           if(objetivoRepository.deleteByPk(idobjetivo) ==0L){
              return Response.status(201).entity(Boolean.TRUE).build();
        }else{
            return Response.status(400).entity("Error " + objetivoRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="List<Objetivo> lookup(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size)">

    @GET
    @Path("lookup")
    @RolesAllowed({"admin"})
    @Operation(summary = "Busca un objetivo", description = "Busqueda de objetivo por search")
    @APIResponse(responseCode = "200", description = "Objetivo")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Objetivo.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public List<Objetivo> lookup(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size) {
        List<Objetivo> suggestions = new ArrayList<>();
        try {

        Search search = DocumentUtil.convertForLookup(filter, sort, page, size);
        suggestions = objetivoRepository.lookup(search);

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
    @Operation(summary = "Cuenta ", description = "Cuenta objetivo")
    @APIResponse(responseCode = "200", description = "contador")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Objetivo.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public Long count(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size) {
       Long result = 0L;
        try {

        Search search = DocumentUtil.convertForLookup(filter, sort, page, size);
        result = objetivoRepository.count(search);

        } catch (Exception e) {
       
          MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + "error: " + e.getLocalizedMessage());
        }

        return result;
    }

    // </editor-fold>
    
       // <editor-fold defaultstate="collapsed" desc="private void saveHistory(Objetivo objetivo)">
    
    private void saveHistory(Objetivo objetivo){
        try {
                History history = new History.Builder()                 
               .collection("objetivo")
                    .idcollection(objetivo.getIdobjetivo().toString())
                    .database("sft")
                    .data(objetivo.toString())
                    .actionHistory(objetivo.getActionHistory().get(objetivo.getActionHistory().size()-1)                  )
                     .build();
            historyRepository.save(history);
        } catch (Exception e) {
           ConsoleUtil.error("saveHistory() "+e.getLocalizedMessage());
        }
    }
     
    
// </editor-fold>
}
