package at.favre.lib.hood.interfaces.values;

import java.util.Collection;

/**
 * A spinner (dropdown list) value, which has the list of all values and a changeable-value
 * @param <T> type of the while list
 * @param <E> type of the changeable value
 */
public interface SpinnerValue<T extends Collection, E> extends ChangeableValue<E, E>, ValueSet<T> {
}
