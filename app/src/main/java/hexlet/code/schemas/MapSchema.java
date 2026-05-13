package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema<MapSchema> {

    private Integer expectedSize;

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

        return true;
    }

    public MapSchema sizeof(int size) {
        this.expectedSize = size;
        return this;
    }
}
