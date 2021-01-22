package fil.adventural.microprojectmanagement.repositories;

import fil.adventural.microprojectmanagement.models.Project;
import fil.adventural.microprojectmanagement.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class ProjectRepositoryTest {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testFindByUserEmail_WillReturnAllProjectOfThatUser() {
        User user = User.builder().email("test@gmail.com").isEnabled(true).build();

        entityManager.persist(user);

        Project project = Project.builder().title("test").isFavourite(true).members(Collections.singletonList(user)).build();

        user.setProjects(Collections.singletonList(project));

        entityManager.persist(project);

        assertThat(projectRepository.findProjectsByUserEmail("test@gmail.com")).isEqualTo(Collections.singletonList(project));
    }


    @Test
    void testFindByUserId_WillReturnAllProjectOfThatUser() {
        User user = User.builder().email("test@gmail.com").isEnabled(true).build();

        entityManager.persist(user);

        Project project = Project.builder().title("test").isFavourite(true).members(Collections.singletonList(user)).build();

        user.setProjects(Collections.singletonList(project));

        entityManager.persist(project);

        project.setId(1L);

        assertThat(projectRepository.findProjectsByUserId(1L)).isEqualTo(Collections.singletonList(project));
    }

    @Test
    void testFindProjectById_ByUserId_WillReturnAllProjectOfThatUser() {
        User user = User.builder().email("test@gmail.com").isEnabled(true).build();

        entityManager.persist(user);

        Project project = Project.builder().title("test").isFavourite(true).members(Collections.singletonList(user)).build();

        user.setProjects(Collections.singletonList(project));

        entityManager.persist(project);

        project.setId(1L);

        assertThat(projectRepository.findProjectById_ByUserId(1L, 1L)).isEqualTo(project);
    }
}
