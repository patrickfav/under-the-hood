package at.favre.lib.hood.views;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;

import at.favre.lib.hood.KeyValueDetailActivity;
import at.favre.lib.hood.R;

public class KeyValueEntry extends AbstractPageEntry<Map.Entry<CharSequence, String>> implements Comparator<KeyValueEntry> {
    public static final int VIEWTYPE_KEYVALUE = 1 << 16 + 1;

    private final Map.Entry<CharSequence, String> data;
    private final boolean isStatic;
    private final Template template;

    public KeyValueEntry(CharSequence key, String value, boolean isStatic) {
        this.data = new AbstractMap.SimpleEntry<>(key, value);
        this.isStatic = isStatic;
        template = new Template();
    }

    public KeyValueEntry(CharSequence key, String value) {
        this(key, value, true);
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
    public boolean isStaticContent() {
        return isStatic;
    }

    @Override
    public int compare(KeyValueEntry o1, KeyValueEntry o2) {
        return String.valueOf(o1.getValue().getKey()).compareTo(o2.getValue().getKey().toString());
    }

    private static class Template implements ViewTemplate<Map.Entry<CharSequence, String>> {
        @Override
        public int getViewType() {
            return VIEWTYPE_KEYVALUE;
        }

        @Override
        public View constructView(ViewGroup viewGroup, LayoutInflater inflater) {
            return inflater.inflate(R.layout.template_keyvalue, viewGroup, false);
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
                    KeyValueDetailActivity.start(v.getContext(), entry.getKey().toString(), entry.getValue(),options.toBundle());
                }
            });
        }

        @Override
        public void decorateViewWithZebra(View view, boolean hasZebra) {

            Drawable zebra = null;

            if (hasZebra) {
                zebra = new ColorDrawable(ContextCompat.getColor(view.getContext(), R.color.zebra_color));
            }

            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                view.findViewById(R.id.inner_wrapper).setBackgroundDrawable(zebra);
            } else {
                view.findViewById(R.id.inner_wrapper).setBackground(zebra);
            }
        }
    }
}
