package fil.adventural.microprojectmanagement.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectViewTest {

    @Test
    public void testProjectViewName_WillReturnCorrectName(){
        assertThat(ProjectView.BOARD.getProjectViewName()).isEqualTo("board");
        assertThat(ProjectView.LIST.getProjectViewName()).isEqualTo("list");
    }

}
