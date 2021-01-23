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
     void testProject_WillHaveZeroInitialTasks() {
         //assertThat(project.getSharedTasks().size()).isZero();
     }

}
