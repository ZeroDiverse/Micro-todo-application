package fil.adventural.microprojectmanagement.repositories;

import fil.adventural.microprojectmanagement.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Project repository to do action with projects
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    /**
     * Find projects by user email (unique)
     * @param userEmail user's email
     * @return list of projects by user email
     */
    @Query("SELECT p FROM Project p JOIN p.members u WHERE u.email=:userEmail")
    List<Project> findProjectsByUserEmail(String userEmail);

    /**
     * Find projects by user id
     * @param userId the user's id
     * @return the list of projects from user by user id
     */
    @Query("SELECT p FROM Project p JOIN p.members u WHERE u.id=:userId")
    List<Project> findProjectsByUserId(Long userId);

    /**
     * Find project by id and by user id
     * @param projectId project id
     * @param userId user id
     * @return the project that have project id and from user with user id
     */
    @Query("SELECT p FROM Project p JOIN p.members u WHERE u.id=:userId AND p.id =:projectId")
    Project findProjectById_ByUserId(Long projectId, Long userId);
}
