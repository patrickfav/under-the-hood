package at.favre.lib.hood.page.entries;

import android.app.Activity;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;

import at.favre.lib.hood.KeyValueDetailActivity;
import at.favre.lib.hood.R;
import at.favre.lib.hood.page.PageEntry;
import at.favre.lib.hood.page.ViewTemplate;
import at.favre.lib.hood.page.values.DynamicValue;
import at.favre.lib.hood.util.HoodUtil;

import static at.favre.lib.hood.page.entries.ViewTypes.VIEWTYPE_KEYVALUE;
import static at.favre.lib.hood.page.entries.ViewTypes.VIEWTYPE_KEYVALUE_MULTILINE;

public class KeyValueEntry implements Comparator<KeyValueEntry>, PageEntry<Map.Entry<CharSequence, String>> {

    private Map.Entry<CharSequence, String> data;
    private final Template template;
    private final DynamicValue<String> value;

    public KeyValueEntry(CharSequence key, DynamicValue<String> value, boolean multiLine) {
        this.value = value;
        this.data = new AbstractMap.SimpleEntry<>(key, value.getValue());
        this.template = new Template(multiLine);
    }

    public KeyValueEntry(CharSequence key, final String value, boolean multiLine) {
        this(key, new DynamicValue<String>() {
            @Override
            public String getValue() {
                return value;
            }
        }, multiLine);
    }

    public KeyValueEntry(CharSequence key, final String value) {
        this(key, value, false);
    }

    @Override
    public Map.Entry<CharSequence, String> getValue() {
        return data;
    }

    @Override
    public ViewTemplate<Map.Entry<CharSequence, String>> getViewTemplate() {
        return template;
    }

    @Override
    public String toLogString() {
        return "\t" + data.getKey() + "=" + data.getValue();
    }

    @Override
    public void refresh() {
        data = new AbstractMap.SimpleEntry<>(data.getKey(), value.getValue());
    }

    @Override
    public int compare(KeyValueEntry o1, KeyValueEntry o2) {
        return String.valueOf(o1.getValue().getKey()).compareTo(o2.getValue().getKey().toString());
    }

    private static class Template implements ViewTemplate<Map.Entry<CharSequence, String>> {
        private final boolean multiLine;

        public Template(boolean multiLine) {
            this.multiLine = multiLine;
        }

        @Override
        public int getViewType() {
            return multiLine ? VIEWTYPE_KEYVALUE_MULTILINE : VIEWTYPE_KEYVALUE;
        }

        @Override
        public View constructView(ViewGroup viewGroup, LayoutInflater inflater) {
            if (multiLine) {
                return inflater.inflate(R.layout.template_keyvalue_multiline, viewGroup, false);
            } else {
                return inflater.inflate(R.layout.template_keyvalue, viewGroup, false);
            }
        }

        @Override
        public void setContent(final Map.Entry<CharSequence, String> entry, final View view) {
            ((TextView) view.findViewById(R.id.key)).setText(entry.getKey());
            ((TextView) view.findViewById(R.id.value)).setText(entry.getValue());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityOptionsCompat options =
                            ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) v.getContext(), view.findViewById(R.id.value), "value");
                    KeyValueDetailActivity.start(v.getContext(), entry.getKey().toString(), entry.getValue(), options.toBundle());
                }
            });
        }

        @Override
        public void decorateViewWithZebra(View view, boolean hasZebra) {
            HoodUtil.setZebraToView(view, hasZebra);
        }
    }
}
