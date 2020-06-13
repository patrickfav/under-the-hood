package at.favre.lib.hood.internal.entries;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import at.favre.lib.hood.R;
import at.favre.lib.hood.interfaces.PageEntry;
import at.favre.lib.hood.interfaces.ViewTemplate;
import at.favre.lib.hood.interfaces.ViewTypes;

/**
 * A simple, non-interactive header used to separate other entries
 */
public class HeaderEntry implements PageEntry<CharSequence> {

    private final CharSequence header;
    private boolean loggingEnabled = true;

    /**
     * @param header as shown in ui
     */
    public HeaderEntry(CharSequence header) {
        this.header = header;
    }

    @Override
    public CharSequence getValue() {
        return header;
    }

    @Override
    public ViewTemplate<CharSequence> createViewTemplate() {
        return new Template(getViewType());
    }

    @Override
    public int getViewType() {
        return ViewTypes.VIEWTYPE_HEADER;
    }

    @Override
    public String toLogString() {
        return loggingEnabled ? "## " + header.toString() : null;
    }

    @Override
    public void disableLogging() {
        loggingEnabled = false;
    }

    @Override
    public void refresh() {
        //no-op
    }

    private static class Template implements ViewTemplate<CharSequence> {
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
            return inflater.inflate(R.layout.hoodlib_template_header, viewGroup, false);
        }

        @Override
        public void setContent(CharSequence value, @NonNull View view) {
            ((TextView) view.findViewById(R.id.title)).setText(value);
        }

        @Override
        public void decorateViewWithZebra(@NonNull View view, @ColorInt int zebraColor, boolean isOdd) {
            //no-op
        }
    }
}
