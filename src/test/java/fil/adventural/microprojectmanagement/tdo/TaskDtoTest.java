package fil.adventural.microprojectmanagement.tdo;

import fil.adventural.microprojectmanagement.models.Project;
import fil.adventural.microprojectmanagement.models.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TaskDtoTest {

    private Task task;

    private TaskDto taskDto;

    private Project project;


    @BeforeEach
    void setUp() {

        project = Project.builder().id(1L).build();

        task = Task.builder().id(1L).title("Hello").project(project).build();

        taskDto = TaskDto.builder().id(1L).title("Hello").project(1L).build();
    }

    @Test
    void testTaskDtoWillHaveSameTitleIdAndDueDateWithTask(){
        assertThat(taskDto.getId()).isEqualTo(task.getId());
        assertThat(taskDto.getTitle()).isEqualTo(task.getTitle());
        assertThat(taskDto.getProject()).isEqualTo(task.getProject().getId());
    }

}
