package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class StringSchema {

    private boolean required;
    private Integer minLength;
    private final List<String> substrs = new ArrayList<>();

    public StringSchema required() {
        this.required = true;
        return this;
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

    public boolean isValid(Object data) {
        if (!required && isBlankOrMissing(data)) {
            return true;
        }

        if (required && isBlankOrMissing(data)) {
            return false;
        }

        if (!(data instanceof String s)) {
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

    private static boolean isBlankOrMissing(Object data) {
        return data == null || "".equals(data);
    }
}
