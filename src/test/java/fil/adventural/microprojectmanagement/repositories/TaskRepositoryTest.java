package fil.adventural.microprojectmanagement.repositories;

import fil.adventural.microprojectmanagement.models.Project;
import fil.adventural.microprojectmanagement.models.Task;
import fil.adventural.microprojectmanagement.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Task mockTask;

    private User mockUser;

    private Project mockProject;

    @BeforeEach
    void setUp() {
        mockTask = Task.builder().title("mock task").owner(null).build();
        mockUser = User.builder().email("mock@mock.com").personalTasks(new ArrayList<>()).build();
        mockProject = Project.builder().title("mock project").build();
    }

    @Test
    void testAddTaskToUser() {

        mockUser = entityManager.persist(mockUser);

        mockUser.addTask(mockTask);

        mockTask = entityManager.persist(mockTask);


        List<Task> taskList = taskRepository.findAllTasks_ByOwnerId(mockUser.getId());

        assertThat(taskList.size()).isEqualTo(1);

        assertThat(taskList).contains(mockTask);
    }

    @Test
    void testFindTaskThatDontHaveProject_AndByUserId_WillReturnListOfTasks() {

        mockUser = entityManager.persist(mockUser);

        mockUser.addTask(mockTask);

        mockTask = entityManager.persist(mockTask);

        mockProject = entityManager.persist(mockProject);

        Task mockTask1 = Task.builder().title("mock task 1").build();

        mockUser.addTask(mockTask1);

        mockProject.addTask(mockTask1);

        entityManager.persist(mockTask1);

        List<Task> taskList = taskRepository.findAllTasks_ByProjectIsNullAndOwnerId(mockUser.getId());

        assertThat(taskList.size()).isEqualTo(1);
    }

    @Test
    void testUpdateTask_WillReturnSuccessful() {

        String testTitle = "Hello World";

        String testTitle2 = "Hello App";

        mockTask.setTitle(testTitle);

        mockTask = entityManager.persist(mockTask);

        mockTask = taskRepository.getOne(mockTask.getId());

        assertThat(mockTask.getTitle()).isEqualTo(testTitle);

        mockUser = entityManager.persist(mockUser);

        mockUser.addTask(mockTask);

        mockTask = entityManager.persist(mockTask);

        mockTask.setTitle(testTitle2);

        mockTask = entityManager.persist(mockTask);

        mockTask = taskRepository.getOne(mockTask.getId());

        assertThat(mockTask.getTitle()).isEqualTo(testTitle2);
    }

    @Test
    void testDeleteTask() {

        mockUser = entityManager.persist(mockUser);

        mockUser.addTask(mockTask);

        mockTask = entityManager.persist(mockTask);

        mockUser.removeTask(mockTask);

        taskRepository.delete(mockTask);

        List<Task> taskList = taskRepository.findAll();

        assertThat(taskList.size()).isZero();
    }

    @Test
    void testDeleteTask_NotSuccessful() {

        mockUser = entityManager.persist(mockUser);

        mockUser.addTask(mockTask);

        mockTask = entityManager.persist(mockTask);

        taskRepository.delete(mockTask);

        List<Task> taskList = taskRepository.findAll();

        assertThat(taskList.size()).isNotZero();
    }


}
