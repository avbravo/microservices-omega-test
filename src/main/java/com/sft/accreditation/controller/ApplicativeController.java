/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sft.accreditation.controller;

import com.jmoordb.core.model.Search;
import com.jmoordb.core.util.ConsoleUtil;
import com.jmoordb.core.util.DocumentUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.sft.model.Applicative;
import com.sft.model.History;
import com.sft.model.Applicative;
import com.sft.repository.ApplicativeRepository;
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
@Path("applicative")
@Tag(name = "Información del applicative", description = "End-point para entidad Applicative")
@RolesAllowed({"admin"})
public class ApplicativeController {

    // <editor-fold defaultstate="collapsed" desc="Inject">
    @Inject
    ApplicativeRepository applicativeRepository;

    @Inject
    HistoryRepository historyRepository;

    @Inject
    private MetricRegistry registry;

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="findAll">
    @GET
    @RolesAllowed({"admin"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Timed(name = "applicativeesFindAll",
            description = "Monitorea el tiempo en que se obtiene la lista de todos los applicativees",
            unit = MetricUnits.MILLISECONDS, absolute = true)
    @Operation(summary = "Obtiene todos los applicativees", description = "Retorna todos los applicativees disponibles")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @APIResponse(responseCode = "200", description = "Los applicativees")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "Los applicativees", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Collection.class, readOnly = true, description = "los applicativees", required = true, name = "applicativees")))
    public List<Applicative> findAll() {

        return applicativeRepository.findAll();
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Applicative findByIdapplicative">
    @GET
    @RolesAllowed({"admin"})
    @Path("{idapplicative}")
    @Operation(summary = "Busca un applicative por el idapplicative", description = "Busqueda de applicative por idapplicative")
    @APIResponse(responseCode = "200", description = "El applicative")
    @APIResponse(responseCode = "404", description = "Cuando no existe el idapplicative")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El applicative", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Applicative.class)))
    public Applicative findByIdapplicative(
            @Parameter(description = "El idapplicative", required = true, example = "1", schema = @Schema(type = SchemaType.NUMBER)) @PathParam("idapplicative") Long idapplicative) {

        return applicativeRepository.findByPk(idapplicative).orElseThrow(
                () -> new WebApplicationException("No hay applicative con idapplicative " + idapplicative, Response.Status.NOT_FOUND));

    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Response save">
    @POST
    @RolesAllowed({"admin"})
    @Operation(summary = "Inserta un nuevo applicative", description = "Inserta un nuevo applicative")
    @APIResponse(responseCode = "201", description = "Cuanoo se crea un  applicative")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response save(
            @RequestBody(description = "Crea un nuevo applicative.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Applicative.class))) Applicative applicative) {

        Optional<Applicative> applicativeOptional = applicativeRepository.save(applicative);
        if (applicativeOptional.isPresent()) {
            saveHistory(applicative);
            return Response.status(201).entity(applicativeOptional.get()).build();
        } else {
            return Response.status(400).entity("Error " + applicativeRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Response update">

    @PUT
    @RolesAllowed({"admin"})
    @Operation(summary = "Inserta un nuevo applicative", description = "Inserta un nuevo applicative")
    @APIResponse(responseCode = "201", description = "Cuanoo se crea un  applicative")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response update(
            @RequestBody(description = "Crea un nuevo applicative.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Applicative.class))) Applicative applicative) {

        if (applicativeRepository.update(applicative)) {
            saveHistory(applicative);
            return Response.status(201).entity(applicative).build();
        } else {
            return Response.status(400).entity("Error " + applicativeRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Response delete">
    @DELETE
    @RolesAllowed({"admin"})
    @Path("{idapplicative}")
    @Operation(summary = "Elimina un applicative por  idapplicative", description = "Elimina un applicative por su idapplicative")
    @APIResponse(responseCode = "200", description = "Cuando elimina el applicative")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    public Response delete(
            @Parameter(description = "El elemento idapplicative", required = true, example = "1", schema = @Schema(type = SchemaType.NUMBER)) @PathParam("idapplicative") Long idapplicative) {
        if (applicativeRepository.deleteByPk(idapplicative) == 0L) {
            return Response.status(201).entity(Boolean.TRUE).build();
        } else {
            return Response.status(400).entity("Error " + applicativeRepository.getJmoordbException().getLocalizedMessage()).build();
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="List<Applicative> lookup(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size)">
    @GET
    @Path("lookup")
    @RolesAllowed({"admin"})
    @Operation(summary = "Busca un appconfiguration", description = "Busqueda de user por search")
    @APIResponse(responseCode = "200", description = "Applicative")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Applicative.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public List<Applicative> lookup(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size) {
        List<Applicative> suggestions = new ArrayList<>();
        try {

            Search search = DocumentUtil.convertForLookup(filter, sort, page, size);
            suggestions = applicativeRepository.lookup(search);

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
    @Operation(summary = "Cuenta ", description = "Cuenta applicative")
    @APIResponse(responseCode = "200", description = "contador")
    @APIResponse(responseCode = "404", description = "Cuando no existe la condicion en el search")
    @APIResponse(responseCode = "500", description = "Servidor inalcanzable")
    @Tag(name = "BETA", description = "Esta api esta en desarrollo")
    @APIResponse(description = "El search", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Applicative.class)))

    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public Long count(@QueryParam("filter") String filter, @QueryParam("sort") String sort, @QueryParam("page") Integer page, @QueryParam("size") Integer size) {
        Long result = 0L;
        try {

            Search search = DocumentUtil.convertForLookup(filter, sort, page, size);
            result = applicativeRepository.count(search);

        } catch (Exception e) {

            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + "error: " + e.getLocalizedMessage());
        }

        return result;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="private void saveHistory(Applicative applicative)">
    private void saveHistory(Applicative applicative) {
        try {
            History history = new History.Builder()
                    .collection("applicative")
                    .idcollection(applicative.getIdapplicative().toString())
                    .database("accreditation")
                    .data(applicative.toString())
                    .actionHistory(applicative.getActionHistory().get(applicative.getActionHistory().size() - 1))
                    .build();
            historyRepository.save(history);
        } catch (Exception e) {
            ConsoleUtil.error("saveHistory() " + e.getLocalizedMessage());
        }
    }

// </editor-fold>
}
