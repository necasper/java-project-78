package hexlet.code.schemas;

import hexlet.code.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StringSchemaTest {

    @Test
    void withoutRequiredBlankAndNullAreValid() {
        Validator validator = new Validator();
        StringSchema schema = validator.string();

        Assertions.assertTrue(schema.isValid(""));
        Assertions.assertTrue(schema.isValid(null));
    }

    @Test
    void requiredRejectsBlankAndAcceptsNonempty() {
        Validator validator = new Validator();
        StringSchema schema = validator.string().required();

        Assertions.assertFalse(schema.isValid(null));
        Assertions.assertFalse(schema.isValid(""));
        Assertions.assertTrue(schema.isValid("what does the fox say"));
        Assertions.assertTrue(schema.isValid("hexlet"));
    }

    @Test
    void containsConstraintsAccumulate() {
        Validator validator = new Validator();
        StringSchema schema = validator.string();
        Assertions.assertTrue(schema.contains("wh").isValid("what does the fox say"));
        Assertions.assertTrue(schema.contains("what").isValid("what does the fox say"));
        Assertions.assertFalse(schema.contains("whatthe").isValid("what does the fox say"));

        Assertions.assertFalse(schema.isValid("what does the fox say"));
    }

    @Test
    void minLengthLastCallWins() {
        Validator validator = new Validator();
        StringSchema schema = validator.string().minLength(10).minLength(4);
        Assertions.assertTrue(schema.isValid("Hexlet"));
    }

    @Test
    void nonStringFailsWhenNonempty() {
        Validator validator = new Validator();
        StringSchema schema = validator.string();
        Assertions.assertFalse(schema.isValid(123));
    }
}
