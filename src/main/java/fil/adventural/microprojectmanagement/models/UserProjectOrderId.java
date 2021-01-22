package fil.adventural.microprojectmanagement.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@EqualsAndHashCode
public class UserProjectOrderId implements Serializable {
    @Column(name = "user_id")
    private Long user;
    @Column(name = "project_id")
    private Long project;

    // getters/setters and most importantly equals() and hashCode()
}
