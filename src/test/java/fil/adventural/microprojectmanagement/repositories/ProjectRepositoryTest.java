package fil.adventural.microprojectmanagement.repositories;

import fil.adventural.microprojectmanagement.models.Project;
import fil.adventural.microprojectmanagement.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class ProjectRepositoryTest {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    private User user;

    private Project project;

    @BeforeEach
    void setUp() {
        user = User.builder().email("test@gmail.com").projects(new ArrayList<>()).isEnabled(true).build();
        project = Project.builder().title("test").members(new ArrayList<>()).build();
    }

    @Test
    void testFindByUserEmail_WillReturnAllProjectOfThatUser() {
        user = entityManager.persist(user);

        user.getProjects().add(project);

        project.getMembers().add(user);

        project = entityManager.persist(project);

        assertThat(projectRepository.findProjectsByUserEmail(user.getEmail())).isEqualTo(Collections.singletonList(project));
    }

    @Test
    void testFindByUserId_WillReturnAllProjectOfThatUser() {
        user = entityManager.persist(user);

        user.getProjects().add(project);

        project = entityManager.persist(project);

        assertThat(projectRepository.findProjectsByUserId(user.getId())).isEqualTo(Collections.singletonList(project));
    }

    @Test
    void testFindProjectById_ByUserId_WillReturnAllProjectOfThatUser() {
        user = entityManager.persist(user);

        user.getProjects().add(project);

        project = entityManager.persist(project);

        assertThat(projectRepository.findProjectById_ByUserId(project.getId(), user.getId())).isEqualTo(project);
    }

    @Test
    void testAddMemberToProject() {
        entityManager.persist(user);

        User user1 = User.builder().email("test@gmail.com").projects(new ArrayList<>()).isEnabled(true).build();

        entityManager.persist(user1);

        project.addMember(user);

        user.addProject(project);

        project = entityManager.persist(project);

        //Add the member to the project
        user1.addProject(project);

        projectRepository.save(project);

        assertThat(userRepository.getOne(user1.getId()).getProjects()).contains(project);
    }

    @Test
    void testAddProject_WillHaveDefaultColorAndDefaultIsFavouriteOfFalse(){
        //Need to have builder default since builder not going to take default value
        project = entityManager.persist(project);
        assertThat(project.isFavourite()).isFalse();
        assertThat(project.getColor()).isEqualTo("#6800fa");
    }
}
