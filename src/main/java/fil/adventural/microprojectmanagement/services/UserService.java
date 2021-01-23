package fil.adventural.microprojectmanagement.services;

import fil.adventural.microprojectmanagement.exceptions.UserNotFoundException;
import fil.adventural.microprojectmanagement.mappers.UserMapper;
import fil.adventural.microprojectmanagement.models.User;
import fil.adventural.microprojectmanagement.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public List<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public User findUserByEmail(String username) {
        User user = userRepository.findUserByEmail(username);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }
}
