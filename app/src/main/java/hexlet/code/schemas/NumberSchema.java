package hexlet.code.schemas;

public final class NumberSchema extends BaseSchema<Integer> {

    public NumberSchema() {
        addCheck("type", value -> value instanceof Integer);
    }

    @Override
    protected boolean isAbsent(Object value) {
        return value == null;
    }

    public NumberSchema required() {
        activateRequired();
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
