package at.favre.lib.hood.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import at.favre.lib.hood.R;
import at.favre.lib.hood.util.HoodUtil;


public class KeyValueDetailDialog {
    private static final String TAG = KeyValueDetailDialog.class.getName();

    public static class CustomDetailDialog extends Dialog {
        private CharSequence key;
        private String value;

        public CustomDetailDialog(Context context, CharSequence key, String value) {
            super(context);
            this.key = key;
            this.value = value;
            setup();
        }

        private void setup() {
            setContentView(R.layout.dialog_detail);
            setTitle(key);
            ((TextView) findViewById(R.id.key)).setText(key);
            ((TextView) findViewById(R.id.value)).setText(value);
            findViewById(R.id.copy_clipboard).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HoodUtil.copyToClipboard(String.valueOf(key), value, getContext());
                    Toast.makeText(getContext(), R.string.hood_toast_copied, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public static class NativeDialog extends AlertDialog {

        private CharSequence key;
        private String value;

        public NativeDialog(Context context, CharSequence key, String value) {
            super(context);
            this.key = key;
            this.value = value;
            setup();
        }

        private void setup() {
            setTitle(key);
            setMessage(value);
            setButton(BUTTON_POSITIVE, "Copy", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    HoodUtil.copyToClipboard(String.valueOf(key), value, getContext());
                    Toast.makeText(getContext(), R.string.hood_toast_copied, Toast.LENGTH_SHORT).show();
                }
            });
            setButton(BUTTON_NEUTRAL, "Close", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            setButton(BUTTON_NEGATIVE, "Log", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.w(TAG, key + "=" + value);
                    Toast.makeText(getContext(), R.string.hood_toast_logged, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
