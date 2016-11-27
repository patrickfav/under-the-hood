package at.favre.lib.hood;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import at.favre.lib.hood.util.HoodUtil;

public class KeyValueDetailActivity extends AppCompatActivity {

    public static final String KEY_LABEL = "KEY_LABEL";
    public static final String KEY_VALUE = "KEY_VALUE";

    public static void start(Context context, String key, String value,@Nullable Bundle options) {
        Intent starter = new Intent(context, KeyValueDetailActivity.class);
        starter.putExtra(KEY_LABEL, key);
        starter.putExtra(KEY_VALUE, value);
        context.startActivity(starter,options);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        findViewById(R.id.copy_clipboard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HoodUtil.copyToClipboard(getIntent().getStringExtra(KEY_LABEL), getIntent().getStringExtra(KEY_VALUE), KeyValueDetailActivity.this);
            }
        });

        ((TextView) findViewById(R.id.key)).setText(getIntent().getStringExtra(KEY_LABEL));
        ((TextView) findViewById(R.id.value)).setText(getIntent().getStringExtra(KEY_VALUE));
    }
}
