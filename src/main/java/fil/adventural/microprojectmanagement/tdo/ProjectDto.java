package fil.adventural.microprojectmanagement.tdo;

import fil.adventural.microprojectmanagement.models.ProjectView;
import fil.adventural.microprojectmanagement.models.Task;
import fil.adventural.microprojectmanagement.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProjectDto {
    private Long id;

    private String title;

    private String color;

    private boolean isFavourite;

    private ProjectView projectView;

}
