package fil.adventural.microprojectmanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class UserNotInProjectException extends RuntimeException{
    public UserNotInProjectException() {
        super("User not in the project");
    }
}
