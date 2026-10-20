package webservices;

import entities.UniteEnseignement;
import metiers.UniteEnseignementBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/ue")
public class UniteEnsWS {
    static UniteEnseignementBusiness helper = new UniteEnseignementBusiness();

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response
                .status(Response.Status.OK)
                .entity(helper.getListeUE())
                .build();
    }

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

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUEById(@PathParam("id") int code) {
        UniteEnseignement ue = helper.getUEByCode(code);
        if (ue != null) {
            return Response.status(Response.Status.OK).entity(ue).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("object not found").build();
    }

    @Path("/add")
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.TEXT_PLAIN)
    public Response addUE(UniteEnseignement ue) {
        if (helper.addUniteEnseignement(ue)) {
            return Response.status(Response.Status.CREATED)
                    .entity("object added").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("object not added").build();
        }
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
    public Response createUE(UniteEnseignement ue) {
        if (helper.addUniteEnseignement(ue)) {
            return Response.status(Response.Status.CREATED)
                    .entity("object added").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("object not added").build();
        }
    }

    @Path("/delete/{id}")
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteUE(@PathParam("id") int code) {
        if (helper.deleteUniteEnseignement(code)) {
            return Response.status(Response.Status.OK)
                    .entity("object deleted").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("object not FOUND, please").build();
        }
    }

    @Path("/{id}")
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteUEById(@PathParam("id") int code) {
        if (helper.deleteUniteEnseignement(code)) {
            return Response.status(Response.Status.OK)
                    .entity("object deleted").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("object not FOUND, please").build();
        }
    }

    @Path("/update/{id}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateUE(UniteEnseignement ue, @PathParam("id") int code) {
        if (helper.updateUniteEnseignement(code, ue)) {
            return Response.status(Response.Status.OK)
                    .entity("object updated").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("object not updated").build();
        }
    }

    @Path("/{id}")
    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateUEById(UniteEnseignement ue, @PathParam("id") int code) {
        if (helper.updateUniteEnseignement(code, ue)) {
            return Response.status(Response.Status.OK)
                    .entity("object updated").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("object not updated").build();
        }
    }
}
