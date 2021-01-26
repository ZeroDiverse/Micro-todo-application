package fil.adventural.microprojectmanagement.mappers;

import fil.adventural.microprojectmanagement.models.Task;
import fil.adventural.microprojectmanagement.tdo.TaskDto;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    /**
     * Map task dto to task
     * @param taskDto task dto to be mapped
     * @return task after mapped
     */
    public Task mapTaskDtoToTask(TaskDto taskDto) {
        return Task.builder().id(taskDto.getId()).title(taskDto.getTitle()).build();
    }

    /**
     *Map task to task dto
     * @param task task to be mapped
     * @return task dto after mapped
     */
    public TaskDto mapTaskToTaskDto(Task task) {
        return TaskDto.builder().id(task.getId()).title(task.getTitle()).build();
    }
}
