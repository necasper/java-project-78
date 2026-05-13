package hexlet.code.schemas;

import hexlet.code.Validator;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

    @Test
    void shapeValidatesNestedStringFields() {
        Validator v = new Validator();

        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("firstName", v.string().required());
        schemas.put("lastName", v.string().required().minLength(2));

        MapSchema schema = v.map().shape(schemas);

        Map<String, String> human1 = new HashMap<>();
        human1.put("firstName", "John");
        human1.put("lastName", "Smith");
        Assertions.assertTrue(schema.isValid(human1));

        Map<String, String> human2 = new HashMap<>();
        human2.put("firstName", "John");
        human2.put("lastName", null);
        Assertions.assertFalse(schema.isValid(human2));

        Map<String, String> human3 = new HashMap<>();
        human3.put("firstName", "Anna");
        human3.put("lastName", "B");
        Assertions.assertFalse(schema.isValid(human3));
    }

    @Test
    void shapeWithNumberPositiveRangeAndCombinedConstraints() {
        Validator v = new Validator();

        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("name", v.string().required());
        schemas.put("age", v.number().required().positive().range(18, 120));

        MapSchema schema = v.map().required().shape(schemas).sizeof(2);

        Map<String, Object> invalidAge = Map.of(
            "name", "Ada",
            "age", 10
        );
        Assertions.assertFalse(schema.isValid(invalidAge));

        Map<String, Object> invalidNotPositive = new HashMap<>();
        invalidNotPositive.put("name", "Ada");
        invalidNotPositive.put("age", -1);
        Assertions.assertFalse(schema.isValid(invalidNotPositive));

        Map<String, Object> validPerson = Map.of(
            "name", "Ada",
            "age", 30
        );
        Assertions.assertTrue(schema.isValid(validPerson));
    }

    @Test
    void shapeAllowsOptionalAbsentNestedKeyWhenSchemaOptional() {
        Validator v = new Validator();

        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("firstName", v.string().required());
        schemas.put("nickname", v.string());

        Map<String, Object> missingNickname = Map.of(
            "firstName", "John"
        );
        Assertions.assertTrue(v.map().shape(schemas).isValid(missingNickname));

        Map<String, Object> nicknameWrongType = new LinkedHashMap<>();
        nicknameWrongType.put("firstName", "John");
        nicknameWrongType.put("nickname", 777);
        Assertions.assertFalse(v.map().shape(schemas).isValid(nicknameWrongType));
    }

    @Test
    void shapeDefinitionIsReplacedByLastShapeCall() {
        Validator v = new Validator();

        Map<String, BaseSchema<?>> first = Map.of(
            "a", v.number().required()
        );
        Map<String, BaseSchema<?>> second = Map.of(
            "b", v.string().required()
        );

        MapSchema schema = v.map().shape(first).shape(second);

        Assertions.assertFalse(schema.isValid(Map.of("b", "")));
        Assertions.assertTrue(schema.isValid(Map.of("b", "ok")));
        Assertions.assertFalse(schema.isValid(Map.of()));
    }

    @Test
    void mapSchemaValidatesSubsetOfNestedMapAgainstShape() {
        Validator v = new Validator();

        Map<String, BaseSchema<?>> schemas = Map.of(
            "tracked", v.string().required().contains("@")
        );
        MapSchema schema = v.map().shape(schemas);

        Assertions.assertFalse(schema.isValid(Map.of()));

        Map<String, Object> withExtra = new HashMap<>();
        withExtra.put("tracked", "u@site");
        withExtra.put("extra", true);

        Assertions.assertTrue(schema.isValid(withExtra));
    }
}
