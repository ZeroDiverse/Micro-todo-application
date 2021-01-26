package fil.adventural.microprojectmanagement.services;

import fil.adventural.microprojectmanagement.exceptions.UserNotAllowedException;
import fil.adventural.microprojectmanagement.exceptions.UserNotFoundException;
import fil.adventural.microprojectmanagement.mappers.TaskMapper;
import fil.adventural.microprojectmanagement.models.Task;
import fil.adventural.microprojectmanagement.models.User;
import fil.adventural.microprojectmanagement.repositories.TaskRepository;
import fil.adventural.microprojectmanagement.repositories.UserRepository;
import fil.adventural.microprojectmanagement.tdo.TaskDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    private final TaskMapper taskMapper;

    /**
     * Find all personal tasks of user id
     * @param userId user id
     * @return all the task dto of the user
     */
    public List<TaskDto> findAllPersonalTasks(long userId) {
        return taskRepository.findAllTasks_ByProjectIsNullAndOwnerId(userId).stream().map(taskMapper::mapTaskToTaskDto).collect(Collectors.toList());
    }

    /**
     * Add task to user by user id
     * @param userId user id
     * @param taskDto task dto to add
     */
    public Long addTaskToUserByUserId(Long userId, TaskDto taskDto){
        try{
            User user = userRepository.getOne(userId);

            Task task = taskMapper.mapTaskDtoToTask(taskDto);

            user.addTask(task);

            return taskRepository.save(task).getId();
        }
        catch (EntityNotFoundException e){
            throw new UserNotFoundException();
        }

    }

    /**
     * Delete task
     * @param taskId task id of the task to be deleted
     * @param userId user id of the user delete the task
     */
    public void deleteTask(long userId, long taskId) {
        //Get the user
        User user = userRepository.getOne(userId);
        //Get the task
        Task task = taskRepository.getOne(taskId);
        //If task not of user
        if(!user.getPersonalTasks().contains(task)){
            //Then throw user not allowed
            throw new UserNotAllowedException();
        }

        user.removeTask(task);
        //Delete the task
        userRepository.save(user);
    }

    /**
     * Update task by user id
     * @param userId the user id
     * @param taskDto task dto to be updated
     * @return the task after updated
     */
    public TaskDto updateTask(long userId, TaskDto taskDto) {
        //Get the user
        User user = userRepository.getOne(userId);
        //Get the task
        Task task = taskRepository.getOne(taskDto.getId());
        //If task not of user
        if(!user.getPersonalTasks().contains(task)){
            //Then throw user not allowed
            throw new UserNotAllowedException();
        }

        //Delete the task
        return taskMapper.mapTaskToTaskDto(taskRepository.save(taskMapper.mapTaskDtoToTask(taskDto)));
    }
}
