package at.favre.lib.hood.internal.entries;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import at.favre.lib.hood.R;
import at.favre.lib.hood.interfaces.PageEntry;
import at.favre.lib.hood.interfaces.ViewTemplate;
import at.favre.lib.hood.interfaces.ViewTypes;
import at.favre.lib.hood.view.HoodDebugPageView;


/**
 * A simple, non-interactive entry that displays a simple text message
 */
public class TextMessageEntry implements PageEntry<CharSequence> {

    private final CharSequence message;

    /**
     * Creates a simple, non-interactive text message
     *
     * @param message as shown in
     */
    public TextMessageEntry(@NonNull CharSequence message) {
        this.message = message;
    }

    @Override
    public CharSequence getValue() {
        return message;
    }

    @Override
    public ViewTemplate<CharSequence> createViewTemplate() {
        return new Template(getViewType());
    }

    @Override
    public int getViewType() {
        return ViewTypes.VIEWTYPE_MESSAGE;
    }

    @Override
    public String toLogString() {
        return message.toString();
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
            return inflater.inflate(R.layout.hoodlib_template_message, viewGroup, false);
        }

        @Override
        public void setContent(CharSequence value, @NonNull View view) {
            ((TextView) view.findViewById(R.id.message)).setText(value);
        }

        @Override
        public void decorateViewWithZebra(@NonNull View view, @ColorInt int zebraColor, boolean isOdd) {
            HoodDebugPageView.setZebraToView(view, zebraColor, isOdd);
        }
    }
}
