package hexlet.code.schemas;

import java.util.Objects;

public final class StringSchema extends BaseSchema<String> {

    public StringSchema() {
        super(value -> value == null || "".equals(value));
        addCheck("type", value -> value instanceof String);
    }

    public StringSchema required() {
        addCheck("required", value -> value instanceof String s && !s.isEmpty());
        return this;
    }

    public StringSchema minLength(int length) {
        addCheck("minLength", value -> value instanceof String s && s.length() >= length);
        return this;
    }

    public StringSchema contains(String substring) {
        Objects.requireNonNull(substring, "substring");
        addCheck("contains:" + substring, value -> value instanceof String s && s.contains(substring));
        return this;
    }
}
