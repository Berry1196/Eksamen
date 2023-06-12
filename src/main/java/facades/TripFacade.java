package facades;

import dtos.TripDTO;
import entities.Trip;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class TripFacade {
    private static EntityManagerFactory emf;
    private static TripFacade instance;

    private TripFacade() {
    }

    public static TripFacade getTripFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TripFacade();
        }
        return instance;
    }

    public TripDTO createTrip(TripDTO tripDTO) {
        EntityManager em = emf.createEntityManager();
        Trip trip = new Trip(tripDTO.getName(), tripDTO.getDate(), tripDTO.getTime(), tripDTO.getLocation(), tripDTO.getDuration(), tripDTO.getPackingList());
        try {
            em.getTransaction().begin();
            em.persist(trip);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new TripDTO(trip);
    }
    public void deleteTrip(Long tripId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, tripId);
            if (trip != null) {
                // Remove associated users
                trip.getUser().clear();

                // Remove associated guides
                trip.getGuide().clear();

                em.remove(trip);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public TripDTO editTrip(TripDTO tripDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, tripDTO.getId());
            if (trip != null) {
                trip.setName(tripDTO.getName());
                trip.setDate(tripDTO.getDate());
                trip.setTime(tripDTO.getTime());
                trip.setLocation(tripDTO.getLocation());
                trip.setDuration(tripDTO.getDuration());
                trip.setPackingList(tripDTO.getPackingList());
            }
            em.getTransaction().commit();
            return new TripDTO(trip);
        } finally {
            em.close();
        }
    }



    public List<TripDTO> getAllTrips() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Trip> query = em.createQuery("SELECT t FROM Trip t", Trip.class);
            List<Trip> trips = query.getResultList();
            return TripDTO.getDTOs(trips);
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {
        TripFacade facade = TripFacade.getTripFacade(EMF_Creator.createEntityManagerFactory());

        System.out.println(facade.getAllTrips());
    }
}
