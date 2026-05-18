package hexlet.code.schemas;

import java.util.Objects;

public final class NumberSchema extends BaseSchema<Integer> {

    public NumberSchema() {
        super(Objects::isNull);
        addCheck("type", value -> value instanceof Integer);
    }

    public NumberSchema required() {
        addCheck("required", Objects::nonNull);
        return this;
    }

    public NumberSchema positive() {
        addCheck("positive", value -> value instanceof Integer i && i > 0);
        return this;
    }

    public NumberSchema range(int min, int max) {
        addCheck("range", value -> value instanceof Integer i && i >= min && i <= max);
        return this;
    }
}
