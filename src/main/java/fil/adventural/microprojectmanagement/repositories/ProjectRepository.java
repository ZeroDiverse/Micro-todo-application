package fil.adventural.microprojectmanagement.repositories;

import fil.adventural.microprojectmanagement.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT p FROM Project p JOIN p.members u WHERE u.email=:userEmail")
    List<Project> findProjectsByUserEmail(String userEmail);

    @Query("SELECT p FROM Project p JOIN p.members u WHERE u.id=:userId")
    List<Project> findProjectsByUserId(Long userId);

    @Query("SELECT p FROM Project p JOIN p.members u WHERE u.id=:userId AND p.id =:projectId")
    Project findProjectById_ByUserId(Long projectId, Long userId);
}
