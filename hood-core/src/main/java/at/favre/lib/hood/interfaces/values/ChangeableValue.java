package at.favre.lib.hood.interfaces.values;

/**
 * As en enhancement to {@link DynamicValue} a value that can also be changed (e.g. like a value in
 * {@link android.content.SharedPreferences}. Returned value and change value might be of different
 * types (e.g value is the whole Object, change-value just the id).
 *
 * @param <T> value
 * @param <E> change-value
 */
public interface ChangeableValue<T, E> extends DynamicValue<T> {
    /**
     * Given a value change the internal status. Will affect what {@link #getValue()}
     * returns.
     *
     * @param value to change to
     */
    void onChange(E value);
}
