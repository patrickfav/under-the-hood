package at.favre.lib.hood.page.entries;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import at.favre.lib.hood.R;
import at.favre.lib.hood.page.PageEntry;
import at.favre.lib.hood.page.ViewTemplate;

import static at.favre.lib.hood.page.entries.ViewTypes.VIEWTYPE_HEADER;

public class HeaderEntry implements PageEntry<CharSequence> {

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
    public String toLogString() {
        return "#" + header.toString();
    }

    @Override
    public void refresh() {
        //no-op
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
