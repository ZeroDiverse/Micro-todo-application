package fil.adventural.microprojectmanagement.controllers;

import fil.adventural.microprojectmanagement.models.Project;
import fil.adventural.microprojectmanagement.services.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping(value = {"/users/{userId}/projects", "/users/{userId}/projects/"})
    public ResponseEntity<List<Project>> getProjectsByUserId(@PathVariable Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(projectService.findProjectsByUserId(userId));
    }

    @GetMapping(value = {"/users/{userId}/projects/{projectId}", "/users/{userId}/projects/{projectId}"})
    public ResponseEntity<Project> getProjectById_ByUserId(@PathVariable Long projectId, @PathVariable Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(projectService.findProjectById_ByUserId(projectId, userId));
    }

    @PostMapping(value = {"/users/{userId}/projects", "/users/{userId}/projects"})
    public ResponseEntity<Long> createProject_ByUserId(@RequestBody Project project, @PathVariable Long userId){
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.saveProject_ByUserId(project, userId));
    }
}
