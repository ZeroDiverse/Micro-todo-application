package fil.adventural.microprojectmanagement.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super("User not found!!!");
    }
}
