package fil.adventural.microprojectmanagement.services;

import fil.adventural.microprojectmanagement.exceptions.*;
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

import javax.persistence.EntityNotFoundException;
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
        try {
            return projectMapper.mapProjectToProjectDto(projectRepository.findProjectById_ByUserId(projectId, userId));
        } catch (EntityNotFoundException e) {
            throw new ProjectNotFoundException();
        }
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
     *
     * @param projectDto project to be updated
     * @return the Project after updating
     */
    public ProjectDto updateProject(Long userId, ProjectDto projectDto) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException();
        }

        Project oldProject = projectRepository.getOne(projectDto.getId());

        if (!oldProject.getMembers().contains(userOptional.get())) {
            throw new UserNotAllowedException();
        }

        Project project = projectMapper.mapProjectDtoToProject(projectDto);

        project = projectRepository.save(project);

        return projectMapper.mapProjectToProjectDto(project);
    }

    /**
     * Add member to project
     * @param projectId project id
     * @param userId user id
     * @param userToAddId the id of member to be added
     */
    public void addMemberToProject(Long projectId, Long userId, Long userToAddId){
        //Get the project
        Project projectFound = projectRepository.getOne(projectId);

        //Get the member
        User user = userRepository.getOne(userId);

        //check if user in the project

        if(!projectFound.containsMember(user)){
            throw new UserNotAllowedException();
        }

        User member = userRepository.getOne(userToAddId);

        if(projectFound.containsMember(member)){
            throw new UserAlreadyInProjectException();
        }

        //Add the member to the project
        projectFound.addMember(member);

        member.addProject(projectFound);

        //Save the project
        projectRepository.save(projectFound);
    }

    /**
     * Delete project by id
     * @param projectId id of the project
     * @return true if project exists and deleted, false if not
     */
    public boolean deleteProjectById(long projectId) {
        if(!projectRepository.existsById(projectId)){
            throw new ProjectNotFoundException();
        }
        projectRepository.deleteById(projectId);
        return true;
    }

    /**
     * Remove member from project
     * @param projectId project id
     * @param userId user in the project
     * @param userRemovedId user to be removed id
     */
    public void removeMemberFromProject(Long projectId, Long userId, Long userRemovedId) {
        //Get the project
        Project projectFound = projectRepository.getOne(projectId);

        //Get the member
        User user = userRepository.getOne(userId);

        //check if user in the project

        if(!projectFound.containsMember(user)){
            throw new UserNotAllowedException();
        }

        User member = userRepository.getOne(userRemovedId);

        if(!projectFound.containsMember(member)){
            throw new UserNotInProjectException();
        }

        //Add the member to the project
        projectFound.removeMember(member);

        member.removeProject(projectFound);

        //Save the project
        projectRepository.save(projectFound);
    }
}
