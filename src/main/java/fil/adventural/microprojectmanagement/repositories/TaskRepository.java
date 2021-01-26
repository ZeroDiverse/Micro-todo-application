package fil.adventural.microprojectmanagement.repositories;

import fil.adventural.microprojectmanagement.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllTasks_ByOwnerId(Long id);

    List<Task> findAllTasks_ByProjectIsNullAndOwnerId(Long ownerId);
}
