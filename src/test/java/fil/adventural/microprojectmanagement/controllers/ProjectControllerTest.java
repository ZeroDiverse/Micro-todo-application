package fil.adventural.microprojectmanagement.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fil.adventural.microprojectmanagement.models.Project;
import fil.adventural.microprojectmanagement.services.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    private List<Project> testProjects;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        testProjects = Arrays.asList(Project.builder().build(), Project.builder().build());
    }

    @Test
    public void testGetProject_WillGetByUserId() throws Exception {
        given(projectService.findProjectsByUserId(anyLong())).willReturn(testProjects);

        MvcResult result = mockMvc
                .perform(get("/api/v1/users/1/projects").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Project[] projects = mapper.readValue(result.getResponse().getContentAsString(), Project[].class);

        List<Project> projectList = Arrays.asList(projects);

        assertThat(projectList).isEqualTo(testProjects);
    }

    @Test
    public void testGetProjectId_WillGetByUserId() throws Exception {
        given(projectService.findProjectById_ByUserId(anyLong(), anyLong())).willReturn(testProjects.get(0));

        MvcResult result = mockMvc
                .perform(get("/api/v1/users/1/projects/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Project project = mapper.readValue(result.getResponse().getContentAsString(), Project.class);

        assertThat(project).isEqualTo(testProjects.get(0));
    }

    @Test
    public void testCreatProject_WillReturnStatusOfOkAndBodyOfCreatedProjectId() throws Exception {
        given(projectService.saveProject_ByUserId(any(), anyLong())).willReturn(1L);

        MvcResult result = mockMvc
                .perform(post("/api/v1/users/1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(testProjects.get(0))))
                .andExpect(status().isCreated())
                .andReturn();

        Long projectId = mapper.readValue(result.getResponse().getContentAsString(), Long.class);

        assertThat(projectId).isEqualTo(1L);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
