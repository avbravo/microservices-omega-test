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
import com.sft.model.Role;
import com.sft.repository.HistoryRepository;
import com.sft.repository.RoleRepository;
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
@Path("role")
@Tag(name = "Información del role", description = "End-point para entidad Role")
@RolesAllowed({"admin"})
public class RoleController {

    
    // <editor-fold defaultstate="collapsed" desc="Inject">
    @Inject
    RoleRepository roleRepository;
    
 
     @Inject
HistoryRepository historyRepository;

// </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="findAll">
    @GET
    @RolesAllowed({"admin"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Timed(name = "roleesFindAll",
            description = "Monitorea el tiempo en que se obtiene la lista de todos los rolees",
            unit = MetricUnits.MILLISECONDS, absolute = true)
    @Operation(summary = "Obtiene todos los rolees", description = "Retorna todos los rolees disponibles")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @APIResponse(responseCode = "200", description = "Los rolees")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "Los rolees", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Collection.class, readOnly = true, description = "los rolees", required = true, name = "rolees")))
    public List<Role> findAll() {
        
        return roleRepository.findAll();
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Role findByIdrole">
    @GET
    @RolesAllowed({"admin"})
    @Path("{idrole}")
    @Operation(summary = "Busca un role por el idrole", description = "Busqueda de role por idrole")
    @APIResponse(responseCode = "200", description = "El role")
    @APIResponse(responseCode = "404", description = "Cuando no existe el idrole")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El role", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Role.class)))
    public Role findByIdrole(
            @Parameter(description = "El idrole", required = true, example = "1", schema = @Schema(type = SchemaType.NUMBER)) @PathParam("idrole") Long idrole) {

   

        return roleRepository.findByPk(idrole).orElseThrow(
                () -> new WebApplicationException("No hay role con idrole " + idrole, Response.Status.NOT_FOUND));

    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Role findByRole(String role)">
    @GET
    @RolesAllowed({"admin"})
    @Path("role")
    @Operation(summary = "Busca un role por el idrole", description = "Busqueda de role por role")
    @APIResponse(responseCode = "200", description = "El role")
    @APIResponse(responseCode = "404", description = "Cuando no existe el role")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El role", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Role.class)))
    public Role findByRole(
            @Parameter(description = "El idrole", required = true, example = "1", schema = @Schema(type = SchemaType.STRING)) @QueryParam("role")String role) {

   

        return roleRepository.findByRole(role).orElseThrow(
                () -> new WebApplicationException("No hay role con role " + role, Response.Status.NOT_FOUND));

    }
// </editor-fold>
    
    
    
    
    // <editor-fold defaultstate="collapsed" desc="List<Role> lookup(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size)">

    @GET
    @Path("lookup")
    @RolesAllowed({"admin"})
    @Operation(summary = "Busca un appconfiguration", description = "Busqueda de user por search")
    @APIResponse(responseCode = "200", description = "Role")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Role.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public List<Role> lookup(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size) {
        List<Role> suggestions = new ArrayList<>();
        try {

        Search search = DocumentUtil.convertForLookup(filter, sort, page, size);
        suggestions = roleRepository.lookup(search);

        } catch (Exception e) {
       
          MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + "error: " + e.getLocalizedMessage());
        }

        return suggestions;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Response save">
    @POST
    @RolesAllowed({"admin"})
    @Operation(summary = "Inserta un nuevo role", description = "Inserta un nuevo role")
    @APIResponse(responseCode = "201", description = "Cuanoo se crea un  role")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response save(
            @RequestBody(description = "Crea un nuevo role.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Role.class))) Role role) {


        Optional<Role> roleOptional=roleRepository.save(role);
        if(roleOptional.isPresent()){
            saveHistory(role);
               return Response.status(201).entity(roleOptional.get()).build();
        }else{
              return Response.status(400).entity("Error " + roleRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Response update">

    @PUT
    @RolesAllowed({"admin"})
    @Operation(summary = "Inserta un nuevo role", description = "Inserta un nuevo role")
    @APIResponse(responseCode = "201", description = "Cuanoo se crea un  role")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response update(
            @RequestBody(description = "Crea un nuevo role.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Role.class))) Role role) {


     if(roleRepository.update(role)){
         saveHistory(role);
               return Response.status(201).entity(role).build();
        }else{
              return Response.status(400).entity("Error " + roleRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Response delete">
    @DELETE
    @RolesAllowed({"admin"})
    @Path("{idrole}")
    @Operation(summary = "Elimina un role por  idrole", description = "Elimina un role por su idrole")
    @APIResponse(responseCode = "200", description = "Cuando elimina el role")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response delete(
            @Parameter(description = "El elemento idrole", required = true, example = "1", schema = @Schema(type = SchemaType.NUMBER)) @PathParam("idrole") Long idrole) {
         if(roleRepository.deleteByPk(idrole) ==0L){
              return Response.status(201).entity(Boolean.TRUE).build();
        }else{
            return Response.status(400).entity("Error " + roleRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
    // </editor-fold>
    
     // <editor-fold defaultstate="collapsed" desc="Long count(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size)">

    @GET
    @Path("count")
    @RolesAllowed({"admin"})
    @Operation(summary = "Cuenta ", description = "Cuenta role")
    @APIResponse(responseCode = "200", description = "contador")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Role.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public Long count(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size) {
       Long result = 0L;
        try {

        Search search = DocumentUtil.convertForLookup(filter, sort, page, size);
        result = roleRepository.count(search);

        } catch (Exception e) {
       
          MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + "error: " + e.getLocalizedMessage());
        }

        return result;
    }

    // </editor-fold>
         // <editor-fold defaultstate="collapsed" desc="private void saveHistory(Role role)">
    
    private void saveHistory(Role role){
        try {
                History history = new History.Builder()                 
               .collection("role")
                    .idcollection(role.getIdrole().toString())
                    .database("accreditation")
                    .data(role.toString())
                    .actionHistory(role.getActionHistory().get(role.getActionHistory().size()-1)                  )
                     .build();
            historyRepository.save(history);
        } catch (Exception e) {
           ConsoleUtil.error("saveHistory() "+e.getLocalizedMessage());
        }
    }
     
    
// </editor-fold>
    
    
      // <editor-fold defaultstate="collapsed" desc="List<Role> likeByRole(@QueryParam("role") String role)">

    @GET
    @Path("likebyrole")
    @RolesAllowed({"admin"})
    @Operation(summary = "Busca un user", description = "Busqueda de user usando like%")
    @APIResponse(responseCode = "200", description = "DepartamentView")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Role.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public List<Role> likeByRole(@QueryParam("role") String role) {
        List<Role> suggestions = new ArrayList<>();
        try {

       
        suggestions = roleRepository.likeByRole(role);

        } catch (Exception e) {
       
          MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + "error: " + e.getLocalizedMessage());
        }

        return suggestions;
    }

    // </editor-fold>
}
