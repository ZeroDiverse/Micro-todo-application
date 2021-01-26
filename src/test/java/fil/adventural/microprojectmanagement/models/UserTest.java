package fil.adventural.microprojectmanagement.models;

import fil.adventural.microprojectmanagement.exceptions.UserNotInProjectException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {
    private User user;
    private Project project;
    private Task task;

    @BeforeEach
    public void init() {
        user = new User();
        project = new Project();
        task = new Task();
    }

    @Test
     void testUser_HasZeroProjects_AtBeginning() {
         assertThat(user.getProjects().size()).isZero();
     }

     @Test
     void testUser_HasZeroTasks_AtBeginning() {
         assertThat(user.getPersonalTasks().size()).isZero();
     }

     @Test
     void testUserAddProject() {
         user.addProject(project);
         assertThat(user.getProjects().size()).isEqualTo(1);
     }

     @Test
     void testUserRemoveProjectWillGoOkIfProjectInUsersProjects() {
         user.addProject(project);
         user.removeProject(project);
         assertThat(user.getProjects().size()).isZero();
     }

     @Test
     void testUserRemoveProjectWillFailIfProjectNotInInUsersProjects() {
         assertThrows(UserNotInProjectException.class, () -> user.removeProject(project));
     }

    @Test
    void testUserAddTask_SoPersonalTaskWillHaveOneTask() {
        user.addTask(task);
        assertThat(user.getPersonalTasks().size()).isEqualTo(1);
        assertThat(task.getOwner()).isEqualTo(user);
    }

    @Test
    void testUserRemoveTask_SoPersonalTaskWillHaveOneTask() {
        user.addTask(task);
        user.removeTask(task);
        assertThat(user.getPersonalTasks().size()).isZero();
        assertThat(task.getOwner()).isNull();
    }
 }
