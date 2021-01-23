package fil.adventural.microprojectmanagement.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

 class UserTest {

     private User user;

     @BeforeEach
     public void init() {
         user = new User();
     }

     @Test
     void testUser_HasZeroProjects_AtBeginning() {
         assertThat(user.getProjects().size()).isEqualTo(0);
     }

     @Test
     void testUser_HasZeroTasks_AtBeginning() {
         assertThat(user.getPersonalTasks().size()).isEqualTo(0);
     }
}
