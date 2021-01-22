package fil.adventural.microprojectmanagement.mappers;

import fil.adventural.microprojectmanagement.models.User;
import fil.adventural.microprojectmanagement.tdo.UserResponse;
import org.springframework.stereotype.Component;

/**
 * This class handle user mapper to user response and request
 */
@Component
public class UserMapper {

    public UserResponse mapUserToUserResponse(User user) {
        return UserResponse.builder().id(user.getId()).username(user.getUsername()).email(user.getEmail()).build();
    }

    public User mapUserResponseToUser(UserResponse userResponse) {
        return User.builder().id(userResponse.getId()).username(userResponse.getUsername()).email(userResponse.getEmail()).build();
    }
}
