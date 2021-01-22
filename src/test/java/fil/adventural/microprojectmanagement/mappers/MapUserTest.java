package fil.adventural.microprojectmanagement.mappers;

import fil.adventural.microprojectmanagement.models.User;
import fil.adventural.microprojectmanagement.tdo.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MapUserTest {
    private UserMapper userMapper;
    private User user;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
        user = User.builder().id(1L).email("Test").username("Test").build();
        userResponse = UserResponse.builder().id(1L).email("Test").username("Test").build();
    }

    @Test
    void testMapUserToUserResponse_WillReturnUserResponse() {
        assertThat(userMapper.mapUserToUserResponse(user)).isEqualTo(userResponse);
    }

    @Test
    void testMapUserResponseToUser_WillReturnUser() {
        assertThat(userMapper.mapUserResponseToUser(userResponse)).isEqualTo(user);
    }

}
