package fil.adventural.microprojectmanagement.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProjectTest {

    private Project project;

    private User user;

    @BeforeEach
    public void init() {
        project = new Project();
        user = User.builder().username("Funny dude").build();
    }

     @Test
     void testProject_WillHaveInitialProjectView_OfList() {
         assertThat(project.getProjectView()).isEqualTo(ProjectView.LIST);
     }

     @Test
     void testProjectAddMember_WillAddMemberToItsMembers() {
         assertThat(project.getMembers().size()).isZero();
         project.addMember(user);
         assertThat(project.getMembers().size()).isEqualTo(1);
     }

     @Test
     void testProjectRemoveMember_WillAddMemberToItsMembers() {
         project.addMember(user);
         assertThat(project.getMembers().size()).isEqualTo(1);
         project.removeMember(user);
         assertThat(project.getMembers().size()).isZero();
     }

    @Test
    void testProjectContainsMember_WillReturnTrue_IfUserInProject() {
        project.addMember(user);
        assertThat(project.containsMember(user)).isTrue();
    }

    @Test
    void testProjectContainsMember_WillReturnFalse_IfUserNotInProject() {
        assertThat(project.containsMember(user)).isFalse();
    }

    @Test
    void testProjectCreate_WillHaveInitialColorOfHex6800fa() {
        assertThat(project.getColor()).isEqualTo("#6800fa");
    }

    @Test
    void testProjectCreate_WillHaveInitialIsFavoriteOfFalse() {
        assertThat(project.isFavourite()).isFalse();
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

}
