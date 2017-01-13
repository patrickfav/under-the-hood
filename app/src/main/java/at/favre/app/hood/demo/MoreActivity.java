package at.favre.app.hood.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import at.favre.lib.hood.Hood;
import at.favre.lib.hood.interfaces.actions.ManagerControl;

public class MoreActivity extends AppCompatActivity {
    private ManagerControl shakeControl;

    public static void start(Context context) {
        Intent starter = new Intent(context, MoreActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        shakeControl = Hood.ext().registerShakeToOpenDebugActivity(this.getApplicationContext(), DebugDarkMultiPageActivity.createIntent(this, DebugDarkMultiPageActivity.class));

        findViewById(R.id.btn_stop_shake).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.tv_try_shake).setVisibility(View.INVISIBLE);
                v.setEnabled(false);
                shakeControl.stop();
            }
        });

        findViewById(R.id.tv_doubletap).setOnTouchListener(Hood.ext().createArbitraryTapListener(2, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DebugDarkActivity.start(MoreActivity.this, DebugDarkActivity.class);
            }
        }));

        findViewById(R.id.tv_doubletap).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(MoreActivity.this, "LOOOONG CLIICK", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        findViewById(R.id.tv_tripletap).setOnTouchListener(Hood.ext().createArbitraryTapListener(3, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DebugLightActivity.start(MoreActivity.this, DebugLightActivity.class);
            }
        }));

    }

    @Override
    protected void onResume() {
        super.onResume();
        shakeControl.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        shakeControl.stop();
    }
}
