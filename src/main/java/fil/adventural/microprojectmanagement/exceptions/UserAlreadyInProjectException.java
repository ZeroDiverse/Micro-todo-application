package fil.adventural.microprojectmanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class UserAlreadyInProjectException extends RuntimeException{
    public UserAlreadyInProjectException() {
        super("User already in the project");
    }
}
