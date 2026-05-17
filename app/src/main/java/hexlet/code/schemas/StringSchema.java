package hexlet.code.schemas;

import java.util.Objects;

public class StringSchema extends BaseSchema<String> {

    public StringSchema() {
        addCheck("type", value -> value instanceof String);
    }

    @Override
    protected boolean isAbsent(Object value) {
        return value == null || "".equals(value);
    }

    public StringSchema required() {
        activateRequired();
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
