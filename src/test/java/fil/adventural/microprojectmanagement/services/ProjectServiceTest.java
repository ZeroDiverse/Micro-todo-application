package fil.adventural.microprojectmanagement.services;

import fil.adventural.microprojectmanagement.mappers.ProjectMapper;
import fil.adventural.microprojectmanagement.mappers.UserMapper;
import fil.adventural.microprojectmanagement.models.Project;
import fil.adventural.microprojectmanagement.models.User;
import fil.adventural.microprojectmanagement.repositories.ProjectRepository;
import fil.adventural.microprojectmanagement.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
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

    @BeforeEach
    void setUp() {
        projectService = new ProjectService(projectRepository, userRepository, projectMapper, userMapper);
    }

    @Test
     void testProjectService_FindProjectsByUserEmail_WillReturnProjects(){
        Project project = Project.builder().build();

        given(projectRepository.findProjectsByUserEmail(anyString())).willReturn(Collections.singletonList(project));

        assertThat(projectService.findProjectsByUserEmail("test@gmail.com")).isEqualTo(Collections.singletonList(projectMapper.mapProjectToProjectDto(project)));
    }


    @Test
    void testProjectService_FindProjectsByUserId_WillReturnProjects(){
        Project project = Project.builder().build();

        given(projectRepository.findProjectsByUserId(anyLong())).willReturn(Collections.singletonList(project));

        assertThat(projectService.findProjectsByUserId(anyLong())).isEqualTo(Collections.singletonList(projectMapper.mapProjectToProjectDto(project)));
    }

    @Test
    void testProjectService_FindProjectById_ByUserId_WillReturnProjectDetail(){
        Project project = Project.builder().build();

        given(projectRepository.findProjectById_ByUserId(anyLong(), anyLong())).willReturn(project);

        assertThat(projectService.findProjectById_ByUserId(anyLong(), anyLong())).isEqualTo(projectMapper.mapProjectToProjectDto(project));
    }

    @Test
    void testProjectService_CreateProject_ByUserId_WillReturnCreatedProjectId(){
        Project project = Project.builder().members(new ArrayList<>()).build();

        Optional<User> mockUserOptional = Optional.of(User.builder().projects(new ArrayList<>()).build());

        given(userRepository.findById(anyLong())).willReturn(mockUserOptional);

        given(projectRepository.save(project)).willReturn(project);

        project.setId(project.getId());

        assertThat(projectService.saveProject_ByUserId(project, anyLong())).isEqualTo(project.getId());
    }

    @Test
    void testUpdateProjectByProjectId_WillReturnTheUpdatedProject(){
        Project project = Project.builder().title("Hello bank").build();

        //TODO Write test for update project

        //given(projectRepository.save(project)).willReturn(project);

        //assertThat(projectService.updateProject(projectMapper.mapProjectToProjectDto(project))).isEqualTo(projectMapper.mapProjectToProjectDto(project));

    }

    @Test
    void testDeleteProjectByProjectId_WillReturnTrue_IfProjectExists(){
        given(projectRepository.existsById(anyLong())).willReturn(true);

        doNothing().when(projectRepository).deleteById(anyLong());

        assertThat(projectService.deleteProjectById(anyLong())).isTrue();

    }

    @Test
    void testDeleteProjectByProjectId_WillReturnFalse_IfProjectNotExists(){
        given(projectRepository.existsById(anyLong())).willReturn(false);

        assertThat(projectService.deleteProjectById(anyLong())).isFalse();

    }

    @Test
    void testGetMembersByProjectId_WillReturnUserResponse() {
        User user = User.builder().build();

        Project project = Project.builder().members(Collections.singletonList(user)).build();

        given(projectRepository.getOne(anyLong())).willReturn(project);

        assertThat(projectService.findMembers_ByProjectId(anyLong())).isEqualTo(Stream.of(user).map(userMapper::mapUserToUserResponse).collect(toList()));

    }
}
