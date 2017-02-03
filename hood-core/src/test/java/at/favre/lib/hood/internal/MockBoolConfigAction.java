package at.favre.lib.hood.internal;

import at.favre.lib.hood.interfaces.actions.BoolConfigAction;
import at.favre.lib.hood.interfaces.values.ChangeableValue;

public class MockBoolConfigAction extends BoolConfigAction {
    public MockBoolConfigAction(String label) {
        super(label, new ChangeableValue<Boolean, Boolean>() {
            @Override
            public void onChange(Boolean value) {

            }

            @Override
            public Boolean getValue() {
                return true;
            }
        });
    }
}
