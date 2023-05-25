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
import com.sft.model.Subactividad;
import com.sft.model.Subactividad;
import com.sft.repository.HistoryRepository;
import com.sft.repository.SubactividadRepository;
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
@Path("subactividad")
@Tag(name = "Información del subactividad", description = "End-point para entidad Subactividad")
  @RolesAllowed({"admin"})
public class SubactividadController {

    
    // <editor-fold defaultstate="collapsed" desc="Inject">
    @Inject
    SubactividadRepository subactividadRepository;
    
      @Inject
HistoryRepository historyRepository;


// </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="findAll">
    @GET
      @RolesAllowed({"admin"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Timed(name = "subactividadesFindAll",
            description = "Monitorea el tiempo en que se obtiene la lista de todos los subactividades",
            unit = MetricUnits.MILLISECONDS, absolute = true)
    @Operation(summary = "Obtiene todos los subactividades", description = "Retorna todos los subactividades disponibles")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @APIResponse(responseCode = "200", description = "Los subactividades")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "Los subactividades", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Collection.class, readOnly = true, description = "los subactividades", required = true, name = "subactividades")))
    public List<Subactividad> findAll() {
       
        return subactividadRepository.findAll();
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Subactividad findByIdsubactividad">
    @GET
      @RolesAllowed({"admin"})
    @Path("{idsubactividad}")
    @Operation(summary = "Busca un subactividad por el idsubactividad", description = "Busqueda de subactividad por idsubactividad")
    @APIResponse(responseCode = "200", description = "El subactividad")
    @APIResponse(responseCode = "404", description = "Cuando no existe el idsubactividad")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El subactividad", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Subactividad.class)))
    public Subactividad findByIdsubactividad(
            @Parameter(description = "El idsubactividad", required = true, example = "1", schema = @Schema(type = SchemaType.NUMBER)) @PathParam("idsubactividad") Long idsubactividad) {



        return subactividadRepository.findByPk(idsubactividad).orElseThrow(
                () -> new WebApplicationException("No hay subactividad con idsubactividad " + idsubactividad, Response.Status.NOT_FOUND));

    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Response save">
    @POST
      @RolesAllowed({"admin"})
    @Operation(summary = "Inserta un nuevo subactividad", description = "Inserta un nuevo subactividad")
    @APIResponse(responseCode = "201", description = "Cuanoo se crea un  subactividad")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response save(
            @RequestBody(description = "Crea un nuevo subactividad.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Subactividad.class))) Subactividad subactividad) {

 Optional<Subactividad> subactividadOptional=subactividadRepository.save(subactividad);
        if(subactividadOptional.isPresent()){
            saveHistory(subactividad);
               return Response.status(201).entity(subactividadOptional.get()).build();
        }else{
              return Response.status(400).entity("Error " + subactividadRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Response update">

    @PUT
      @RolesAllowed({"admin"})
    @Operation(summary = "Inserta un nuevo subactividad", description = "Inserta un nuevo subactividad")
    @APIResponse(responseCode = "201", description = "Cuanoo se crea un  subactividad")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response update(
            @RequestBody(description = "Crea un nuevo subactividad.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Subactividad.class))) Subactividad subactividad) {


          if(subactividadRepository.update(subactividad)){
              saveHistory(subactividad);
               return Response.status(201).entity(subactividad).build();
        }else{
              return Response.status(400).entity("Error " + subactividadRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Response delete">
    @DELETE
      @RolesAllowed({"admin"})
    @Path("{idsubactividad}")
    @Operation(summary = "Elimina un subactividad por  idsubactividad", description = "Elimina un subactividad por su idsubactividad")
    @APIResponse(responseCode = "200", description = "Cuando elimina el subactividad")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response delete(
            @Parameter(description = "El elemento idsubactividad", required = true, example = "1", schema = @Schema(type = SchemaType.NUMBER)) @PathParam("idsubactividad") Long idsubactividad) {

         if(subactividadRepository.deleteByPk(idsubactividad) ==0L){
              return Response.status(201).entity(Boolean.TRUE).build();
        }else{
            return Response.status(400).entity("Error " + subactividadRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="List<Subactividad> lookup(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size)">

    @GET
    @Path("lookup")
    @RolesAllowed({"admin"})
    @Operation(summary = "Busca un subactividad", description = "Busqueda de subactividad por search")
    @APIResponse(responseCode = "200", description = "Subactividad")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Subactividad.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public List<Subactividad> lookup(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size) {
        List<Subactividad> suggestions = new ArrayList<>();
        try {

        Search search = DocumentUtil.convertForLookup(filter, sort, page, size);
        suggestions = subactividadRepository.lookup(search);

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
    @Operation(summary = "Cuenta ", description = "Cuenta subactividad")
    @APIResponse(responseCode = "200", description = "contador")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Subactividad.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public Long count(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size) {
       Long result = 0L;
        try {

        Search search = DocumentUtil.convertForLookup(filter, sort, page, size);
        result = subactividadRepository.count(search);

        } catch (Exception e) {
       
          MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + "error: " + e.getLocalizedMessage());
        }

        return result;
    }

    // </editor-fold>
     // <editor-fold defaultstate="collapsed" desc="private void saveHistory(Subactividad subactividad)">
    
    private void saveHistory(Subactividad subactividad){
        try {
                History history = new History.Builder()                 
               .collection("subactividad")
                    .idcollection(subactividad.getIdsubactividad().toString())
                    .database("sft")
                    .data(subactividad.toString())
                    .actionHistory(subactividad.getActionHistory().get(subactividad.getActionHistory().size()-1)                  )
                     .build();
            historyRepository.save(history);
        } catch (Exception e) {
           ConsoleUtil.error("saveHistory() "+e.getLocalizedMessage());
        }
    }
     
    
// </editor-fold>
}
