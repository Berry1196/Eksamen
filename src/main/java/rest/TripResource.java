package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.TripFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("trip")
public class TripResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final TripFacade FACADE = TripFacade.getTripFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    //Get all trips
    @GET
    public String getAllTrips() {
        return GSON.toJson(FACADE.getAllTrips());
    }
}
