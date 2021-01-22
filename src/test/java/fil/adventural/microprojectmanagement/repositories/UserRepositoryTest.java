package fil.adventural.microprojectmanagement.repositories;

import fil.adventural.microprojectmanagement.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testFindUserByUsername_WillReturnUserDetails() {
        String testUsername = "Doggy";
        entityManager.persistFlushFind(User.builder().username(testUsername).build());
        List<User> users = userRepository.findUserByUsername(testUsername);
        for (User user : users) {
            assertThat(user.getUsername()).isEqualTo(testUsername);
        }
    }

    @Test
    public void testFindUserByEmail_WillReturnUserDetails() {
        String testEmail = "Doggy@gmail.com";
        entityManager.persistFlushFind(User.builder().email(testEmail).build());
        User user = userRepository.findUserByEmail(testEmail);
        assertThat(user.getEmail()).isEqualTo(testEmail);
    }
}
