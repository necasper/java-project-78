package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StringSchema extends BaseSchema<StringSchema> {

    private Integer minLength;
    private final List<String> substrs = new ArrayList<>();

    @Override
    protected boolean isAbsent(Object value) {
        return value == null || "".equals(value);
    }

    @Override
    protected boolean validatePresent(Object value) {
        if (!(value instanceof String s)) {
            return false;
        }

        if (minLength != null && s.length() < minLength) {
            return false;
        }

        for (String part : substrs) {
            if (!s.contains(part)) {
                return false;
            }
        }

        return true;
    }

    public StringSchema minLength(int length) {
        this.minLength = length;
        return this;
    }

    public StringSchema contains(String substring) {
        Objects.requireNonNull(substring, "substring");
        substrs.add(substring);
        return this;
    }
}
