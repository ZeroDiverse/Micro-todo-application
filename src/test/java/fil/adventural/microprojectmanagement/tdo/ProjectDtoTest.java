package fil.adventural.microprojectmanagement.tdo;

import fil.adventural.microprojectmanagement.models.Project;
import fil.adventural.microprojectmanagement.models.ProjectView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class ProjectDtoTest {

    private Project project;
    private ProjectDto projectDto;

    @BeforeEach
    void setUp() {
        project = Project.builder().id(1L).title("Test").projectView(ProjectView.LIST).isFavourite(true).color("white").members(new ArrayList<>()).build();
        projectDto = ProjectDto.builder().id(1L).title("Test").projectView(ProjectView.LIST).isFavourite(true).color("white").build();
    }

    @Test
    void testProjectDto_WillHaveTheSameDetailAsProject() {
        assertThat(projectDto.getId()).isEqualTo(project.getId());
        assertThat(projectDto.getTitle()).isEqualTo(project.getTitle());
        assertThat(projectDto.getProjectView()).isEqualTo(project.getProjectView());
        assertThat(projectDto.isFavourite()).isEqualTo(project.isFavourite());
        assertThat(projectDto.getColor()).isEqualTo(project.getColor());
    }

}
