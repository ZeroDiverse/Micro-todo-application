package fil.adventural.microprojectmanagement.services;

import fil.adventural.microprojectmanagement.exceptions.UserNotAllowedException;
import fil.adventural.microprojectmanagement.exceptions.UserNotFoundException;
import fil.adventural.microprojectmanagement.mappers.TaskMapper;
import fil.adventural.microprojectmanagement.models.Task;
import fil.adventural.microprojectmanagement.models.User;
import fil.adventural.microprojectmanagement.repositories.TaskRepository;
import fil.adventural.microprojectmanagement.repositories.UserRepository;
import fil.adventural.microprojectmanagement.tdo.TaskDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@MockitoSettings
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskMapper taskMapper;

    private TaskService taskService;

    private User mockUser;

    private Task mockTask;

    private TaskDto mockTaskDto;


    @BeforeEach
    void setUp() {
        taskService = new TaskService(taskRepository, userRepository, taskMapper);
        mockUser = User.builder().id(1L).build();
        mockTask = Task.builder().id(1L).build();
        mockTaskDto = TaskDto.builder().id(1L).build();
    }

    @Test
    void testFindAllPersonalTasks_WillReturnAllTheTaskWithProjectOfNullAndWithUserId(){
        given(taskRepository.findAllTasks_ByProjectIsNullAndOwnerId(anyLong())).willReturn(Collections.singletonList(mockTask));

        assertThat(taskService.findAllPersonalTasks(anyLong())).isEqualTo(Collections.singletonList(taskMapper.mapTaskToTaskDto(mockTask)));

    }

    @Test
    void addTaskToUserByUserId_WillAddTaskToUserPersonalTask_IfEveryConditionPass(){
        given(userRepository.getOne(anyLong())).willReturn(mockUser);

        given(taskRepository.save(any())).willReturn(mockTask);

        given(taskMapper.mapTaskDtoToTask(any())).willReturn(mockTask);

        taskService.addTaskToUserByUserId(anyLong(), mockTaskDto);

        assertThat(mockUser.getPersonalTasks()).contains(mockTask);
    }

    @Test
    void addTaskToUserByUserId_WillThrowUserNotFoundException_IfUserIsNotFound(){
        given(userRepository.getOne(anyLong())).willThrow(new EntityNotFoundException());

        assertThrows(UserNotFoundException.class, () -> taskService.addTaskToUserByUserId(anyLong(), taskMapper.mapTaskToTaskDto(mockTask)));
    }

    @Test
    void updateTaskToUserByUserId_WillUpdateTask_IfEveryConditionPass(){

        mockUser.addTask(mockTask);

        mockTask.setOwner(mockUser);

        given(userRepository.getOne(anyLong())).willReturn(mockUser);

        given(taskRepository.getOne(anyLong())).willReturn(mockTask);

        given(taskRepository.save(any())).willReturn(mockTask);

        given(taskMapper.mapTaskDtoToTask(any())).willReturn(mockTask);

        given(taskMapper.mapTaskToTaskDto(any())).willReturn(mockTaskDto);

        assertThat(taskService.updateTask(1L, mockTaskDto)).isEqualTo(mockTaskDto);
    }

    @Test
    void updateTaskToUserByUserId_WillThrowUserNotFoundException_IfUserIsNotFound(){

        given(userRepository.getOne(anyLong())).willReturn(mockUser);

        given(taskRepository.getOne(anyLong())).willReturn(mockTask);

        assertThrows(UserNotAllowedException.class, () -> taskService.updateTask(1L, mockTaskDto));
    }

    @Test
    void deleteTaskToUserByUserId_WillThrowUserNotFoundException_IfUserIsNotFound(){

        given(userRepository.getOne(anyLong())).willReturn(mockUser);

        given(taskRepository.getOne(anyLong())).willReturn(mockTask);

        assertThrows(UserNotAllowedException.class, () -> taskService.deleteTask(1L, 1L));
    }

    @Test
    void deleteTaskToUserByUserId_WillRemoveTaskFromUserPersonalTasks(){

        mockUser.addTask(mockTask);

        mockTask.setOwner(mockUser);

        given(userRepository.getOne(anyLong())).willReturn(mockUser);

        given(taskRepository.getOne(anyLong())).willReturn(mockTask);

        taskService.deleteTask(mockUser.getId(), mockTask.getId());

        assertThat(mockUser.getPersonalTasks()).doesNotContain(mockTask);

    }

}
