package at.favre.lib.hood.interfaces.actions;

import android.support.annotation.Nullable;

import java.util.List;

import at.favre.lib.hood.interfaces.values.SpinnerElement;
import at.favre.lib.hood.interfaces.values.SpinnerValue;

/**
 * Defines the ui label and list elements (as well as how changing it will be handled
 */
public class SingleSelectListConfigAction {
    public final String label;
    public final SpinnerValue<List<SpinnerElement>, SpinnerElement> changeableValue;

    public SingleSelectListConfigAction(@Nullable String label, SpinnerValue<List<SpinnerElement>, SpinnerElement> value) {
        this.label = label;
        this.changeableValue = value;
    }
}
