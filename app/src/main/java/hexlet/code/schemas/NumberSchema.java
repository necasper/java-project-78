package hexlet.code.schemas;

public class NumberSchema extends BaseSchema<NumberSchema> {

    private boolean positive;
    private Integer rangeMin;
    private Integer rangeMax;

    @Override
    protected boolean isAbsent(Object value) {
        return value == null;
    }

    @Override
    protected boolean validatePresent(Object value) {
        if (!(value instanceof Integer integer)) {
            return false;
        }
        int v = integer.intValue();

        if (positive && v <= 0) {
            return false;
        }

        if (rangeMin != null && rangeMax != null) {
            if (v < rangeMin || v > rangeMax) {
                return false;
            }
        }

        return true;
    }

    public NumberSchema positive() {
        this.positive = true;
        return this;
    }

    public NumberSchema range(int min, int max) {
        this.rangeMin = min;
        this.rangeMax = max;
        return this;
    }
}
