package at.favre.lib.hood.interfaces.values;

/**
 * Used for a value that might change (e.g. connectivity status).
 * Calling {@link #getValue()} should always return the current value.
 *
 * @param <T>
 */
public interface DynamicValue<T> {
    /**
     * @return the current value
     */
    T getValue();


    /**
     * Used for static values in APIs where dynamic and static values are supported
     *
     * @param <T>
     */
    class DefaultStaticValue<T> implements DynamicValue<T> {
        private final T value;

        public DefaultStaticValue(T value) {
            this.value = value;
        }

        @Override
        public T getValue() {
            return value;
        }
    }
}
