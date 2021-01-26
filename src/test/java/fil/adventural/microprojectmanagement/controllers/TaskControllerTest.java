package fil.adventural.microprojectmanagement.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fil.adventural.microprojectmanagement.exceptions.UserNotAllowedException;
import fil.adventural.microprojectmanagement.services.TaskService;
import fil.adventural.microprojectmanagement.tdo.TaskDto;
import fil.adventural.microprojectmanagement.utils.JsonUtils;
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

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @MockBean
    private TaskService taskService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper;

    private List<TaskDto> mockTaskDtoList;

    @BeforeEach
    void setUp() {
        mockTaskDtoList = Arrays.asList(TaskDto.builder().build(), TaskDto.builder().build(), TaskDto.builder().build());
        mapper = new ObjectMapper();
    }

    @Test
    void testFindAllPersonalTasksByUserId_WillReturnAllTasksDetail() throws Exception {
        given(taskService.findAllPersonalTasks(anyLong())).willReturn(mockTaskDtoList);

        //Test status of get request
        MvcResult result = mockMvc.perform(get("/api/v1/users/1/personalTasks"))
                .andExpect(status().isOk())
                .andReturn();

        //Transform get response to object
        TaskDto[] resultTasksDto = mapper.readValue(result.getResponse().getContentAsString(), TaskDto[].class);

        //Test result of get request
        assertThat(Arrays.asList(resultTasksDto)).isEqualTo(mockTaskDtoList);
    }

    @Test
    void testAddPersonalTasksByUserId_WillReturnIdOfTaskCreated() throws Exception {
        given(taskService.addTaskToUserByUserId(anyLong(), any())).willReturn(1L);

        //Test status of post request
        MvcResult result = mockMvc.perform(post("/api/v1/users/1/personalTasks")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.asJsonString(mockTaskDtoList.get(0))))
                .andExpect(status().isCreated())
                .andReturn();

        Long taskCreatedId = mapper.readValue(result.getResponse().getContentAsString(), Long.class);

        assertThat(taskCreatedId).isEqualTo(1L);
    }

    @Test
    void testUpdatePersonalTasksByUserId_WillReturnTheUpdatedTask_StatusOfOk() throws Exception {
        given(taskService.updateTask(anyLong(), any())).willReturn(mockTaskDtoList.get(0));

        //Test status of post request
        MvcResult result = mockMvc.perform(patch("/api/v1/users/1/personalTasks")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.asJsonString(mockTaskDtoList.get(0))))
                .andExpect(status().isOk())
                .andReturn();

        TaskDto updatedTask = mapper.readValue(result.getResponse().getContentAsString(), TaskDto.class);

        assertThat(updatedTask).isEqualTo(mockTaskDtoList.get(0));
    }

    @Test
    void testDeletePersonalTasksByUserId_WillReturnAStatusOfNoContent() throws Exception {
        doNothing().when(taskService).deleteTask(anyLong(), anyLong());

        //Test status of delete request
        mockMvc.perform(delete("/api/v1/users/1/personalTasks/1"))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void testDeletePersonalTasksByUserId_WillReturnAStatusOfNotAllowed() throws Exception {
        doThrow(new UserNotAllowedException()).when(taskService).deleteTask(anyLong(), anyLong());

        //Test status of delete request
        mockMvc.perform(delete("/api/v1/users/1/personalTasks/1"))
                .andExpect(status().isForbidden())
                .andReturn();
    }
}
