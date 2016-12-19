package at.favre.lib.hood.interfaces.actions;

import at.favre.lib.hood.interfaces.values.ChangeableValue;

/**
 * The abstraction of the switch containing a label (as shown in ui) and a changeable value
 * representing the boolean switch value. Default implementation see {@link at.favre.lib.hood.defaults.DefaultConfigActions}
 */
public class BoolConfigAction {
    public final String label;
    public final ChangeableValue<Boolean, Boolean> changeableValue;

    public BoolConfigAction(String label, ChangeableValue<Boolean, Boolean> boolValue) {
        this.label = label;
        this.changeableValue = boolValue;
    }
}
