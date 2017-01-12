package at.favre.lib.hood.interfaces.values;

import java.util.Collection;

/**
 *
 */
public interface ValueSet<T extends Collection> {
    T getAlPossibleValues();
}
