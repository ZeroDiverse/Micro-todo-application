package fil.adventural.microprojectmanagement.services;

import fil.adventural.microprojectmanagement.exceptions.ProjectNotFoundException;
import fil.adventural.microprojectmanagement.exceptions.UserAlreadyInProjectException;
import fil.adventural.microprojectmanagement.exceptions.UserNotAllowedException;
import fil.adventural.microprojectmanagement.exceptions.UserNotFoundException;
import fil.adventural.microprojectmanagement.mappers.ProjectMapper;
import fil.adventural.microprojectmanagement.mappers.UserMapper;
import fil.adventural.microprojectmanagement.models.Project;
import fil.adventural.microprojectmanagement.models.User;
import fil.adventural.microprojectmanagement.repositories.ProjectRepository;
import fil.adventural.microprojectmanagement.repositories.UserRepository;
import fil.adventural.microprojectmanagement.tdo.ProjectDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
@MockitoSettings
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    private ProjectService projectService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectMapper projectMapper;


    @Mock
    private UserMapper userMapper;

    private Project project;

    private ProjectDto projectDto;

    private User user;

    private User user1;

    @BeforeEach
    void setUp() {
        projectService = new ProjectService(projectRepository, userRepository, projectMapper, userMapper);
        user = User.builder().id(1L).projects(new ArrayList<>()).build();
        user1 = User.builder().id(2L).projects(new ArrayList<>()).build();
        project = Project.builder().id(1L).title("Hello bank").members(Collections.singletonList(user)).build();
        projectDto = ProjectDto.builder().id(1L).title("Hello bank").build();
    }

    @Test
     void testProjectService_FindProjectsByUserEmail_WillReturnProjects(){
        given(projectRepository.findProjectsByUserEmail(anyString())).willReturn(Collections.singletonList(project));

        assertThat(projectService.findProjectsByUserEmail("test@gmail.com")).isEqualTo(Collections.singletonList(projectMapper.mapProjectToProjectDto(project)));
    }


    @Test
    void testProjectService_FindProjectsByUserId_WillReturnProjects(){
        given(projectRepository.findProjectsByUserId(anyLong())).willReturn(Collections.singletonList(project));

        assertThat(projectService.findProjectsByUserId(anyLong())).isEqualTo(Collections.singletonList(projectMapper.mapProjectToProjectDto(project)));
    }

    @Test
    void testProjectService_FindProjectById_ByUserId_WillReturnProjectDetail() {
        given(projectRepository.findProjectById_ByUserId(anyLong(), anyLong())).willReturn(project);

        assertThat(projectService.findProjectById_ByUserId(anyLong(), anyLong())).isEqualTo(projectMapper.mapProjectToProjectDto(project));
    }

    @Test
    void testProjectService_FindProjectById_ByUserId_WillThrowProjectNotFoundExceptionIfProjectNotExists() {
        given(projectRepository.findProjectById_ByUserId(anyLong(), anyLong())).willThrow(new EntityNotFoundException());

        assertThrows(ProjectNotFoundException.class, () -> projectService.findProjectById_ByUserId(anyLong(), anyLong()));

    }

    @Test
    void testProjectService_CreateProject_ByUserId_WillReturnCreatedProjectId() {

        user.setProjects(new ArrayList<>());

        project.setMembers(new ArrayList<>());

        Optional<User> mockUserOptional = Optional.of(user);

        given(userRepository.findById(user.getId())).willReturn(mockUserOptional);

        given(projectRepository.save(project)).willReturn(project);

        assertThat(projectService.saveProject_ByUserId(project, user.getId())).isEqualTo(project.getId());
    }

    @Test
    void testUpdateProjectByProjectId_WillReturnTheUpdatedProject() {

        Optional<User> userOptional = Optional.of(user);

        given(projectMapper.mapProjectDtoToProject(projectDto)).willReturn(project);

        given(userRepository.findById(user.getId())).willReturn(userOptional);

        given(projectRepository.getOne(projectDto.getId())).willReturn(project);

        given(projectMapper.mapProjectToProjectDto(project)).willReturn(projectDto);

        given(projectRepository.save(project)).willReturn(project);

        assertThat(projectService.updateProject(user.getId(), projectDto)).isEqualTo(projectDto);
    }


    @Test
    void testUpdateProjectByProjectId_WillThrowUserRestrictionException_IfUserNotInProject() {

        ProjectDto projectDto = ProjectDto.builder().id(1L).title("Hello bank").build();

        given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> projectService.updateProject(anyLong(), projectDto));
    }

    @Test
    void testUpdateProjectByProjectId_WillThrowProjectNotFoundException_IfProjectNotExist() {
        //TODO
    }


    @Test
    void testAddMemberForProject_WillNotHaveError_IfEverythingOk() {

        project.setMembers(new ArrayList<>());

        user.setProjects(new ArrayList<>());

        project.addMember(user);

        user.addProject(project);

        given(projectRepository.getOne(anyLong())).willReturn(project);

        //Get 2 user in 2 call from get one
        given(userRepository.getOne(anyLong())).willReturn(user, user1);

        projectService.addMemberToProject(project.getId(), user.getId(), user1.getId());

        assertThat(project.getMembers()).contains(user1);

        assertThat(user1.getProjects()).contains(project);

    }

    @Test
    void testAddMemberForProject_WillThrowUserAlreadyInException_IfMemberIsAlreadyInIt() {
        //Init
        project.setMembers(new ArrayList<>());

        user.setProjects(new ArrayList<>());

        project.addMember(user);

        project.addMember(user1);

        user.addProject(project);

        user1.addProject(project);

        //Given
        given(projectRepository.getOne(anyLong())).willReturn(project);

        given(userRepository.getOne(anyLong())).willReturn(user);

        given(userRepository.getOne(anyLong())).willReturn(user1);

        //Assert
        assertThrows(UserAlreadyInProjectException.class, () -> projectService.addMemberToProject(project.getId(), user.getId(), user1.getId()));

    }

    @Test
    void testAddMemberForProject_WillThrowUserNotAllowedException_IfUserIsNotInProject() {

        project.setMembers(new ArrayList<>());

        user.setProjects(new ArrayList<>());

        given(projectRepository.getOne(anyLong())).willReturn(project);

        given(userRepository.getOne(anyLong())).willReturn(user);

        assertThrows(UserNotAllowedException.class, () -> projectService.addMemberToProject(project.getId(), user.getId(), user1.getId()));

    }

    @Test
    void testRemoveMemberForProject_WillNotHaveError_IfEverythingOk() {

        project.setMembers(new ArrayList<>());

        user.setProjects(new ArrayList<>());

        project.addMember(user);

        project.addMember(user1);

        user.addProject(project);

        given(projectRepository.getOne(anyLong())).willReturn(project);

        //Get 2 user in 2 call from get one
        given(userRepository.getOne(anyLong())).willReturn(user, user1);

        projectService.removeMemberFromProject(project.getId(), user.getId(), user1.getId());

        assertThat(project.getMembers()).doesNotContain(user1);

        assertThat(user1.getProjects()).doesNotContain(project);

    }

    @Test
    void testDeleteProjectByProjectId_WillReturnTrue_IfProjectExists() {
        given(projectRepository.existsById(anyLong())).willReturn(true);

        doNothing().when(projectRepository).deleteById(anyLong());

        assertThat(projectService.deleteProjectById(anyLong())).isTrue();

    }

    @Test
    void testDeleteProjectByProjectId_WillReturnFalse_IfProjectNotExists(){
        given(projectRepository.existsById(anyLong())).willReturn(false);

        assertThrows(ProjectNotFoundException.class, () -> projectService.deleteProjectById(anyLong()));
    }

    @Test
    void testGetMembersByProjectId_WillReturnUserResponse() {
        given(projectRepository.getOne(anyLong())).willReturn(project);

        assertThat(projectService.findMembers_ByProjectId(anyLong())).isEqualTo(Stream.of(user).map(userMapper::mapUserToUserResponse).collect(toList()));
    }
}
