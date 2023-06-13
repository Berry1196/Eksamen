package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.AssignmentDTO;
import facades.AssignmentFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;

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
    public String createAssignment(String assignment) {
        AssignmentDTO assignmentDTO = GSON.fromJson(assignment, AssignmentDTO.class);
        AssignmentDTO createdAssignment = FACADE.createAssignment(assignmentDTO);
        return GSON.toJson(createdAssignment);
    }

}
