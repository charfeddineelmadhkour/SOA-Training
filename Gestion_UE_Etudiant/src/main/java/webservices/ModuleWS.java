package webservices;

import entities.Module;
import entities.UniteEnseignement;
import metiers.ModuleBusiness;
import metiers.UniteEnseignementBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/{modulePath: modules?}")
public class ModuleWS {
    static ModuleBusiness helper = new ModuleBusiness();
    static UniteEnseignementBusiness ueHelper = new UniteEnseignementBusiness();

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.status(Response.Status.OK).entity(helper.getAllModules()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllModules() {
        return Response.status(Response.Status.OK).entity(helper.getAllModules()).build();
    }

    @Path("/add")
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.TEXT_PLAIN)
    public Response addModule(Module module) {
        if (helper.addModule(module)) {
            return Response
                    .status(Response.Status.CREATED)
                    .entity("Module added").build();
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Module not added")
                    .build();
        }
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.APPLICATION_JSON)
    public Response createModule(Module module) {
        if (helper.addModule(module)) {
            return Response
                    .status(Response.Status.CREATED)
                    .entity(helper.getAllModules())
                    .build();
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

    @Path("/UE")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getModulesByUE(@QueryParam("codeUE") Integer codeUE) {
        if (codeUE == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        UniteEnseignement ue = ueHelper.getUEByCode(codeUE);
        if (ue == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        List<Module> modules = helper.getModulesByUE(ue);
        return Response.status(Response.Status.OK).entity(modules).build();
    }

    @Path("/ue")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getModulesByUeLower(@QueryParam("codeUE") Integer codeUE) {
        return getModulesByUE(codeUE);
    }

    @Path("/{matricule}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getModuleByMatricule(@PathParam("matricule") String matricule) {
        Module module = helper.getModuleByMatricule(matricule);
        if (module != null) {
            return Response.status(Response.Status.OK).entity(module).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @Path("/delete/{matricule}")
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteModule(@PathParam("matricule") String matricule) {
        if (helper.deleteModule(matricule)) {
            return Response.status(Response.Status.OK).entity("Module deleted").build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Module not found").build();
    }

    @Path("/{matricule}")
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteModuleByMatricule(@PathParam("matricule") String matricule) {
        if (helper.deleteModule(matricule)) {
            return Response.status(Response.Status.OK).entity("Module deleted").build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Module not found").build();
    }

    @Path("/update/{matricule}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateModule(@PathParam("matricule") String matricule, Module updatedModule) {
        if (helper.updateModule(matricule, updatedModule)) {
            return Response.status(Response.Status.OK).entity("Module updated").build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Module not updated").build();
    }

    @Path("/{matricule}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateModuleByMatricule(@PathParam("matricule") String matricule, Module updatedModule) {
        if (helper.updateModule(matricule, updatedModule)) {
            return Response.status(Response.Status.OK).entity("Module updated").build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Module not updated").build();
    }
}
