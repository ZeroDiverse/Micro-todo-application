package fil.adventural.microprojectmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProjectView {
    LIST("list"), BOARD("board");

    private final String projectViewName;
}
