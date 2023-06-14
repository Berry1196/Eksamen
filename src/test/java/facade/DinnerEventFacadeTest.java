package facade;

import facades.DinnerEventFacade;
import utils.EMF_Creator;
import entities.Dinnerevent;
import dtos.DinnerEventDTO;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DinnerEventFacadeTest {

    private static EntityManagerFactory emf;
    private static DinnerEventFacade facade;
    private Dinnerevent dinnerEvent, dinnerEvent1;

    public DinnerEventFacadeTest() {}

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = DinnerEventFacade.getDinnerEventFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
        //Since we are using a drop-and-create we dont have to do anything here
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        dinnerEvent = new Dinnerevent("20:00", "Dinner Party 3", "Location 3", "Dish 3", 200);
        dinnerEvent1 = new Dinnerevent("21:00", "Dinner Party 4", "Location 4", "Dish 4", 250);
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Dinnerevent").executeUpdate();

            // create some DinnerEvent objects
            em.persist(dinnerEvent);
            em.persist(dinnerEvent1);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
        //Remove any data after each test was run
    }

    @Test
    public void testCreateEvent() {
        String time = "20:00";
        String eventName = "Dinner Party 3";
        String location = "Location 3";
        String dish = "Dish 3";
        int pricePerPerson = 200;

        DinnerEventDTO dinnerEventDTO = new DinnerEventDTO(time, eventName, location, dish, pricePerPerson);

        DinnerEventDTO result = facade.createEvent(dinnerEventDTO);

        assertNotNull(result);
        assertEquals(time, result.getTime());
        assertEquals(eventName, result.getEventName());
        assertEquals(location, result.getLocation());
        assertEquals(dish, result.getDish());
        assertEquals(pricePerPerson, result.getPricePerPerson());
    }

    @Test
    public void testGetAllDinnerEvents() {
        List<DinnerEventDTO> dinnerEvents = facade.getAllDinnerEvents();
        assertEquals(2, dinnerEvents.size()); // expected to be 2 as we created 2 in the setUp
    }

    @Test
    public void testDeleteDinnerEvent() {
        facade.deleteDinnerEvent(dinnerEvent.getId());
        List<DinnerEventDTO> dinnerEvents = facade.getAllDinnerEvents();
        assertEquals(1, dinnerEvents.size()); // expected to be 1 as we deleted 1 in the test
    }
}
