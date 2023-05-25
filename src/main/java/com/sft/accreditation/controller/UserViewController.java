/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sft.accreditation.controller;

import com.jmoordb.core.model.Search;
import com.jmoordb.core.util.DocumentUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.sft.model.UserView;
import com.sft.repository.HistoryRepository;
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
@Path("userview")
@Tag(name = "Información del user", description = "End-point para entidad UserViewView")
@RolesAllowed({"admin"})
public class UserViewController {

    
    // <editor-fold defaultstate="collapsed" desc="Inject">
    @Inject
    UserViewRepository userRepository;
    
      @Inject
HistoryRepository historyRepository;


// </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="findAll">
    @GET
    @RolesAllowed({"admin"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
        
    @Timed(name = "useresFindAll",
            description = "Monitorea el tiempo en que se obtiene la lista de todos los useres",
            unit = MetricUnits.MILLISECONDS, absolute = true)
    @Operation(summary = "Obtiene todos los useres", description = "Retorna todos los useres disponibles")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @APIResponse(responseCode = "200", description = "Los useres")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "Los useres", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Collection.class, readOnly = true, description = "los useres", required = true, name = "useres")))
    public List<UserView> findAll() {
       
        return userRepository.findAll();
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="UserView findByIduser">
    @GET
    @RolesAllowed({"admin"})
    @Path("{iduser}")
    @Operation(summary = "Busca un user por el iduser", description = "Busqueda de user por iduser")
    @APIResponse(responseCode = "200", description = "El user")
    @APIResponse(responseCode = "404", description = "Cuando no existe el iduser")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El user", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UserView.class)))
    public UserView findByIduser(
            @Parameter(description = "El iduser", required = true, example = "1", schema = @Schema(type = SchemaType.NUMBER)) @PathParam("iduser") Long iduser) {



        return userRepository.findByPk(iduser).orElseThrow(
                () -> new WebApplicationException("No hay user con iduser " + iduser, Response.Status.NOT_FOUND));

    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="UserView findByUsername">
    @GET
    @RolesAllowed({"admin"})
    @Path("username")
    @Operation(summary = "Busca un user por el usernam", description = "Busqueda de user por iduser")
    @APIResponse(responseCode = "200", description = "El user")
    @APIResponse(responseCode = "404", description = "Cuando no existe el username")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El user", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UserView.class)))
    
   
    public UserView findByUsername(@Parameter(description = "El username", required = true, example = "1", schema = @Schema(type = SchemaType.STRING)) @QueryParam("username") final String username) {



        return userRepository.findByUsername(username).orElseThrow(
                () -> new WebApplicationException("No hay user con username " + username, Response.Status.NOT_FOUND));

    }
//// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="List<UserView> lookup(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size)">

    @GET
    @Path("lookup")
    @RolesAllowed({"admin"})
    @Operation(summary = "Busca un user", description = "Busqueda de user por search")
    @APIResponse(responseCode = "200", description = "UserView")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UserView.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public List<UserView> lookup(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size) {
        List<UserView> suggestions = new ArrayList<>();
        try {

        Search search = DocumentUtil.convertForLookup(filter, sort, page, size);
        suggestions = userRepository.lookup(search);

        } catch (Exception e) {
       
          MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + "error: " + e.getLocalizedMessage());
        }

        return suggestions;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Response save">
    @POST
    @RolesAllowed({"admin"})
    @Operation(summary = "Inserta un nuevo user", description = "Inserta un nuevo user")
    @APIResponse(responseCode = "201", description = "Cuanoo se crea un  user")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response save(
            @RequestBody(description = "Crea un nuevo user.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserView.class))) UserView user) {


        Optional<UserView> userOptional=userRepository.save(user);
        if(userOptional.isPresent()){
               return Response.status(201).entity(userOptional.get()).build();
        }else{
              return Response.status(400).entity("Error " + userRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Response update">

    @PUT
    @RolesAllowed({"admin"})
    @Operation(summary = "Inserta un nuevo user", description = "Inserta un nuevo user")
    @APIResponse(responseCode = "201", description = "Cuanoo se crea un  user")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response update(
            @RequestBody(description = "Crea un nuevo user.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserView.class))) UserView user) {


    if(userRepository.update(user)){
               return Response.status(201).entity(user).build();
        }else{
              return Response.status(400).entity("Error " + userRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Response delete">
    @DELETE
    @RolesAllowed({"admin"})
    @Path("{iduser}")
    @Operation(summary = "Elimina un user por  iduser", description = "Elimina un user por su iduser")
    @APIResponse(responseCode = "200", description = "Cuando elimina el user")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response delete(
            @Parameter(description = "El elemento iduser", required = true, example = "1", schema = @Schema(type = SchemaType.NUMBER)) @PathParam("iduser") Long iduser) {
         if(userRepository.deleteByPk(iduser) ==0L){
              return Response.status(201).entity(Boolean.TRUE).build();
        }else{
            return Response.status(400).entity("Error " + userRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
    // </editor-fold>
    
     // <editor-fold defaultstate="collapsed" desc="Long count(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size)">

    @GET
    @Path("count")
    @RolesAllowed({"admin"})
    @Operation(summary = "Cuenta ", description = "Cuenta user")
    @APIResponse(responseCode = "200", description = "contador")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UserView.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public Long count(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size) {
       Long result = 0L;
        try {

        Search search = DocumentUtil.convertForLookup(filter, sort, page, size);
        result = userRepository.count(search);

        } catch (Exception e) {
       
          MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + "error: " + e.getLocalizedMessage());
        }

        return result;
    }

    // </editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc=" List<UserView> likeByName(@QueryParam("name") String name)">

    @GET
    @Path("likebyname")
    @RolesAllowed({"admin"})
    @Operation(summary = "Busca un user", description = "Busqueda de user usando like%")
    @APIResponse(responseCode = "200", description = "UserView")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UserView.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public List<UserView> likeByName(@QueryParam("name") String name) {
        List<UserView> suggestions = new ArrayList<>();
        try {

       
        suggestions = userRepository.likeByName(name);

        } catch (Exception e) {
       
          MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + "error: " + e.getLocalizedMessage());
        }

        return suggestions;
    }

    // </editor-fold>
}
