package at.favre.lib.hood.internal.entries;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import at.favre.lib.hood.R;
import at.favre.lib.hood.interfaces.PageEntry;
import at.favre.lib.hood.interfaces.ViewTemplate;
import at.favre.lib.hood.interfaces.ViewTypes;

/**
 * A lightweight spacer creating gaps
 */
public class SpacerEntry implements PageEntry<Void> {

    public SpacerEntry() {
    }

    @Override
    public Void getValue() {
        return null;
    }

    @Override
    public ViewTemplate<Void> createViewTemplate() {
        return new Template(getViewType());
    }

    @Override
    public int getViewType() {
        return ViewTypes.VIEWTYPE_SPACER;
    }

    @Override
    public String toLogString() {
        return null;
    }

    @Override
    public void disableLogging() {
        //no-op
    }

    @Override
    public void refresh() {
        //no-op
    }

    private static class Template implements ViewTemplate<Void> {
        private final int viewType;

        Template(int viewType) {
            this.viewType = viewType;
        }

        @Override
        public int getViewType() {
            return viewType;
        }

        @Override
        public View constructView(ViewGroup viewGroup, LayoutInflater inflater) {
            return inflater.inflate(R.layout.hoodlib_template_spacer, viewGroup, false);
        }

        @Override
        public void setContent(Void value, @NonNull View view) {
            //no-op
        }

        @Override
        public void decorateViewWithZebra(@NonNull View view, @ColorInt int zebraColor, boolean isOdd) {
            //no-op
        }
    }
}
