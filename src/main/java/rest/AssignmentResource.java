package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.AssignmentDTO;
import facades.AssignmentFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("assignment")
public class AssignmentResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final AssignmentFacade FACADE = AssignmentFacade.getAssignmentFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("all")
    public String getAllAssignments() {
        return GSON.toJson(FACADE.getAllAssignments());
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response createAssignment(AssignmentDTO assignmentDTO, @QueryParam("user_name") List<String> userNames) {
        try {
            AssignmentDTO newAssignment = FACADE.createAssignment(assignmentDTO, userNames);
            return Response.ok(newAssignment).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }




}
