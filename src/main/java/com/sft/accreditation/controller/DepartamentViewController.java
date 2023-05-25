/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sft.accreditation.controller;

import com.jmoordb.core.model.Search;
import com.jmoordb.core.util.DocumentUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.sft.model.DepartamentView;
import com.sft.repository.DepartamentViewRepository;
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
import org.eclipse.microprofile.metrics.MetricRegistry;
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
@Path("departamentview")
@Tag(name = "Información del departamentView", description = "End-point para entidad DepartamentView")
@RolesAllowed({"admin"})
public class DepartamentViewController {

    
    // <editor-fold defaultstate="collapsed" desc="Inject">
    @Inject
    DepartamentViewRepository departamentViewRepository;
    
 
     @Inject
HistoryRepository historyRepository;
   
    private MetricRegistry registry;

// </editor-fold>
  

    // <editor-fold defaultstate="collapsed" desc="findAll">
    @GET
    @RolesAllowed({"admin"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Timed(name = "departamentViewesFindAll",
            description = "Monitorea el tiempo en que se obtiene la lista de todos los departamentViewes",
            unit = MetricUnits.MILLISECONDS, absolute = true)
    @Operation(summary = "Obtiene todos los departamentViewes", description = "Retorna todos los departamentViewes disponibles")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @APIResponse(responseCode = "200", description = "Los departamentViewes")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "Los departamentViewes", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Collection.class, readOnly = true, description = "los departamentViewes", required = true, name = "departamentViewes")))
    public List<DepartamentView> findAll() {
        
        return departamentViewRepository.findAll();
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="DepartamentView findByIddepartamentView">
    @GET
    @RolesAllowed({"admin"})
    @Path("{iddepartament}")
    @Operation(summary = "Busca un departamentView por el iddepartamentView", description = "Busqueda de departamentView por iddepartamentView")
    @APIResponse(responseCode = "200", description = "El departamentView")
    @APIResponse(responseCode = "404", description = "Cuando no existe el iddepartamentView")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El departamentView", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DepartamentView.class)))
    public DepartamentView findByIddepartament(
            @Parameter(description = "El iddepartamentView", required = true, example = "1", schema = @Schema(type = SchemaType.NUMBER)) @PathParam("iddepartament") Long iddepartament) {

      

        return departamentViewRepository.findByPk(iddepartament).orElseThrow(
                () -> new WebApplicationException("No hay departamentview con iddepartamentview " + iddepartament, Response.Status.NOT_FOUND));

    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="DepartamentView findByDepartamentView">
    @GET
    @RolesAllowed({"admin"})
    @Path("departament")
    @Operation(summary = "Busca un departamentView por el iddepartamentView", description = "Busqueda de departamentView por iddepartamentView")
    @APIResponse(responseCode = "200", description = "El departamentView")
    @APIResponse(responseCode = "404", description = "Cuando no existe el iddepartamentView")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El departamentView", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DepartamentView.class)))
    public DepartamentView findByDepartament(
            @Parameter(description = "El departamentView", required = true, example = "1", schema = @Schema(type = SchemaType.STRING)) @QueryParam("departament") String departament) {

      

        return departamentViewRepository.findByDepartament(departament).orElseThrow(
                () -> new WebApplicationException("No hay departamentview con departamentview " + departament, Response.Status.NOT_FOUND));

    }
// </editor-fold>
    
    
    
    
    // <editor-fold defaultstate="collapsed" desc="List<DepartamentView> lookup(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size)">

    @GET
    @Path("lookup")
    @RolesAllowed({"admin"})
    @Operation(summary = "Busca un appconfiguration", description = "Busqueda de user por search")
    @APIResponse(responseCode = "200", description = "DepartamentView")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DepartamentView.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public List<DepartamentView> lookup(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size) {
        List<DepartamentView> suggestions = new ArrayList<>();
        try {

        Search search = DocumentUtil.convertForLookup(filter, sort, page, size);
        suggestions = departamentViewRepository.lookup(search);

        } catch (Exception e) {
       
          MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + "error: " + e.getLocalizedMessage());
        }

        return suggestions;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Response save">
    @POST
    @RolesAllowed({"admin"})

    @Operation(summary = "Inserta un nuevo departamentview", description = "Inserta un nuevo departamentview")
    @APIResponse(responseCode = "201", description = "Cuanoo se crea un  departamentview")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response save(
            @RequestBody(description = "Crea un nuevo departamentview.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DepartamentView.class))) DepartamentView departamentview) {


       Optional<DepartamentView> departamentviewOptional=departamentViewRepository.save(departamentview);
        if(departamentviewOptional.isPresent()){
//            saveHistory(departamentview);
               return Response.status(201).entity(departamentviewOptional.get()).build();
        }else{
              return Response.status(400).entity("Error " + departamentViewRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Response update">

    @PUT
    @RolesAllowed({"admin"})
    @Operation(summary = "Inserta un nuevo departamentview", description = "Inserta un nuevo departamentview")
    @APIResponse(responseCode = "201", description = "Cuanoo se crea un  departamentview")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response update(
            @RequestBody(description = "Crea un nuevo departamentview.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DepartamentView.class))) DepartamentView departamentview) {


         if(departamentViewRepository.update(departamentview)){
//             saveHistory(departamentview);
               return Response.status(201).entity(departamentview).build();
        }else{
              return Response.status(400).entity("Error " + departamentViewRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Response delete">
    @DELETE
    @RolesAllowed({"admin"})
    @Path("{iddepartamentview}")
    @Operation(summary = "Elimina un departamentview por  iddepartamentview", description = "Elimina un departamentview por su iddepartamentview")
    @APIResponse(responseCode = "200", description = "Cuando elimina el departamentview")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response delete(
            @Parameter(description = "El elemento iddepartamentview", required = true, example = "1", schema = @Schema(type = SchemaType.NUMBER)) @PathParam("iddepartamentview") Long iddepartamentview) {
        if(departamentViewRepository.deleteByPk(iddepartamentview) ==0L){
              return Response.status(201).entity(Boolean.TRUE).build();
        }else{
            return Response.status(400).entity("Error " + departamentViewRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
    // </editor-fold>
    
     // <editor-fold defaultstate="collapsed" desc="Long count(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size)">

    @GET
    @Path("count")
    @RolesAllowed({"admin"})
    @Operation(summary = "Cuenta ", description = "Cuenta departamentview")
    @APIResponse(responseCode = "200", description = "contador")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DepartamentView.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public Long count(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size) {
       Long result = 0L;
        try {

        Search search = DocumentUtil.convertForLookup(filter, sort, page, size);
        result = departamentViewRepository.count(search);

        } catch (Exception e) {
       
          MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + "error: " + e.getLocalizedMessage());
        }

        return result;
    }

    // </editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="List<DepartamentView> likeByDepartament(@QueryParam("departamentview") String departamentview)">

    @GET
    @Path("likebydepartament")
    @RolesAllowed({"admin"})
    @Operation(summary = "Busca un user", description = "Busqueda de user usando like%")
    @APIResponse(responseCode = "200", description = "DepartamentView")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DepartamentView.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public List<DepartamentView> likeByDepartament(@QueryParam("departament") String departament) {
        List<DepartamentView> suggestions = new ArrayList<>();
        try {

       
        suggestions = departamentViewRepository.likeByDepartament(departament);

        } catch (Exception e) {
       
          MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + "error: " + e.getLocalizedMessage());
        }

        return suggestions;
    }

    // </editor-fold>
}
