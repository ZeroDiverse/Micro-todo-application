package fil.adventural.microprojectmanagement.services;

import fil.adventural.microprojectmanagement.models.Project;
import fil.adventural.microprojectmanagement.models.User;
import fil.adventural.microprojectmanagement.repositories.ProjectRepository;
import fil.adventural.microprojectmanagement.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    /**
     * Find projects by user email
     * @param email email
     * @return the projects
     */
    public List<Project> findProjectsByUserEmail(String email) {
        return projectRepository.findProjectsByUserEmail(email);
    }

    /**
     * Find projects by user id
     * @param userId user id
     * @return the projects
     */
    public List<Project> findProjectsByUserId(long userId) {
        return projectRepository.findProjectsByUserId(userId);
    }

    public Project findProjectById_ByUserId(long projectId, long userId) {
        return projectRepository.findProjectById_ByUserId(projectId, userId);
    }

    /**
     * Create thr project by user id
     * @param project project to be created
     * @return the id of created project
     */
    public Long saveProject_ByUserId(Project project, Long userId) {
        //Get user optional
        Optional<User> userOptional = userRepository.findById(userId);

        if(userOptional.isEmpty()){
            return null;
        }
        //Get user
        User user = userOptional.get();

        List<User> members = project.getMembers() != null ? project.getMembers(): new ArrayList<>();

        members.add(user);

        //Set project's user to user
        project.setMembers(members);

        //Save new project
        project =projectRepository.save(project);

        return project.getId();
    }
}
