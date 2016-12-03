package at.favre.lib.hood.page.entries;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;

import at.favre.lib.hood.R;
import at.favre.lib.hood.defaults.DefaultIntents;
import at.favre.lib.hood.page.PageEntry;
import at.favre.lib.hood.page.ViewTemplate;
import at.favre.lib.hood.page.values.DynamicValue;
import at.favre.lib.hood.util.HoodUtil;
import at.favre.lib.hood.views.KeyValueDetailDialog;

import static android.content.ContentValues.TAG;
import static at.favre.lib.hood.page.entries.ViewTypes.VIEWTYPE_KEYVALUE;
import static at.favre.lib.hood.page.entries.ViewTypes.VIEWTYPE_KEYVALUE_MULTILINE;

public class KeyValueEntry implements Comparator<KeyValueEntry>, PageEntry<Map.Entry<CharSequence, String>> {

    private Map.Entry<CharSequence, String> data;
    private final Template template;
    private final DynamicValue<String> value;

    public KeyValueEntry(CharSequence key, DynamicValue<String> value, OnClickAction action, boolean multiLine) {
        this.value = value;
        this.data = new AbstractMap.SimpleEntry<>(key, value.getValue());
        this.template = new Template(multiLine, action);
    }

    public KeyValueEntry(CharSequence key, DynamicValue<String> value, boolean multiLine) {
        this(key, value, new DialogClickAction(), multiLine);
    }

    public KeyValueEntry(CharSequence key, DynamicValue<String> value) {
        this(key, value, new DialogClickAction(), false);
    }

    public KeyValueEntry(CharSequence key, final String value, OnClickAction action, boolean multiLine) {
        this(key, new DynamicValue<String>() {
            @Override
            public String getValue() {
                return value;
            }
        }, action, multiLine);
    }

    public KeyValueEntry(CharSequence key, final String value, boolean multiLine) {
        this(key, new DynamicValue<String>() {
            @Override
            public String getValue() {
                return value;
            }
        }, new DialogClickAction(), multiLine);
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
        private final OnClickAction clickAction;

        public Template(boolean multiLine, OnClickAction clickAction) {
            this.multiLine = multiLine;
            this.clickAction = clickAction;
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
            if (clickAction != null) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickAction.onClick(v, entry);
                    }
                });
                view.setClickable(true);
            } else {
                view.setOnClickListener(null);
                view.setClickable(false);
            }
        }

        @Override
        public void decorateViewWithZebra(View view, boolean hasZebra) {
            HoodUtil.setZebraToView(view, hasZebra);
        }
    }

    public interface OnClickAction {
        void onClick(View v, Map.Entry<CharSequence, String> value);
    }

    public static class ToastClickAction implements OnClickAction {
        @Override
        public void onClick(View v, Map.Entry<CharSequence, String> value) {
            Toast.makeText(v.getContext(), value.getKey() + "\n" + value.getValue(), Toast.LENGTH_SHORT).show();
        }
    }

    public static class AskPermissionClickAction implements OnClickAction {
        private String androidPermissionName;
        private Activity activity;

        public AskPermissionClickAction(String androidPermissionName, Activity activity) {
            this.androidPermissionName = androidPermissionName;
            this.activity = activity;
        }

        @Override
        public void onClick(View v, Map.Entry<CharSequence, String> value) {
            Log.d(TAG, "check android permissions for " + androidPermissionName);
            if (ContextCompat.checkSelfPermission(v.getContext(), androidPermissionName) != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "permission not granted yet, show dialog");
                ActivityCompat.requestPermissions(activity, new String[]{androidPermissionName}, 2587);
            } else {
                Toast.makeText(activity, R.string.hood_toast_already_allowed, Toast.LENGTH_SHORT).show();
                v.getContext().startActivity(DefaultIntents.getAppInfoIntent(v.getContext()));
            }
        }
    }

    public static class DialogClickAction implements OnClickAction {
        @Override
        public void onClick(View v, Map.Entry<CharSequence, String> value) {
            new KeyValueDetailDialog(v.getContext(), value.getKey(), value.getValue()).show();
        }
    }

    public static class StartIntentAction implements OnClickAction {
        private final Intent intent;

        public StartIntentAction(Intent intent) {
            this.intent = intent;
        }

        @Override
        public void onClick(View v, Map.Entry<CharSequence, String> value) {
            v.getContext().startActivity(intent);
        }
    }
}
