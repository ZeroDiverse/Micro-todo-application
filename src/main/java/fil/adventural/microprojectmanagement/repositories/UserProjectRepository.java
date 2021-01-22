package fil.adventural.microprojectmanagement.repositories;

import fil.adventural.microprojectmanagement.models.UserProjectOrder;
import fil.adventural.microprojectmanagement.models.UserProjectOrderId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProjectRepository extends JpaRepository<UserProjectOrder, UserProjectOrderId> {
}
