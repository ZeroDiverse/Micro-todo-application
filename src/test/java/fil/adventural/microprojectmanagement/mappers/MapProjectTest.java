package fil.adventural.microprojectmanagement.mappers;

import fil.adventural.microprojectmanagement.models.Project;
import fil.adventural.microprojectmanagement.models.ProjectView;
import fil.adventural.microprojectmanagement.tdo.ProjectDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MapProjectTest {

    private ProjectMapper projectMapper;
    private Project project;
    private ProjectDto projectDto;

    @BeforeEach
    void setUp() {
        projectMapper = new ProjectMapper();
        project = Project.builder().id(1L).title("Test").projectView(ProjectView.LIST).isFavourite(true).color("white").build();
        projectDto = ProjectDto.builder().id(1L).title("Test").projectView(ProjectView.LIST).isFavourite(true).color("white").build();
    }

    @Test
    void testMapProjectToProjectDto_WillReturnProjectDto(){
        assertThat(projectMapper.mapProjectToProjectDto(project)).isEqualTo(projectDto);
    }

    @Test
    void testMapProjectDtoToProject_WillReturnProject(){
        assertThat(projectMapper.mapProjectDtoToProject(projectDto)).isEqualTo(project);
    }

}
