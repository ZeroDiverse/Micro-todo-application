package fil.adventural.microprojectmanagement.controllers;

import fil.adventural.microprojectmanagement.services.TaskService;
import fil.adventural.microprojectmanagement.tdo.TaskDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Log4j2
public class TaskController {
    private final TaskService taskService;

    @GetMapping(value = {"/users/{userId}/personalTasks", "/users/{userId}/personalTasks/"})
    public ResponseEntity<List<TaskDto>> getProjectsByUserId(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.findAllPersonalTasks(userId));
    }

    @PostMapping(value = {"/users/{userId}/personalTasks", "/users/{userId}/personalTasks/"})
    public ResponseEntity<Long> createNewPersonalTask(@PathVariable Long userId, @RequestBody TaskDto taskDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.addTaskToUserByUserId(userId, taskDto));
    }

    @PatchMapping(value = {"/users/{userId}/personalTasks", "/users/{userId}/personalTasks/"})
    public ResponseEntity<TaskDto> updatePersonalTask(@PathVariable Long userId, @RequestBody TaskDto taskDto) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.updateTask(userId, taskDto));
    }

    @DeleteMapping(value = {"/users/{userId}/personalTasks/{taskId}", "/users/{userId}/personalTasks/{taskId}"})
    public ResponseEntity<Void> createNewPersonalTask(@PathVariable Long userId, @PathVariable Long taskId) {
        taskService.deleteTask(userId, taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
