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
    private List<Project> projects = new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<Task> personalTasks = new ArrayList<>();

    /**
     * Add project to user's projects
     * @param project the project to be added
     */
    @Transient
    public void addProject(Project project) {
        projects.add(project);
    }

    /**
     * Remove project from user's projects
     * @param project the project to be removed
     */
    @Transient
    public void removeProject(Project project) {
        if (!projects.contains(project)) {
            throw new UserNotInProjectException();
        }
        projects.remove(project);
    }
}
