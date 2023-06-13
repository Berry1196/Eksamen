package facades;

import dtos.AssignmentDTO;
import entities.Assignment;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class AssignmentFacade {
    private EntityManagerFactory emf;

    public AssignmentFacade(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public AssignmentDTO createAssignment(AssignmentDTO assignmentDTO) {
        EntityManager em = getEntityManager();
        Assignment assignment = new Assignment(assignmentDTO.getFamilyName(), assignmentDTO.getCreateDate(), assignmentDTO.getContactInfo());
        try {
            em.getTransaction().begin();
            em.persist(assignment);
            em.getTransaction().commit();
            return new AssignmentDTO(assignment);
        } finally {
            em.close();
        }
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
}
