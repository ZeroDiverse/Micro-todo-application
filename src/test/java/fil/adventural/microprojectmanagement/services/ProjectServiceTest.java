package fil.adventural.microprojectmanagement.services;

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
import org.mockito.quality.Strictness;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@MockitoSettings
public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    private ProjectService projectService;

    @Mock
    private UserRepository userRepository;


    @BeforeEach
    void setUp() {
        projectService = new ProjectService(projectRepository, userRepository);
    }

    @Test
    public void testProjectService_FindProjectsByUserEmail_WillReturnProjects(){
        Project project = Project.builder().build();

        given(projectRepository.findProjectsByUserEmail(anyString())).willReturn(Collections.singletonList(project));

        assertThat(projectService.findProjectsByUserEmail("test@gmail.com")).isEqualTo(Collections.singletonList(project));
    }


    @Test
    public void testProjectService_FindProjectsByUserId_WillReturnProjects(){
        Project project = Project.builder().build();

        given(projectRepository.findProjectsByUserId(anyLong())).willReturn(Collections.singletonList(project));

        assertThat(projectService.findProjectsByUserId(anyLong())).isEqualTo(Collections.singletonList(project));
    }

    @Test
    public void testProjectService_FindProjectById_ByUserId_WillReturnProjectDetail(){
        Project project = Project.builder().build();

        given(projectRepository.findProjectById_ByUserId(anyLong(), anyLong())).willReturn(project);

        assertThat(projectService.findProjectById_ByUserId(anyLong(), anyLong())).isEqualTo(project);
    }

    @Test
    public void testProjectService_CreateProject_ByUserId_WillReturnCreatedProjectId(){
        Project project = Project.builder().build();

        Optional<User> mockUserOptional = Optional.of(User.builder().build());

        given(userRepository.findById(anyLong())).willReturn(mockUserOptional);

        given(projectRepository.save(project)).willReturn(project);

        project.setId(project.getId());

        assertThat(projectService.saveProject_ByUserId(project, anyLong())).isEqualTo(project.getId());
    }
}
