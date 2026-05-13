package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;

public class MapSchema extends BaseSchema<MapSchema> {

    private Integer expectedSize;
    private Map<String, BaseSchema<?>> shapeSchemas;

    @Override
    protected boolean isAbsent(Object value) {
        return value == null;
    }

    @Override
    protected boolean validatePresent(Object value) {
        if (!(value instanceof Map<?, ?> map)) {
            return false;
        }

        if (expectedSize != null && map.size() != expectedSize) {
            return false;
        }

        if (!passesShapeChecks(map)) {
            return false;
        }

        return true;
    }

    public MapSchema sizeof(int size) {
        this.expectedSize = size;
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema<?>> schemas) {
        this.shapeSchemas = new HashMap<>(schemas);
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
