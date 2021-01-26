package fil.adventural.microprojectmanagement.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class ProjectTest {

    private Project mockProject;

    private User user;

    private Task mockTask;


    @BeforeEach
    public void init() {
        mockProject = new Project();
        user = User.builder().username("Funny dude").build();
        mockTask = Task.builder().build();
    }

     @Test
     void testProject_WillHaveInitialProjectView_OfList() {
         assertThat(mockProject.getProjectView()).isEqualTo(ProjectView.LIST);
     }

     @Test
     void testProjectAddMember_WillAddMemberToItsMembers() {
         assertThat(mockProject.getMembers().size()).isZero();
         mockProject.addMember(user);
         assertThat(mockProject.getMembers().size()).isEqualTo(1);
     }

     @Test
     void testProjectRemoveMember_WillAddMemberToItsMembers() {
         mockProject.addMember(user);
         assertThat(mockProject.getMembers().size()).isEqualTo(1);
         mockProject.removeMember(user);
         assertThat(mockProject.getMembers().size()).isZero();
     }

    @Test
    void testProjectContainsMember_WillReturnTrue_IfUserInProject() {
        mockProject.addMember(user);
        assertThat(mockProject.containsMember(user)).isTrue();
    }

    @Test
    void testProjectContainsMember_WillReturnFalse_IfUserNotInProject() {
        assertThat(mockProject.containsMember(user)).isFalse();
    }

    @Test
    void testProjectCreate_WillHaveInitialColorOfHex6800fa() {
        assertThat(mockProject.getColor()).isEqualTo("#6800fa");
    }

    @Test
    void testProjectCreate_WillHaveInitialIsFavoriteOfFalse() {
        assertThat(mockProject.isFavourite()).isFalse();
    }

    @Test
    void testProjectBuilder_WillHaveInitialColorOfHex6800fa() {
        Project project = Project.builder().build();
        assertThat(project.getColor()).isEqualTo("#6800fa");
    }

    @Test
    void testProjectBuilder_WillHaveInitialIsFavoriteOfFalse() {
        Project project = Project.builder().build();
        assertThat(project.isFavourite()).isFalse();
    }

    @Test
    void testProjectBuilder_WillHaveInitialProjectView_OfList() {
        Project project = Project.builder().build();
        assertThat(project.getProjectView()).isEqualTo(ProjectView.LIST);
    }

    @Test
    void testProjectBuilder_WillHaveDefaultMembersAndTasksOfEmptyArrayList(){
        Project project = Project.builder().build();
        assertThat(project.getMembers()).isEqualTo(new ArrayList<>());
        assertThat(project.getProjectTasks()).isEqualTo(new ArrayList<>());
    }

    @Test
    void testAddTaskToProject_WillAddTaskToProjectTasks(){
        mockProject.addTask(mockTask);
        assertThat(mockTask.getProject()).isEqualTo(mockProject);
        assertThat(mockProject.getProjectTasks()).contains(mockTask);
    }

}
