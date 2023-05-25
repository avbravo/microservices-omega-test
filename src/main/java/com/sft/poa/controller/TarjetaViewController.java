/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sft.poa.controller;

import com.jmoordb.core.model.Search;
import com.jmoordb.core.util.DocumentUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.sft.model.TarjetaView;
import com.sft.repository.TarjetaViewRepository;
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
@Path("tarjetaView")
@Tag(name = "Información del tarjetaView", description = "End-point para entidad TarjetaView")
  @RolesAllowed({"admin"})
public class TarjetaViewController {

    
    // <editor-fold defaultstate="collapsed" desc="Inject">
    @Inject
    TarjetaViewRepository tarjetaViewRepository;
    
 



// </editor-fold>



    // <editor-fold defaultstate="collapsed" desc="findAll">
    @GET
      @RolesAllowed({"admin"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Timed(name = "tarjetaViewesFindAll",
            description = "Monitorea el tiempo en que se obtiene la lista de todos los tarjetaViewes",
            unit = MetricUnits.MILLISECONDS, absolute = true)
    @Operation(summary = "Obtiene todos los tarjetaViewes", description = "Retorna todos los tarjetaViewes disponibles")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @APIResponse(responseCode = "200", description = "Los tarjetaViewes")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "Los tarjetaViewes", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Collection.class, readOnly = true, description = "los tarjetaViewes", required = true, name = "tarjetaViewes")))
    public List<TarjetaView> findAll() {
        
        return tarjetaViewRepository.findAll();
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="TarjetaView findByIdtarjetaView">
    @GET
      @RolesAllowed({"admin"})
    @Path("{idtarjetaView}")
    @Operation(summary = "Busca un tarjetaView por el idtarjetaView", description = "Busqueda de tarjetaView por idtarjetaView")
    @APIResponse(responseCode = "200", description = "El tarjetaView")
    @APIResponse(responseCode = "404", description = "Cuando no existe el idtarjetaView")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El tarjetaView", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = TarjetaView.class)))
    public TarjetaView findByIdtarjeta(
            @Parameter(description = "El idtarjeta", required = true, example = "1", schema = @Schema(type = SchemaType.NUMBER)) @PathParam("idtarjeta") Long idtarjeta) {

   

        return tarjetaViewRepository.findByPk(idtarjeta).orElseThrow(
                () -> new WebApplicationException("No hay tarjetaView con idtarjeta " + idtarjeta, Response.Status.NOT_FOUND));

    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Response save">
    @POST
      @RolesAllowed({"admin"})

    @Operation(summary = "Inserta un nuevo tarjetaView", description = "Inserta un nuevo tarjetaView")
    @APIResponse(responseCode = "201", description = "Cuanoo se crea un  tarjetaView")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response save(
            @RequestBody(description = "Crea un nuevo tarjetaView.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TarjetaView.class))) TarjetaView tarjetaView) {


       Optional<TarjetaView> tarjetaViewOptional=tarjetaViewRepository.save(tarjetaView);
        if(tarjetaViewOptional.isPresent()){
               return Response.status(201).entity(tarjetaViewOptional.get()).build();
        }else{
              return Response.status(400).entity("Error " + tarjetaViewRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Response update">

    @PUT
      @RolesAllowed({"admin"})
    @Operation(summary = "Inserta un nuevo tarjetaView", description = "Inserta un nuevo tarjetaView")
    @APIResponse(responseCode = "201", description = "Cuanoo se crea un  tarjetaView")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response update(
            @RequestBody(description = "Crea un nuevo tarjetaView.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TarjetaView.class))) TarjetaView tarjetaView) {


       if(tarjetaViewRepository.update(tarjetaView)){
               return Response.status(201).entity(tarjetaView).build();
        }else{
              return Response.status(400).entity("Error " + tarjetaViewRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Response delete">
    @DELETE
      @RolesAllowed({"admin"})
    @Path("{idtarjetaView}")
    @Operation(summary = "Elimina un tarjetaView por  idtarjetaView", description = "Elimina un tarjetaView por su idtarjetaView")
    @APIResponse(responseCode = "200", description = "Cuando elimina el tarjetaView")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response delete(
            @Parameter(description = "El elemento idtarjetaView", required = true, example = "1", schema = @Schema(type = SchemaType.NUMBER)) @PathParam("idtarjetaView") Long idtarjetaView) {
       if(tarjetaViewRepository.deleteByPk(idtarjetaView) ==0L){
              return Response.status(201).entity(Boolean.TRUE).build();
        }else{
            return Response.status(400).entity("Error " + tarjetaViewRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="List<TarjetaView> lookup(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size)">

    @GET
    @Path("lookup")
    @RolesAllowed({"admin"})
    @Operation(summary = "Busca un tarjetaView", description = "Busqueda de tarjetaView por search")
    @APIResponse(responseCode = "200", description = "TarjetaView")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = TarjetaView.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public List<TarjetaView> lookup(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size) {
        List<TarjetaView> suggestions = new ArrayList<>();
        try {

        Search search = DocumentUtil.convertForLookup(filter, sort, page, size);
        suggestions = tarjetaViewRepository.lookup(search);

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
    @Operation(summary = "Cuenta ", description = "Cuenta tarjetaView")
    @APIResponse(responseCode = "200", description = "contador")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = TarjetaView.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public Long count(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size) {
       Long result = 0L;
        try {

        Search search = DocumentUtil.convertForLookup(filter, sort, page, size);
        result = tarjetaViewRepository.count(search);

        } catch (Exception e) {
       
          MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + "error: " + e.getLocalizedMessage());
        }

        return result;
    }

    // </editor-fold>
}
