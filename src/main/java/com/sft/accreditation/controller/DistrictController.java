/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sft.accreditation.controller;

import com.jmoordb.core.model.Search;
import com.jmoordb.core.util.ConsoleUtil;
import com.jmoordb.core.util.DocumentUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.sft.model.District;
import com.sft.model.History;
import com.sft.model.District;
import com.sft.repository.DistrictRepository;
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
@Path("district")
@Tag(name = "Información del district", description = "End-point para entidad District")
@RolesAllowed({"admin"})
public class DistrictController {

    
    // <editor-fold defaultstate="collapsed" desc="Inject">
    @Inject
    DistrictRepository districtRepository;
    
      @Inject
HistoryRepository historyRepository;


// </editor-fold>
 

    // <editor-fold defaultstate="collapsed" desc="findAll">
    @GET
    @RolesAllowed({"admin"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Timed(name = "districtesFindAll",
            description = "Monitorea el tiempo en que se obtiene la lista de todos los districtes",
            unit = MetricUnits.MILLISECONDS, absolute = true)
    @Operation(summary = "Obtiene todos los districtes", description = "Retorna todos los districtes disponibles")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @APIResponse(responseCode = "200", description = "Los districtes")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "Los districtes", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Collection.class, readOnly = true, description = "los districtes", required = true, name = "districtes")))
    public List<District> findAll() {
        
        return districtRepository.findAll();
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="District findByIddistrict">
    @GET
    @RolesAllowed({"admin"})
    @Path("{iddistrict}")
    @Operation(summary = "Busca un district por el iddistrict", description = "Busqueda de district por iddistrict")
    @APIResponse(responseCode = "200", description = "El district")
    @APIResponse(responseCode = "404", description = "Cuando no existe el iddistrict")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El district", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = District.class)))
    public District findByIddistrict(
            @Parameter(description = "El iddistrict", required = true, example = "1", schema = @Schema(type = SchemaType.NUMBER)) @PathParam("iddistrict") String iddistrict) {

     

        return districtRepository.findByPk(iddistrict).orElseThrow(
                () -> new WebApplicationException("No hay district con iddistrict " + iddistrict, Response.Status.NOT_FOUND));

    }
// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="List<District> lookup(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size)">

    @GET
    @Path("lookup")
    @RolesAllowed({"admin"})
    @Operation(summary = "Busca un appconfiguration", description = "Busqueda de user por search")
    @APIResponse(responseCode = "200", description = "District")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = District.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public List<District> lookup(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size) {
        List<District> suggestions = new ArrayList<>();
        try {

        Search search = DocumentUtil.convertForLookup(filter, sort, page, size);
        suggestions = districtRepository.lookup(search);

        } catch (Exception e) {
       
          MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + "error: " + e.getLocalizedMessage());
        }

        return suggestions;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Response save">
    @POST
    @RolesAllowed({"admin"})
    @Operation(summary = "Inserta un nuevo district", description = "Inserta un nuevo district")
    @APIResponse(responseCode = "201", description = "Cuanoo se crea un  district")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response save(
            @RequestBody(description = "Crea un nuevo district.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = District.class))) District district) {


         Optional<District> districtOptional=districtRepository.save(district);
        if(districtOptional.isPresent()){
            saveHistory(district);
               return Response.status(201).entity(districtOptional.get()).build();
        }else{
              return Response.status(400).entity("Error " + districtRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Response update">

    @PUT
    @RolesAllowed({"admin"})
    @Operation(summary = "Inserta un nuevo district", description = "Inserta un nuevo district")
    @APIResponse(responseCode = "201", description = "Cuanoo se crea un  district")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response update(
            @RequestBody(description = "Crea un nuevo district.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = District.class))) District district) {


         if(districtRepository.update(district)){
             saveHistory(district);
               return Response.status(201).entity(district).build();
        }else{
              return Response.status(400).entity("Error " + districtRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Response delete">
    @DELETE
    @RolesAllowed({"admin"})
    @Path("{iddistrict}")
    @Operation(summary = "Elimina un district por  iddistrict", description = "Elimina un district por su iddistrict")
    @APIResponse(responseCode = "200", description = "Cuando elimina el district")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response delete(
            @Parameter(description = "El elemento iddistrict", required = true, example = "1", schema = @Schema(type = SchemaType.NUMBER)) @PathParam("iddistrict") String iddistrict) {
      if(districtRepository.deleteByPk(iddistrict) ==0L){
              return Response.status(201).entity(Boolean.TRUE).build();
        }else{
            return Response.status(400).entity("Error " + districtRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
    // </editor-fold>
    
     // <editor-fold defaultstate="collapsed" desc="Long count(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size)">

    @GET
    @Path("count")
    @RolesAllowed({"admin"})
    @Operation(summary = "Cuenta ", description = "Cuenta district")
    @APIResponse(responseCode = "200", description = "contador")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = District.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public Long count(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size) {
       Long result = 0L;
        try {

        Search search = DocumentUtil.convertForLookup(filter, sort, page, size);
        result = districtRepository.count(search);

        } catch (Exception e) {
       
          MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + "error: " + e.getLocalizedMessage());
        }

        return result;
    }

    // </editor-fold>
    
    
     // <editor-fold defaultstate="collapsed" desc="private void saveHistory(District district)">
    
    private void saveHistory(District district){
        try {
                History history = new History.Builder()                 
               .collection("district")
                    .idcollection(district.getIddistrict().toString())
                                    .database("accreditation")
                    .data(district.toString())
                    .actionHistory(district.getActionHistory().get(district.getActionHistory().size()-1)                  )
                     .build();
            historyRepository.save(history);
        } catch (Exception e) {
           ConsoleUtil.error("saveHistory() "+e.getLocalizedMessage());
        }
    }
     
    
// </editor-fold>
    
}
