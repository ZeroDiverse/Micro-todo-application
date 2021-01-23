package fil.adventural.microprojectmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    private String title;

    private String color;

    private boolean isFavourite;

    @Enumerated(value = EnumType.STRING)
    private ProjectView projectView = ProjectView.LIST;

    @ManyToMany(cascade = {CascadeType.ALL},
            mappedBy = "projects")
    private List<User> members = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private List<Task> sharedTasks = new ArrayList<>();

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
}
