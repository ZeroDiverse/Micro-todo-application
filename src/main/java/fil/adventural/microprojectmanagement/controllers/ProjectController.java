package fil.adventural.microprojectmanagement.controllers;

import fil.adventural.microprojectmanagement.models.Project;
import fil.adventural.microprojectmanagement.services.ProjectService;
import fil.adventural.microprojectmanagement.tdo.ProjectDto;
import fil.adventural.microprojectmanagement.tdo.UserRequest;
import fil.adventural.microprojectmanagement.tdo.UserResponse;
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
    public ResponseEntity<List<ProjectDto>> getProjectsByUserId(@PathVariable Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(projectService.findProjectsByUserId(userId));
    }

    @GetMapping(value = {"/users/{userId}/projects/{projectId}", "/users/{userId}/projects/{projectId}"})
    public ResponseEntity<ProjectDto> getProjectByIdByUserId(@PathVariable Long projectId, @PathVariable Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(projectService.findProjectById_ByUserId(projectId, userId));
    }

    @GetMapping(value = {"/projects/{projectId}/members", "/projects/{projectId}/members/"})
    public ResponseEntity<List<UserResponse>> getMembers_ByProjectId(@PathVariable Long projectId) {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.findMembers_ByProjectId(projectId));
    }

    @PostMapping(value = {"/users/{userId}/projects", "/users/{userId}/projects"})
    public ResponseEntity<Long> createProjectByUserId(@RequestBody ProjectDto projectDto, @PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.saveProject_ByUserId(projectDto, userId));
    }

    @PostMapping(value = {"/users/{userId}/addMember/projects/{projectId}", "/users/{userId}/addMember/projects/{projectId}/"})
    public ResponseEntity<Void> addMemberToProject(@PathVariable Long userId, @PathVariable Long projectId, @RequestBody UserRequest userRequest) {
        projectService.addMemberToProject(projectId, userId, userRequest.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = {"/users/{userId}/removeMember/projects/{projectId}", "/users/{userId}/addMember/projects/{projectId}/"})
    public ResponseEntity<Void> removeMemberToProject(@PathVariable Long userId, @PathVariable Long projectId, @RequestBody UserRequest userRequest) {
        projectService.removeMemberFromProject(projectId, userId, userRequest.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(value = {"/users/{userId}/projects", "/users/{userId}/projects/"})
    public ResponseEntity<ProjectDto> updateProject(@PathVariable Long userId, @RequestBody ProjectDto projectDto) {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.updateProject(userId, projectDto));
    }

    @DeleteMapping(value = {"/projects/{projectId}", "/projects/{projectId}/"})
    public ResponseEntity<Void> deleteProjectById(@PathVariable Long projectId) {
        boolean res = projectService.deleteProjectById(projectId);
        if (res) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
