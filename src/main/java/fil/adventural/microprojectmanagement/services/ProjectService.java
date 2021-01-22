package fil.adventural.microprojectmanagement.services;

import fil.adventural.microprojectmanagement.mappers.ProjectMapper;
import fil.adventural.microprojectmanagement.mappers.UserMapper;
import fil.adventural.microprojectmanagement.models.Project;
import fil.adventural.microprojectmanagement.models.User;
import fil.adventural.microprojectmanagement.repositories.ProjectRepository;
import fil.adventural.microprojectmanagement.repositories.UserRepository;
import fil.adventural.microprojectmanagement.tdo.ProjectDto;
import fil.adventural.microprojectmanagement.tdo.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;
    private final UserMapper userMapper;

    /**
     * Find projects by user email
     *
     * @param email email
     * @return the projects
     */
    public List<ProjectDto> findProjectsByUserEmail(String email) {
        return projectRepository.findProjectsByUserEmail(email).stream().map(projectMapper::mapProjectToProjectDto).collect(Collectors.toList());
    }

    /**
     * Find projects by user id
     *
     * @param userId user id
     * @return the projects
     */
    public List<ProjectDto> findProjectsByUserId(long userId) {
        return projectRepository.findProjectsByUserId(userId).stream().map(projectMapper::mapProjectToProjectDto).collect(Collectors.toList());
    }

    public ProjectDto findProjectById_ByUserId(long projectId, long userId) {
        return projectMapper.mapProjectToProjectDto(projectRepository.findProjectById_ByUserId(projectId, userId));
    }

    /**
     * Create thr project by user id
     *
     * @param project project to be created
     * @return the id of created project
     */
    public Long saveProject_ByUserId(Project project, Long userId) {
        //Get user optional
        Optional<User> userOptional = userRepository.findById(userId);

        if(userOptional.isEmpty()){
            return null;
        }

        //Get user
        User user = userOptional.get();

        user.getProjects().add(project);

        project.getMembers().add(user);

        //Save new project
        project =projectRepository.save(project);

        return project.getId();
    }

    /**
     * Find all members of a project by project id
     * @param projectId project id
     * @return list of members
     */
    public List<UserResponse> findMembers_ByProjectId(long projectId) {
        return projectRepository.getOne(projectId).getMembers().stream().map(userMapper::mapUserToUserResponse).collect(Collectors.toList());
    }

    /**
     * Update existing project
     * @param projectDto project to be updated
     * @return the Project after updating
     */
    public ProjectDto updateProject(ProjectDto projectDto) {
        Project project = projectMapper.mapProjectDtoToProject(projectDto);
        project = projectRepository.save(project);
        return projectMapper.mapProjectToProjectDto(project);
    }

    /**
     * Delete project by id
     * @param projectId id of the project
     * @return true if project exists and deleted, false if not
     */
    public boolean deleteProjectById(long projectId) {
        if(!projectRepository.existsById(projectId)){
            return false;
        }
        projectRepository.deleteById(projectId);
        return true;
    }
}
