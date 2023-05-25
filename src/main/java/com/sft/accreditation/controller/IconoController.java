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
import com.sft.model.Icono;
import com.sft.model.Icono;
import com.sft.repository.HistoryRepository;
import com.sft.repository.IconoRepository;
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
@Path("icono")
@Tag(name = "Información del icono", description = "End-point para entidad Icono")
@RolesAllowed({"admin"})
public class IconoController {

    
    // <editor-fold defaultstate="collapsed" desc="Inject">
    @Inject
    IconoRepository iconoRepository;
    
      @Inject
HistoryRepository historyRepository;


// </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="findAll">
    @GET
    @RolesAllowed({"admin"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
        
    @Timed(name = "iconoesFindAll",
            description = "Monitorea el tiempo en que se obtiene la lista de todos los iconoes",
            unit = MetricUnits.MILLISECONDS, absolute = true)
    @Operation(summary = "Obtiene todos los iconoes", description = "Retorna todos los iconoes disponibles")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @APIResponse(responseCode = "200", description = "Los iconoes")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "Los iconoes", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Collection.class, readOnly = true, description = "los iconoes", required = true, name = "iconoes")))
    public List<Icono> findAll() {
       
        return iconoRepository.findAll();
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Icono findByIdicono">
    @GET
    @RolesAllowed({"admin"})
    @Path("{idicono}")
    @Operation(summary = "Busca un icono por el idicono", description = "Busqueda de icono por idicono")
    @APIResponse(responseCode = "200", description = "El icono")
    @APIResponse(responseCode = "404", description = "Cuando no existe el idicono")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El icono", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Icono.class)))
    public Icono findByIdicono(
            @Parameter(description = "El idicono", required = true, example = "1", schema = @Schema(type = SchemaType.NUMBER)) @PathParam("idicono") Long idicono) {



        return iconoRepository.findByPk(idicono).orElseThrow(
                () -> new WebApplicationException("No hay icono con idicono " + idicono, Response.Status.NOT_FOUND));

    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Icono findByIcononame">
    @GET
    @RolesAllowed({"admin"})
    @Path("icononame")
    @Operation(summary = "Busca un icono por el icononam", description = "Busqueda de icono por idicono")
    @APIResponse(responseCode = "200", description = "El icono")
    @APIResponse(responseCode = "404", description = "Cuando no existe el icononame")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El icono", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Icono.class)))
    
   
    public Icono findByIcono(@Parameter(description = "El icononame", required = true, example = "1", schema = @Schema(type = SchemaType.STRING)) @QueryParam("icono") final String icono) {



        return iconoRepository.findByIcono(icono).orElseThrow(
                () -> new WebApplicationException("No hay icono con icono " + icono, Response.Status.NOT_FOUND));

    }
//// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="List<Icono> lookup(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size)">

    @GET
    @Path("lookup")
    @RolesAllowed({"admin"})
    @Operation(summary = "Busca un icono", description = "Busqueda de icono por search")
    @APIResponse(responseCode = "200", description = "Icono")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Icono.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public List<Icono> lookup(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size) {
        List<Icono> suggestions = new ArrayList<>();
        try {

        Search search = DocumentUtil.convertForLookup(filter, sort, page, size);
        suggestions = iconoRepository.lookup(search);

        } catch (Exception e) {
       
          MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + "error: " + e.getLocalizedMessage());
        }

        return suggestions;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Response save">
    @POST
    @RolesAllowed({"admin"})
    @Operation(summary = "Inserta un nuevo icono", description = "Inserta un nuevo icono")
    @APIResponse(responseCode = "201", description = "Cuanoo se crea un  icono")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response save(
            @RequestBody(description = "Crea un nuevo icono.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Icono.class))) Icono icono) {

  Optional<Icono> iconoOptional=iconoRepository.save(icono);
        if(iconoOptional.isPresent()){
            saveHistory(icono);
               return Response.status(201).entity(iconoOptional.get()).build();
        }else{
              return Response.status(400).entity("Error " + iconoRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Response update">

    @PUT
    @RolesAllowed({"admin"})
    @Operation(summary = "Inserta un nuevo icono", description = "Inserta un nuevo icono")
    @APIResponse(responseCode = "201", description = "Cuanoo se crea un  icono")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response update(
            @RequestBody(description = "Crea un nuevo icono.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Icono.class))) Icono icono) {


         if(iconoRepository.update(icono)){
             saveHistory(icono);
               return Response.status(201).entity(icono).build();
        }else{
              return Response.status(400).entity("Error " + iconoRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Response delete">
    @DELETE
    @RolesAllowed({"admin"})
    @Path("{idicono}")
    @Operation(summary = "Elimina un icono por  idicono", description = "Elimina un icono por su idicono")
    @APIResponse(responseCode = "200", description = "Cuando elimina el icono")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response delete(
            @Parameter(description = "El elemento idicono", required = true, example = "1", schema = @Schema(type = SchemaType.NUMBER)) @PathParam("idicono") Long idicono) {
        if(iconoRepository.deleteByPk(idicono) ==0L){
              return Response.status(201).entity(Boolean.TRUE).build();
        }else{
            return Response.status(400).entity("Error " + iconoRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
    // </editor-fold>
    
     // <editor-fold defaultstate="collapsed" desc="Long count(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size)">

    @GET
    @Path("count")
    @RolesAllowed({"admin"})
    @Operation(summary = "Cuenta ", description = "Cuenta icono")
    @APIResponse(responseCode = "200", description = "contador")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Icono.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public Long count(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size) {
       Long result = 0L;
        try {

        Search search = DocumentUtil.convertForLookup(filter, sort, page, size);
        result = iconoRepository.count(search);

        } catch (Exception e) {
       
          MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + "error: " + e.getLocalizedMessage());
        }

        return result;
    }

    // </editor-fold>
     // <editor-fold defaultstate="collapsed" desc="private void saveHistory(Icono icono)">
    
    private void saveHistory(Icono icono){
        try {
                History history = new History.Builder()                 
               .collection("icono")
                    .idcollection(icono.getIdicono().toString())
                                .database("accreditation")
                    .data(icono.toString())
                    .actionHistory(icono.getActionHistory().get(icono.getActionHistory().size()-1)                  )
                     .build();
            historyRepository.save(history);
        } catch (Exception e) {
           ConsoleUtil.error("saveHistory() "+e.getLocalizedMessage());
        }
    }
     
    
// </editor-fold>
}
