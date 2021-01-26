package fil.adventural.microprojectmanagement.models;

import fil.adventural.microprojectmanagement.exceptions.UserNotInProjectException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    private boolean isEnabled;

    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "project_member",
            joinColumns = @JoinColumn(name = "member_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    @Builder.Default
    private List<Project> projects = new ArrayList<>();


    @OneToMany(targetEntity = Task.class, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "owner")
    @Builder.Default
    private List<Task> personalTasks = new ArrayList<>();

    /**
     * Add project to user's projects
     * @param project the project to be added
     */
    public void addProject(Project project) {
        projects.add(project);
    }

    /**
     * Remove project from user's projects
     * @param project the project to be removed
     */
    public void removeProject(Project project) {
        if (!projects.contains(project)) {
            throw new UserNotInProjectException();
        }
        projects.remove(project);
    }

    /**
     * Add to personal tasks
     * @param task the task to be added
     */
    public void addTask(Task task) {
        //Add task to user's personal task
        this.personalTasks.add(task);
        //Set task owner to this user
        task.setOwner(this);
    }

    /**
     * Remove task from personal tasks
     * @param task the task to be removed
     */
    @Transient
    public void removeTask(Task task) {
        //Remove the task from personal task
        this.personalTasks.remove(task);
        //Set task owner to null
        task.setOwner(null);
    }
}
