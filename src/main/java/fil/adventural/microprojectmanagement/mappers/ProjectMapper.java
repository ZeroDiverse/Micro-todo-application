package fil.adventural.microprojectmanagement.mappers;

import fil.adventural.microprojectmanagement.models.Project;
import fil.adventural.microprojectmanagement.tdo.ProjectDto;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    /**
     * Map project to project dto
     * @param project project
     * @return project dto after mapping
     */
    public ProjectDto mapProjectToProjectDto(Project project) {
        return ProjectDto.builder().id(project.getId()).title(project.getTitle()).isFavourite(project.isFavourite()).projectView(project.getProjectView()).color(project.getColor()).build();
    }

    /**
     * Map project dto to project
     * @param projectDto project dto
     * @return project after mapping
     */
    public Project mapProjectDtoToProject(ProjectDto projectDto) {
        return Project.builder().id(projectDto.getId()).title(projectDto.getTitle()).isFavourite(projectDto.isFavourite()).projectView(projectDto.getProjectView()).color(projectDto.getColor()).build();
    }
}
