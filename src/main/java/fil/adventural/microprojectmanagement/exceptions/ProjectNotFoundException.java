package fil.adventural.microprojectmanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ProjectNotFoundException extends RuntimeException{
    public ProjectNotFoundException() {
        super("Post not found exception!");
    }
}
