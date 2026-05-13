package hexlet.code.schemas;

import hexlet.code.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NumberSchemaTest {

    @Test
    void exampleFromAssignment() {
        Validator v = new Validator();
        NumberSchema schema = v.number();

        Assertions.assertTrue(schema.isValid(5));
        Assertions.assertTrue(schema.isValid(null));

        Assertions.assertTrue(schema.positive().isValid(null));

        schema.required();
        Assertions.assertFalse(schema.isValid(null));
        Assertions.assertTrue(schema.isValid(10));

        Assertions.assertFalse(schema.isValid(-10));
        Assertions.assertFalse(schema.isValid(0));

        schema.range(5, 10);
        Assertions.assertTrue(schema.isValid(5));
        Assertions.assertTrue(schema.isValid(10));
        Assertions.assertFalse(schema.isValid(4));
        Assertions.assertFalse(schema.isValid(11));
    }

    @Test
    void requiredWrongTypeFails() {
        Validator v = new Validator();
        NumberSchema schema = v.number().required();
        Assertions.assertFalse(schema.isValid("5"));
        Assertions.assertFalse(schema.isValid(10.5));
    }

    @Test
    void rangeLastCallWins() {
        Validator v = new Validator();
        NumberSchema schema = v.number().range(1, 3).range(5, 10);
        Assertions.assertFalse(schema.isValid(2));
        Assertions.assertTrue(schema.isValid(7));
    }
}
