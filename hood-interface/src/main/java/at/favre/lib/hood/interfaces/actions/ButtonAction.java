package at.favre.lib.hood.interfaces.actions;

import android.view.View;

/**
 * An action containing a name (button text) and a click-listener used for the button
 */
public class ButtonAction {
    public final String label;
    public final View.OnClickListener onClickListener;

    public ButtonAction(String label, View.OnClickListener onClickListener) {
        this.label = label;
        this.onClickListener = onClickListener;
    }
}
