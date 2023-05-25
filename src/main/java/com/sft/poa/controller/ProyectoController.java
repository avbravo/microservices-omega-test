/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sft.poa.controller;

import com.jmoordb.core.annotation.date.DateFormat;
import com.jmoordb.core.model.Search;
import com.jmoordb.core.util.ConsoleUtil;
import com.jmoordb.core.util.DocumentUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.sft.model.History;
import com.sft.model.Proyecto;
import com.sft.repository.HistoryRepository;
import com.sft.repository.ProyectoRepository;
import com.sft.repository.UserRepository;
import com.sft.repository.UserViewRepository;
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
@Path("proyecto")
@Tag(name = "Información del proyecto", description = "End-point para entidad Proyecto")
@RolesAllowed({"admin"})
public class ProyectoController {


    // <editor-fold defaultstate="collapsed" desc="Inject">
    @Inject
    ProyectoRepository proyectoRepository;
    
     @Inject
HistoryRepository historyRepository;
    
    @Inject
 UserViewRepository userViewRepository;
    @Inject
    UserRepository userRepository;



// </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="findAll">
    @GET
    @RolesAllowed({"admin"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Timed(name = "proyectoesFindAll",
            description = "Monitorea el tiempo en que se obtiene la lista de todos los proyectoes",
            unit = MetricUnits.MILLISECONDS, absolute = true)
    @Operation(summary = "Obtiene todos los proyectoes", description = "Retorna todos los proyectoes disponibles")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @APIResponse(responseCode = "200", description = "Los proyectoes")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "Los proyectoes", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Collection.class, readOnly = true, description = "los proyectos", required = true, name = "proyectoes")))
    public List<Proyecto> findAll() {
        List<Proyecto> proyectoList = new ArrayList<>();
        

        return proyectoRepository.findAll();
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Proyecto findByIdproyecto">
    @GET
    @RolesAllowed({"admin"})
    @Path("{idproyecto}")
    @Operation(summary = "Busca un proyecto por el idproyecto", description = "Busqueda de proyecto por idproyecto")
    @APIResponse(responseCode = "200", description = "El proyecto")
    @APIResponse(responseCode = "404", description = "Cuando no existe el idproyecto")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El proyecto", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Proyecto.class)))
    public Proyecto findByIdproyecto(
            @Parameter(description = "El idproyecto", required = true, example = "1", schema = @Schema(type = SchemaType.NUMBER)) @PathParam("idproyecto") Long idproyecto) {

      

        return proyectoRepository.findByPk(idproyecto).orElseThrow(
                () -> new WebApplicationException("No hay proyecto con idproyecto " + idproyecto, Response.Status.NOT_FOUND));

    }
// </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Proyecto findByProyecto">
    @GET
    @RolesAllowed({"admin"})
    @Path("proyecto")
    @Operation(summary = "Busca un proyecto por proyecti", description = "Busqueda de proyecto por proyecto")
    @APIResponse(responseCode = "200", description = "El proyecto")
    @APIResponse(responseCode = "404", description = "Cuando no existe el proyecto")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El proyecto", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Proyecto.class)))
    
   
    public List<Proyecto> findByProyecto(@Parameter(description = "El proyecto", required = true, example = "1", schema = @Schema(type = SchemaType.STRING)) @QueryParam("proyecto") final String proyecto) {

 return proyectoRepository.findByProyecto(proyecto);

        

    }
//// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Response save">
    @POST
    @RolesAllowed({"admin"})
    @Operation(summary = "Inserta un nuevo proyecto", description = "Inserta un nuevo proyecto")
    @APIResponse(responseCode = "201", description = "Cuanoo se crea un  proyecto")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response save(
            @RequestBody(description = "Crea un nuevo proyecto.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Proyecto.class))) Proyecto proyecto) {

  Optional<Proyecto> proyectoOptional=proyectoRepository.save(proyecto);
        if(proyectoOptional.isPresent()){
             saveHistory(proyecto);
               return Response.status(201).entity(proyectoOptional.get()).build();
        }else{
              return Response.status(400).entity("Error " + proyectoRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Response update">

    @PUT
    @RolesAllowed({"admin"})
    @Operation(summary = "Inserta un nuevo proyecto", description = "Inserta un nuevo proyecto")
    @APIResponse(responseCode = "201", description = "Cuando se crea un  proyecto")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response update(
            @RequestBody(description = "Crea un nuevo proyecto.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Proyecto.class))) Proyecto proyecto) {
        
        
        
       if(proyectoRepository.update(proyecto)){
         saveHistory(proyecto);
               return Response.status(201).entity(proyecto).build();
        }else{
        
             return Response.status(400).entity("Error " + proyectoRepository.getJmoordbException().getLocalizedMessage()).build();

        }
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Response delete">
    @DELETE
    @RolesAllowed({"admin"})
    @Path("{idproyecto}")
    @Operation(summary = "Elimina un proyecto por  idproyecto", description = "Elimina un proyecto por su idproyecto")
    @APIResponse(responseCode = "200", description = "Cuando elimina el proyecto")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response delete(
            @Parameter(description = "El elemento idproyecto", required = true, example = "1", schema = @Schema(type = SchemaType.NUMBER)) @PathParam("idproyecto") Long idproyecto) {
     
         if(proyectoRepository.deleteByPk(idproyecto) ==0L){
              return Response.status(201).entity(Boolean.TRUE).build();
        }else{
            return Response.status(400).entity("Error " + proyectoRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="@Path("entrefechas")">

    @Path("entrefechas")
@GET
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})  
 public List<Proyecto> findByFechaGreaterThanEqualAndFechaLessThanEqual(@QueryParam("fechainicial") @DateFormat("dd-MM-yyyy") final Date fechainicial,@QueryParam("fechafinal") @DateFormat("dd-MM-yyyy") final Date fechafinal) {
        return proyectoRepository.findByFechaGreaterThanEqualAndFechaLessThanEqual(fechainicial, fechafinal);
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="List<User> lookup(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size)">

    @GET
    @Path("lookup")
    @RolesAllowed({"admin"})
    @Operation(summary = "Busca un proyecto", description = "Busqueda de proyecto por search")
    @APIResponse(responseCode = "200", description = "User")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Proyecto.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public List<Proyecto> lookup(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size) {
        List<Proyecto> suggestions = new ArrayList<>();
        try {
            
      

        Search search = DocumentUtil.convertForLookup(filter, sort, page, size);
        
      
        suggestions = proyectoRepository.lookup(search);

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
    @Operation(summary = "Cuenta ", description = "Cuenta proyecto")
    @APIResponse(responseCode = "200", description = "contador")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Proyecto.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public Long count(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size) {
       Long result = 0L;
        try {

        Search search = DocumentUtil.convertForLookup(filter, sort, page, size);
        result = proyectoRepository.count(search);

        } catch (Exception e) {
       
          MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + "error: " + e.getLocalizedMessage());
        }

        return result;
    }

    // </editor-fold>
    
    
    
     // <editor-fold defaultstate="collapsed" desc="private void saveHistory(Proyecto proyecto)">
    
    private void saveHistory(Proyecto proyecto){
        try {
                History history = new History.Builder()                 
               .collection("proyecto")
                    .idcollection(proyecto.getIdproyecto().toString())
                    .database("sft")
                    .data(proyecto.toString())
                    .actionHistory(proyecto.getActionHistory().get(proyecto.getActionHistory().size()-1)                  )
                     .build();
            historyRepository.save(history);
        } catch (Exception e) {
           ConsoleUtil.error("saveHistory() "+e.getLocalizedMessage());
        }
    }
     
    
// </editor-fold>
}
