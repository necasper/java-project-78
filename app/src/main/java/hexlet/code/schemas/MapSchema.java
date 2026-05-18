package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class MapSchema extends BaseSchema<Object> {

    public MapSchema() {
        super(Objects::isNull);
        addCheck("type", value -> value instanceof Map<?, ?>);
    }

    public MapSchema required() {
        addCheck("required", Objects::nonNull);
        return this;
    }

    public MapSchema sizeof(int size) {
        addCheck("sizeof", value -> value instanceof Map<?, ?> map && map.size() == size);
        return this;
    }

    public MapSchema shape(Map<String, ? extends BaseSchema<?>> schemas) {
        Map<String, BaseSchema<?>> snapshot = new HashMap<>(schemas);
        addCheck("shape", value -> {
            if (!(value instanceof Map<?, ?> map)) {
                return false;
            }
            for (Map.Entry<String, BaseSchema<?>> entry : snapshot.entrySet()) {
                if (!entry.getValue().isValid(map.get(entry.getKey()))) {
                    return false;
                }
            }
            return true;
        });
        return this;
    }
}
