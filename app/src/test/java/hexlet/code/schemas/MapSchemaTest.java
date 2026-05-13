package hexlet.code.schemas;

import hexlet.code.Validator;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MapSchemaTest {

    @Test
    void exampleFromAssignment() {
        Validator v = new Validator();
        MapSchema schema = v.map();

        Assertions.assertTrue(schema.isValid(null));

        schema.required();
        Assertions.assertFalse(schema.isValid(null));
        Assertions.assertTrue(schema.isValid(new HashMap<>()));

        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        Assertions.assertTrue(schema.isValid(data));

        schema.sizeof(2);
        Assertions.assertFalse(schema.isValid(data));
        data.put("key2", "value2");
        Assertions.assertTrue(schema.isValid(data));
    }

    @Test
    void rejectsNonMapWhenPresent() {
        Validator v = new Validator();
        MapSchema schema = v.map();

        Assertions.assertFalse(schema.isValid("not-a-map"));

        schema.required();
        Assertions.assertFalse(schema.isValid(12345));
    }

    @Test
    void sizeofZeroMatchesEmptyMap() {
        Validator v = new Validator();
        MapSchema schema = v.map().sizeof(0);
        Assertions.assertTrue(schema.isValid(new HashMap<>()));
        Assertions.assertFalse(schema.isValid(Map.of("a", "b")));
    }

    @Test
    void sizeofLastCallWins() {
        Validator v = new Validator();
        MapSchema schema = v.map().sizeof(5).sizeof(2);
        Map<String, String> map = Map.of(
            "k1", "v1",
            "k2", "v2"
        );
        Assertions.assertTrue(schema.isValid(map));

        Assertions.assertFalse(schema.sizeof(1).isValid(map));
    }
}
