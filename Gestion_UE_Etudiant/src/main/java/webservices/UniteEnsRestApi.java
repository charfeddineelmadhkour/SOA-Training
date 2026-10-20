package webservices;

import entities.UniteEnseignement;
import metiers.UniteEnseignementBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/UE")
public class UniteEnsRestApi {

    private static UniteEnseignementBusiness helper = new UniteEnseignementBusiness();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUEs(@QueryParam("semestre") Integer semestre,
                           @QueryParam("code") Integer code,
                           @QueryParam("domaine") String domaine) {
        if (code != null) {
            UniteEnseignement ue = helper.getUEByCode(code);
            if (ue != null) {
                return Response.status(Response.Status.OK).entity(ue).build();
            }
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (semestre != null) {
            List<UniteEnseignement> list = helper.getUEBySemestre(semestre);
            return Response.status(Response.Status.OK).entity(list).build();
        }
        if (domaine != null) {
            List<UniteEnseignement> list = helper.getUEByDomaine(domaine);
            return Response.status(Response.Status.OK).entity(list).build();
        }
        return Response.status(Response.Status.OK).entity(helper.getListeUE()).build();
    }

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListUe() {
        return Response
                .status(Response.Status.OK)
                .entity(helper.getListeUE())
                .build();
    }

    @Path("/{code}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUEById(@PathParam("code") int code) {
        UniteEnseignement ue = helper.getUEByCode(code);
        if (ue != null) {
            return Response.status(Response.Status.OK).entity(ue).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.TEXT_PLAIN)
    public Response createUe(UniteEnseignement ue) {
        if (helper.addUniteEnseignement(ue)) {
            return Response
                    .status(Response.Status.CREATED)
                    .entity("Unité d'enseignement ajoutée avec succès.")
                    .build();
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Échec de l'ajout de l'unité d'enseignement.")
                    .build();
        }
    }

    @Path("/add")
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.TEXT_PLAIN)
    public Response addUe(UniteEnseignement ue) {
        return createUe(ue);
    }

    @Path("/{code}")
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteUe(@PathParam("code") int code) {
        if (helper.deleteUniteEnseignement(code)) {
            return Response.status(Response.Status.OK)
                    .entity("Unité d'enseignement supprimée avec succès.")
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Unité d'enseignement introuvable.")
                .build();
    }

    @Path("/delete/{code}")
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteUeAlt(@PathParam("code") int code) {
        return deleteUe(code);
    }

    @Path("/{code}")
    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateUe(@PathParam("code") int code, UniteEnseignement ue) {
        if (helper.updateUniteEnseignement(code, ue)) {
            return Response.status(Response.Status.OK)
                    .entity("Unité d'enseignement modifiée avec succès.")
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Unité d'enseignement introuvable.")
                .build();
    }

    @Path("/update/{code}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateUeAlt(@PathParam("code") int code, UniteEnseignement ue) {
        return updateUe(code, ue);
    }
}
