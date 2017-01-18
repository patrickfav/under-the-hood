package at.favre.lib.hood.interfaces.values;

import java.util.Collection;

/**
 * A set of values
 */
public interface ValueSet<T extends Collection> {
    T getAlPossibleValues();
}
