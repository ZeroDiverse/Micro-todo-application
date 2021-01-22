package fil.adventural.microprojectmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private boolean isPersonal;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User owner;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = true)
    private Project project;
}
