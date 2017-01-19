package at.favre.lib.hood.noop;

import android.view.View;

import java.util.Map;

import at.favre.lib.hood.interfaces.actions.OnClickAction;

/**
 * A no-op implementation
 */
class OnClickActionNoop implements OnClickAction {
    @Override
    public void onClick(View v, Map.Entry<CharSequence, String> value) {
        //no-op
    }
}
