package at.favre.lib.hood.views;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import at.favre.lib.hood.R;
import at.favre.lib.hood.util.HoodUtil;


public class KeyValueDetailDialog extends Dialog {
    private CharSequence key;
    private String value;

    public KeyValueDetailDialog(Context context, CharSequence key, String value) {
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
