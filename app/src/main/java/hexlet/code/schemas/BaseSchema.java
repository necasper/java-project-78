package hexlet.code.schemas;

public abstract class BaseSchema<Self extends BaseSchema<Self>> {

    protected boolean required;

    @SuppressWarnings("unchecked")
    public Self required() {
        this.required = true;
        return (Self) this;
    }

    public final boolean isValid(Object value) {
        if (!required && isAbsent(value)) {
            return true;
        }
        if (required && isAbsent(value)) {
            return false;
        }
        return validatePresent(value);
    }

    protected abstract boolean isAbsent(Object value);

    protected abstract boolean validatePresent(Object value);
}
