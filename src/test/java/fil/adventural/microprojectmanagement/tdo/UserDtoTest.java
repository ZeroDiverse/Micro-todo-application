package fil.adventural.microprojectmanagement.tdo;

import fil.adventural.microprojectmanagement.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserDtoTest {
    private User user;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        user = User.builder().id(1L).email("Test").username("Test").build();
    }

    @Test
    void testUserResponse_WillHaveTheSameIdEmailAndUsernameAsUser() {
        userResponse = UserResponse.builder().id(1L).email("Test").username("Test").build();
        assertThat(userResponse.getId()).isEqualTo(user.getId());
        assertThat(userResponse.getEmail()).isEqualTo(user.getEmail());
        assertThat(userResponse.getUsername()).isEqualTo(user.getUsername());
    }
}
