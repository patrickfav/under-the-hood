package at.favre.lib.hood.internal;

import android.support.annotation.Nullable;

import java.util.Collections;
import java.util.List;

import at.favre.lib.hood.interfaces.actions.SingleSelectListConfigAction;
import at.favre.lib.hood.interfaces.values.SpinnerElement;
import at.favre.lib.hood.interfaces.values.SpinnerValue;

class MockSingleSelectConfigAction extends SingleSelectListConfigAction {
    public MockSingleSelectConfigAction(@Nullable String label) {
        super(label, new SpinnerValue<List<SpinnerElement>, SpinnerElement>() {
            @Override
            public void onChange(SpinnerElement value) {

            }

            @Override
            public SpinnerElement getValue() {
                return new SpinnerElement.Default("1", "name");
            }

            @Override
            public List<SpinnerElement> getAllPossibleValues() {
                return Collections.<SpinnerElement>singletonList(new SpinnerElement.Default("1", "name"));
            }
        });
    }
}
