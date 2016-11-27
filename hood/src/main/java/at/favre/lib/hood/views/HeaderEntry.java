package at.favre.lib.hood.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import at.favre.lib.hood.R;

public class HeaderEntry extends AbstractPageEntry<CharSequence> {
    public static final int VIEWTYPE_HEADER = 1 << 16;

    private final CharSequence header;
    private final Template template;

    public HeaderEntry(CharSequence header) {
        this.header = header;
        template = new Template();
    }

    @Override
    public CharSequence getValue() {
        return header;
    }

    @Override
    public ViewTemplate<CharSequence> getViewTemplate() {
        return template;
    }

    @Override
    public boolean isStaticContent() {
        return true;
    }

    @Override
    public String toLogString() {
        return "#" + header.toString();
    }

    private static class Template implements ViewTemplate<CharSequence> {
        @Override
        public int getViewType() {
            return VIEWTYPE_HEADER;
        }

        @Override
        public View constructView(ViewGroup viewGroup, LayoutInflater inflater) {
            return inflater.inflate(R.layout.template_header, viewGroup, false);
        }

        @Override
        public void setContent(CharSequence value, View view) {
            ((TextView) view.findViewById(R.id.title)).setText(value);
        }

        @Override
        public void decorateViewWithZebra(View view, boolean hasZebra) {
            //no-op
        }
    }
}
