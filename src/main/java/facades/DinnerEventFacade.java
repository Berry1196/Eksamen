package facades;

import dtos.DinnerEventDTO;
import entities.Assignment;
import entities.Dinnerevent;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.stream.Collectors;

public class DinnerEventFacade {
    private static DinnerEventFacade instance;
    private static EntityManagerFactory emf;


    private DinnerEventFacade() {
    }

    public static DinnerEventFacade getDinnerEventFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new DinnerEventFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //Create DinnerEvent
    public DinnerEventDTO createEvent(DinnerEventDTO dinnerEventDTO) {
        EntityManager em = getEntityManager();
        Dinnerevent dinnerEvent = new Dinnerevent(dinnerEventDTO.getTime(), dinnerEventDTO.getEventName(), dinnerEventDTO.getLocation(), dinnerEventDTO.getDish(), dinnerEventDTO.getPricePerPerson());
        try {
            em.getTransaction().begin();
            em.persist(dinnerEvent);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new DinnerEventDTO(dinnerEvent);
    }

    //Get all DinnerEvents
    public List<DinnerEventDTO> getAllDinnerEvents() {
        EntityManager em = getEntityManager();
        try {
            List<Dinnerevent> dinnerEvents = em.createQuery("SELECT d FROM Dinnerevent d", Dinnerevent.class).getResultList();
            return dinnerEvents.stream()
                    .map(DinnerEventDTO::new)
                    .collect(Collectors.toList());
        } finally {
            em.close();
        }
    }

    //Get DinnerEvent by ID
    public DinnerEventDTO getDinnerEventById(Long id) {
        EntityManager em = getEntityManager();
        try {
            Dinnerevent dinnerEvent = em.find(Dinnerevent.class, id);
            return new DinnerEventDTO(dinnerEvent);
        } finally {
            em.close();
        }
    }

    //Delete DinnerEvent
    public void deleteDinnerEvent(Long id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Dinnerevent dinnerEvent = em.find(Dinnerevent.class, id);
            em.remove(dinnerEvent);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    //Update DinnerEvent
    public DinnerEventDTO updateDinnerEvent(DinnerEventDTO dinnerEventDTO) {
        EntityManager em = getEntityManager();
        Dinnerevent dinnerEvent = em.find(Dinnerevent.class, dinnerEventDTO.getId());
        try {
            em.getTransaction().begin();
            dinnerEvent.setTime(dinnerEventDTO.getTime());
            dinnerEvent.setLocation(dinnerEventDTO.getLocation());
            dinnerEvent.setDish(dinnerEventDTO.getDish());
            dinnerEvent.setPricePerPerson(dinnerEventDTO.getPricePerPerson());
            em.merge(dinnerEvent);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new DinnerEventDTO(dinnerEvent);
    }

    //Add Assignment to DinnerEvent through id
    public DinnerEventDTO addAssignmentToDinnerEvent(Long dinnerEventId, Long assignmentId) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Dinnerevent dinnerEvent = em.find(Dinnerevent.class, dinnerEventId);
            Assignment assignment = em.find(Assignment.class, assignmentId);
            dinnerEvent.getAssignments().add(assignment);
            em.merge(dinnerEvent);
            em.getTransaction().commit();
            return new DinnerEventDTO(dinnerEvent);
        } finally {
            em.close();
        }

    }

    public static void main(String[] args) {
        DinnerEventFacade def = DinnerEventFacade.getDinnerEventFacade(EMF_Creator.createEntityManagerFactory());
        def.createEvent(new DinnerEventDTO("2021-05-05", "Pizza hos frede", "humlebæk", "Pizza", 50));
    }

}
