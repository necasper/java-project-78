package hexlet.code.schemas;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {

    protected final Map<String, Predicate<Object>> checks = new LinkedHashMap<>();
    private final Predicate<Object> optionalSkipWhen;

    protected BaseSchema(Predicate<Object> absentForOptional) {
        this.optionalSkipWhen = absentForOptional;
    }

    protected final void addCheck(String name, Predicate<Object> predicate) {
        checks.put(name, predicate);
    }

    public final boolean isValid(Object value) {
        if (!checks.containsKey("required") && optionalSkipWhen.test(value)) {
            return true;
        }
        for (Predicate<Object> predicate : checks.values()) {
            if (!predicate.test(value)) {
                return false;
            }
        }
        return true;
    }
}
