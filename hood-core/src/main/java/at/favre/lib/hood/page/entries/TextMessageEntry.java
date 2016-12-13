package at.favre.lib.hood.page.entries;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import at.favre.lib.hood.R;
import at.favre.lib.hood.page.PageEntry;
import at.favre.lib.hood.page.ViewTemplate;
import at.favre.lib.hood.util.HoodUtil;

import static at.favre.lib.hood.page.entries.ViewTypes.VIEWTYPE_MESSAGE;

/**
 * A simple, non-interactive entry that displays a simple text message
 */
public class TextMessageEntry implements PageEntry<CharSequence> {

    private final CharSequence message;
    private final Template template;

    public TextMessageEntry(@NonNull CharSequence message) {
        this.message = message;
        this.template = new Template();
    }

    @Override
    public CharSequence getValue() {
        return message;
    }

    @Override
    public ViewTemplate<CharSequence> getViewTemplate() {
        return template;
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
        @Override
        public int getViewType() {
            return VIEWTYPE_MESSAGE;
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
            HoodUtil.setZebraToView(view, zebraColor, isOdd);
        }
    }
}
