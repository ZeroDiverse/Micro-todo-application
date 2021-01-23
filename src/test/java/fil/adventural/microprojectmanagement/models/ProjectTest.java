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
     void testProjectContainsMember_WillReturnTrue_IfUserInProject(){
         project.addMember(user);
         assertThat(project.containsMember(user)).isTrue();
     }

     @Test
     void testProjectContainsMember_WillReturnFalse_IfUserNotInProject(){
         assertThat(project.containsMember(user)).isFalse();
     }
 }
