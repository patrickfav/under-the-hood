package at.favre.lib.hood.noop;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.legacy.widget.Space;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import at.favre.lib.hood.interfaces.ViewTemplate;

/**
 * A no-op implementation
 */
class ViewTemplateNoop implements ViewTemplate<String> {
    @Override
    public int getViewType() {
        return -1;
    }

    @Override
    public View constructView(ViewGroup parent, LayoutInflater inflater) {
        return new Space(parent.getContext());
    }

    @Override
    public void setContent(String value, @NonNull View view) {
        //no-op
    }

    @Override
    public void decorateViewWithZebra(@NonNull View view, @ColorInt int zebraColor, boolean isOdd) {
        //no-op
    }
}
