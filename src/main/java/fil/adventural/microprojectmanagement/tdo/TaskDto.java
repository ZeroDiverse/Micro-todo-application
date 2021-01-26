package fil.adventural.microprojectmanagement.tdo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class TaskDto {
    private Long id;
    private String title;
    private Long project;
}
