package fil.adventural.microprojectmanagement.services;

import fil.adventural.microprojectmanagement.exceptions.UserNotFoundException;
import fil.adventural.microprojectmanagement.mappers.UserMapper;
import fil.adventural.microprojectmanagement.models.User;
import fil.adventural.microprojectmanagement.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@MockitoSettings
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, userMapper);
    }

    @Test
    void testFindUserByUsername_WillReturnUserDetails() {

        List<User> users = Arrays.asList(User.builder().build(), User.builder().build(), User.builder().build());

        given(userRepository.findUserByUsername(anyString())).willReturn(users);

        assertThat(userService.findUserByUsername(anyString())).isEqualTo(users);
    }

    @Test
    void testFindUserByEmail_WillReturnUserDetails() {

        User user = User.builder().build();

        given(userRepository.findUserByEmail(anyString())).willReturn(user);

        assertThat(userService.findUserByEmail(anyString())).isEqualTo(user);
    }

    @Test
    void testFindUserByEmail_WillThrowUserNotFoundException() {

        given(userRepository.findUserByEmail(anyString())).willReturn(null);

        org.junit.jupiter.api.Assertions.assertThrows(UserNotFoundException.class, () -> userService.findUserByEmail(anyString()));
    }
}
