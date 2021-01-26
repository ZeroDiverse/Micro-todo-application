package fil.adventural.microprojectmanagement.mappers;

import fil.adventural.microprojectmanagement.models.Task;
import fil.adventural.microprojectmanagement.tdo.TaskDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Map task test
 */
class MapTaskTest {
    private Task mockTask;

    private TaskDto mockTaskDto;

    private TaskMapper taskMapper;

    @BeforeEach
    void setUp() {
        mockTask = Task.builder().build();

        mockTaskDto = TaskDto.builder().build();

        taskMapper = new TaskMapper();
    }


    @Test
    void testTaskMapperWillMapTaskDtoToTask() {
        Task task = taskMapper.mapTaskDtoToTask(mockTaskDto);
        assertThat(task).isEqualTo(mockTask);
    }

    @Test
    void testTaskMapperWillMapTaskToTaskDto() {
        TaskDto taskDto = taskMapper.mapTaskToTaskDto(mockTask);
        assertThat(taskDto).isEqualTo(mockTaskDto);
    }
}
