package at.favre.lib.hood.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import at.favre.lib.hood.R;
import at.favre.lib.hood.util.HoodUtil;

/**
 * Dialogs used as detail view in {@link at.favre.lib.hood.page.entries.KeyValueEntry}. See
 * {@link at.favre.lib.hood.page.entries.KeyValueEntry.DialogClickAction}
 */
public class KeyValueDetailDialogs {
    private static final String TAG = KeyValueDetailDialogs.class.getName();

    /**
     * DialogFragment Wrapper for the dialog
     */
    public static class DialogFragmentWrapper extends DialogFragment {
        public static DialogFragmentWrapper newInstance(CharSequence key, String value) {
            DialogFragmentWrapper frag = new DialogFragmentWrapper();
            Bundle args = new Bundle();
            args.putString("key", String.valueOf(key));
            args.putString("value", value);
            frag.setArguments(args);
            return frag;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new CustomDialog(getActivity(), getArguments().getString("key"), getArguments().getString("value"));
        }
    }

    /**
     * Custom view dialog
     */
    public static class CustomDialog extends Dialog {
        private CharSequence key;
        private String value;

        public CustomDialog(Context context, CharSequence key, String value) {
            super(new ContextThemeWrapper(context, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth));

            this.key = key;
            this.value = value;
            setup();
        }

        private void setup() {
            setContentView(R.layout.hoodlib_dialog_detail);
            setTitle(key);
            ((TextView) findViewById(R.id.key)).setText(key);
            ((TextView) findViewById(R.id.value)).setText(value);
            findViewById(R.id.btn_copy_clipboard).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HoodUtil.copyToClipboard(String.valueOf(key), value, getContext());
                    Toast.makeText(getContext(), R.string.hood_toast_copied, Toast.LENGTH_SHORT).show();
                }
            });
            findViewById(R.id.btn_log).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.w(TAG, key + "\n" + value);
                    Toast.makeText(getContext(), R.string.hood_toast_logged, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * Basic, nativate styled dialog
     */
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
                    Log.w(TAG, key + "\n" + value);
                    Toast.makeText(getContext(), R.string.hood_toast_logged, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
