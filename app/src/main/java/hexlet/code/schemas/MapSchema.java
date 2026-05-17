package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;

public class MapSchema extends BaseSchema<Object> {

    private Map<String, BaseSchema<?>> shapeSchemas;

    public MapSchema() {
        addCheck("type", value -> value instanceof Map<?, ?>);
    }

    @Override
    protected boolean isAbsent(Object value) {
        return value == null;
    }

    public MapSchema required() {
        activateRequired();
        return this;
    }

    public MapSchema sizeof(int size) {
        addCheck("sizeof", value -> value instanceof Map<?, ?> map && map.size() == size);
        return this;
    }

    public MapSchema shape(Map<String, ? extends BaseSchema<?>> schemas) {
        shapeSchemas = new HashMap<>(schemas);
        addCheck("shape", value -> value instanceof Map<?, ?> map && passesShapeChecks(map));
        return this;
    }

    private boolean passesShapeChecks(Map<?, ?> map) {
        if (shapeSchemas == null || shapeSchemas.isEmpty()) {
            return true;
        }

        for (Map.Entry<String, BaseSchema<?>> entry : shapeSchemas.entrySet()) {
            String key = entry.getKey();
            BaseSchema<?> schema = entry.getValue();
            Object nested = map.get(key);
            if (!schema.isValid(nested)) {
                return false;
            }
        }

        return true;
    }
}
