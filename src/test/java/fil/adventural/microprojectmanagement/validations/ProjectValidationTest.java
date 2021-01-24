package fil.adventural.microprojectmanagement.validations;

import fil.adventural.microprojectmanagement.models.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Project validation rule test
 */
class ProjectValidationTest {

    private Validator validator;
    private Project project;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        project = new Project();
    }

    @Test
    void testProjectValidationFail_IfTitleIsEmpty() {
        project.setTitle("");
        Set<ConstraintViolation<Project>> violations = validator.validate(project);
        assertThat(violations.isEmpty()).isFalse();
    }

    @Test
    void testProjectValidationFail_IfTitleIsNull() {
        project.setTitle(null);
        Set<ConstraintViolation<Project>> violations = validator.validate(project);
        assertThat(violations.isEmpty()).isFalse();
    }

    @Test
    void testProjectValidationSuccess_IfTitleIsOk() {
        project.setTitle("Testing");
        Set<ConstraintViolation<Project>> violations = validator.validate(project);
        assertThat(violations.isEmpty()).isTrue();
    }
}
