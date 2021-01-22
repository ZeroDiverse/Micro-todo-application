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

    @ManyToMany(mappedBy = "projects")
    private List<User> members = new ArrayList<>();

    @OneToMany(mappedBy = "owner")
    private List<Task> sharedTasks = new ArrayList<>();
}
