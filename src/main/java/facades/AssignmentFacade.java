package facades;

import dtos.AssignmentDTO;
import entities.Assignment;
import entities.Dinnerevent;
import entities.User;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class AssignmentFacade {
    private static EntityManagerFactory emf;
    private static AssignmentFacade instance;

   public static AssignmentFacade getAssignmentFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new AssignmentFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public AssignmentDTO createAssignment(AssignmentDTO assignmentDTO) {
        Assignment assignment = new Assignment(assignmentDTO.getFamilyName(), assignmentDTO.getCreateDate(), assignmentDTO.getContactInfo());

        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(assignment);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Failed to create assignment", e);
        } finally {
            em.close();
        }

        return new AssignmentDTO(assignment);
    }

    public void addUsersToAssignment(Long assignmentId, List<String> userNames) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();

            Assignment assignment = em.find(Assignment.class, assignmentId);
            if (assignment == null) {
                throw new IllegalArgumentException("Assignment not found with ID: " + assignmentId);
            }

            for (String userName : userNames) {
                User user = em.createQuery("SELECT u FROM User u WHERE u.user_name = :userName", User.class)
                        .setParameter("userName", userName)
                        .getSingleResult();

                if (user != null) {
                    assignment.getUsers().add(user);
                    user.getAssignmentList().add(assignment);
                }
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Failed to add users to assignment", e);
        } finally {
            em.close();
        }
    }

    public List<AssignmentDTO> getAllAssignments() {
        EntityManager em = getEntityManager();
        List<Assignment> assignments;
        try {
            assignments = em.createQuery("SELECT a FROM Assignment a", Assignment.class).getResultList();

        } finally {
            em.close();
        }
        return AssignmentDTO.getDtos(assignments);
    }

    public void deleteAssignment(Long id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Assignment assignment = em.find(Assignment.class, id);
            if (assignment != null) {
                em.remove(assignment);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Assignment> getAllAssignmentsByUserId(Long userId) {
        EntityManager em = getEntityManager();
        try {
            User user = em.find(User.class, userId);
            if (user != null) {
                return user.getAssignmentList();
            } else {
                throw new IllegalArgumentException("No User found with provided id");
            }
        } finally {
            em.close();
        }
    }

    public AssignmentDTO assignDinnereventToAssignment(long assignmentId, long dinnereventId) {
        EntityManager em = emf.createEntityManager();
        Assignment assignment;
        Dinnerevent dinnerevent;
        try {
            em.getTransaction().begin();
            assignment = em.find(Assignment.class, assignmentId);
            dinnerevent = em.find(Dinnerevent.class, dinnereventId);
            if (assignment != null && dinnerevent != null) {
                List<Assignment> assignments = dinnerevent.getAssignments();
                assignments.add(assignment);
                dinnerevent.setAssignments(assignments);
                assignment.setDinnerevent(dinnerevent);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new AssignmentDTO(assignment);
    }



    public static void main(String[] args) {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        AssignmentFacade assignmentFacade = getAssignmentFacade(emf);
        //AssignmentDTO assignmentDTO = new AssignmentDTO("Test", "Test", "Test");
        //assignmentFacade.createAssignment(assignmentDTO);
        assignmentFacade.getAllAssignments().forEach(System.out::println);
    }
}
