package at.favre.lib.hood.page.values;

import java.util.Collection;

/**
 *
 */
public interface ValueSet<T extends Collection> {
    T getAlPossibleValues();
}
