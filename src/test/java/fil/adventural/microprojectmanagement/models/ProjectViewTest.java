package fil.adventural.microprojectmanagement.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProjectViewTest {

    @Test
    void testProjectViewName_WillReturnCorrectName() {
        assertThat(ProjectView.BOARD.getProjectViewName()).isEqualTo("board");
        assertThat(ProjectView.LIST.getProjectViewName()).isEqualTo("list");
    }

}
