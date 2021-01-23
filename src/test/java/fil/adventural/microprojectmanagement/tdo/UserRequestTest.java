package fil.adventural.microprojectmanagement.tdo;

import fil.adventural.microprojectmanagement.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserRequestTest {
    private User user;
    private UserRequest userRequest;

    @BeforeEach
    void setUp() {
        user = User.builder().id(1L).build();
    }

    @Test
    void testUserRequest_WillHaveSameIdWithUser(){
        userRequest = UserRequest.builder().id(1L).build();
        assertThat(userRequest.getId()).isEqualTo(user.getId());
    }
}

