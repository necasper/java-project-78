package hexlet.code.schemas;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

public abstract class BaseSchema<Self extends BaseSchema<Self>> {

    protected final Map<String, Predicate<Object>> checks = new LinkedHashMap<>();
    protected boolean required;

    protected final void addCheck(String name, Predicate<Object> predicate) {
        checks.put(name, predicate);
    }

    @SuppressWarnings("unchecked")
    public Self required() {
        required = true;
        return (Self) this;
    }

    public final boolean isValid(Object value) {
        if (!required && isAbsent(value)) {
            return true;
        }
        if (required && isAbsent(value)) {
            return false;
        }
        for (Predicate<Object> predicate : checks.values()) {
            if (!predicate.test(value)) {
                return false;
            }
        }
        return true;
    }

    protected abstract boolean isAbsent(Object value);
}
