package fil.adventural.microprojectmanagement.tdo;

import fil.adventural.microprojectmanagement.models.ProjectView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
