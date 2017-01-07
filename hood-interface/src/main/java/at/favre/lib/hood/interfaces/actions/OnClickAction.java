package at.favre.lib.hood.interfaces.actions;

import android.view.View;

import java.util.Map;

/**
 * Used to define what should happen on click
 */
public interface OnClickAction {
    /**
     * Clicked on an {@link View} type element
     *
     * @param v     the view which was clicked on (to use e.g. its context)
     * @param value value can be different depending on it's use.
     */
    void onClick(View v, Map.Entry<CharSequence, String> value);
}
