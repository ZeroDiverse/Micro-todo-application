package fil.adventural.microprojectmanagement.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fil.adventural.microprojectmanagement.exceptions.UserAlreadyInProjectException;
import fil.adventural.microprojectmanagement.exceptions.UserNotAllowedException;
import fil.adventural.microprojectmanagement.services.ProjectService;
import fil.adventural.microprojectmanagement.tdo.ProjectDto;
import fil.adventural.microprojectmanagement.tdo.UserRequest;
import fil.adventural.microprojectmanagement.tdo.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static fil.adventural.microprojectmanagement.utils.JsonUtils.asJsonString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    private List<ProjectDto> testProjects;

    private List<UserResponse> testMembers;


    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        testProjects = Arrays.asList(ProjectDto.builder().build(), ProjectDto.builder().build());
        testMembers = Arrays.asList(UserResponse.builder().build(), UserResponse.builder().build());
    }

    @Test
    void testGetProject_WillGetByUserId() throws Exception {


        given(projectService.findProjectsByUserId(anyLong())).willReturn(testProjects);

        MvcResult result = mockMvc
                .perform(get("/api/v1/users/1/projects").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ProjectDto[] projects = mapper.readValue(result.getResponse().getContentAsString(), ProjectDto[].class);

        List<ProjectDto> projectList = Arrays.asList(projects);

        assertThat(projectList).isEqualTo(testProjects);
    }

    @Test
    void testGetProjectId_WillGetByUserId() throws Exception {
        given(projectService.findProjectById_ByUserId(anyLong(), anyLong())).willReturn(testProjects.get(0));

        MvcResult result = mockMvc
                .perform(get("/api/v1/users/1/projects/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ProjectDto project = mapper.readValue(result.getResponse().getContentAsString(), ProjectDto.class);

        assertThat(project).isEqualTo(testProjects.get(0));
    }

    @Test
    void testCreateProject_WillReturnStatusOfOkAndBodyOfCreatedProjectId() throws Exception {
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

    @Test
    void testGetMembersByProjectId_WillGetMembers() throws Exception {
        given(projectService.findMembers_ByProjectId(anyLong())).willReturn(testMembers);

        MvcResult result = mockMvc
                .perform(get("/api/v1/projects/1/members").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        UserResponse[] userResponses = mapper.readValue(result.getResponse().getContentAsString(), UserResponse[].class);

        assertThat(Arrays.asList(userResponses)).isEqualTo(testMembers);
    }

    @Test
    void testUpdateProject_WillGetAnUpdatedProjectInResponse() throws Exception {
        given(projectService.updateProject(anyLong(), any())).willReturn(testProjects.get(0));

        MvcResult result = mockMvc
                .perform(patch("/api/v1/users/1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(testProjects.get(0))))
                .andExpect(status().isOk())
                .andReturn();

        ProjectDto projectDto = mapper.readValue(result.getResponse().getContentAsString(), ProjectDto.class);

        assertThat(projectDto).isEqualTo(testProjects.get(0));
    }

    @Test
    void testAddMemberToProject_WillReturnStatusOk_IfMemberWasAdded() throws Exception {
        doNothing().when(projectService).addMemberToProject(anyLong(), anyLong(), anyLong());

        mockMvc
                .perform(post("/api/v1/users/1/addMember/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(UserRequest.builder().id(2L).build())))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void testAddMemberToProject_WillReturnStatusNotAllow_IfUserIsNotAllowed() throws Exception {
        doThrow(new UserNotAllowedException()).when(projectService).addMemberToProject(anyLong(), anyLong(), anyLong());

        mockMvc
                .perform(post("/api/v1/users/1/addMember/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(UserRequest.builder().id(2L).build())))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()))
                .andReturn();
    }

    @Test
    void testAddMemberToProject_WillReturnStatusNotAcceptable_IfUserIsAlreadyInProject() throws Exception {
        doThrow(new UserAlreadyInProjectException()).when(projectService).addMemberToProject(anyLong(), anyLong(), anyLong());

        mockMvc
                .perform(post("/api/v1/users/1/addMember/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(UserRequest.builder().id(2L).build())))
                .andExpect(status().is(HttpStatus.NOT_ACCEPTABLE.value()))
                .andReturn();
    }


    @Test
    void testRemoveMemberToProject_WillReturnStatusOk_IfMemberWasAdded() throws Exception {
        doNothing().when(projectService).removeMemberFromProject(anyLong(), anyLong(), anyLong());

        mockMvc
                .perform(post("/api/v1/users/1/removeMember/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(UserRequest.builder().id(2L).build())))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void testRemoveMemberToProject_WillReturnStatusNotAllow_IfUserIsNotAllowed() throws Exception {
        doThrow(new UserNotAllowedException()).when(projectService).removeMemberFromProject(anyLong(), anyLong(), anyLong());

        mockMvc
                .perform(post("/api/v1/users/1/removeMember/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(UserRequest.builder().id(2L).build())))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()))
                .andReturn();
    }

    @Test
    void testRemoveToProject_WillReturnStatusNotAcceptable_IfUserIsAlreadyInProject() throws Exception {
        doThrow(new UserAlreadyInProjectException()).when(projectService).removeMemberFromProject(anyLong(), anyLong(), anyLong());

        mockMvc
                .perform(post("/api/v1/users/1/removeMember/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(UserRequest.builder().id(2L).build())))
                .andExpect(status().is(HttpStatus.NOT_ACCEPTABLE.value()))
                .andReturn();
    }

    @Test
    void testDeleteProjectById_WillReturnAStatusOfNoContent() throws Exception {
        given(projectService.deleteProjectById(anyLong())).willReturn(true);

        mockMvc
                .perform(delete("/api/v1/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()))
                .andReturn();
    }

    @Test
    void testDeleteProjectById_AndProjectNotExists_WillReturnAStatusOfNotFound() throws Exception {
        given(projectService.deleteProjectById(anyLong())).willReturn(false);

        mockMvc
                .perform(delete("/api/v1/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()))
                .andReturn();
    }

    @Test
    void testDeleteProjectById_WillReturnAStatusOfNotFoundIfFail() throws Exception {
        given(projectService.deleteProjectById(anyLong())).willReturn(false);

        mockMvc
                .perform(delete("/api/v1/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()))
                .andReturn();
    }
}
