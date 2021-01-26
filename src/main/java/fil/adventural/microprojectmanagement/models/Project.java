package fil.adventural.microprojectmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String title;

    @Column(name = "color", columnDefinition = "VARCHAR(50) default '#6800fa'")
    @Builder.Default
    private String color = "#6800fa";

    @Builder.Default
    private boolean isFavourite = false;

    @Enumerated(value = EnumType.STRING)
    @Builder.Default
    private ProjectView projectView = ProjectView.LIST;

    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST},
            mappedBy = "projects")
    @Builder.Default
    private List<User> members = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    @Builder.Default
    private List<Task> projectTasks = new ArrayList<>();

    /**
     * Add member to project
     * @param user user to be added
     */
    @Transient
    public void addMember(User user) {
        members.add(user);
    }

    /**
     * Remove member from project
     * @param user user to be removed
     */
    @Transient
    public void removeMember(User user) {
        members.remove(user);
    }

    /**
     * Check if project contains the user
     * @param user the user to be checked
     * @return true if user in project and false if not
     */
    @Transient
    public boolean containsMember(User user) {
        return members.contains(user);
    }

    /**
     * Add task to project tasks
     * @param task task to be added
     */
    public void addTask(Task task) {
        projectTasks.add(task);
        task.setProject(this);
    }
}
