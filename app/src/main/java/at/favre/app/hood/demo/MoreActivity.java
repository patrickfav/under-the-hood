package at.favre.app.hood.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import at.favre.lib.hood.Hood;
import at.favre.lib.hood.interfaces.actions.Stoppable;

public class MoreActivity extends AppCompatActivity {
    private Stoppable shakeStopper;

    public static void start(Context context) {
        Intent starter = new Intent(context, MoreActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        shakeStopper = Hood.ext().registerShakeToOpenDebugActivity(this.getApplicationContext(), DebugDarkMultiPageActivity.createIntent(this, DebugDarkMultiPageActivity.class));

        findViewById(R.id.btn_stop_shake).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.tv_try_shake).setVisibility(View.INVISIBLE);
                v.setEnabled(false);
                shakeStopper.stop();
            }
        });

        findViewById(R.id.tv_doubletap).setOnTouchListener(Hood.ext().createArbitraryTapListener(2, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DebugDarkActivity.start(MoreActivity.this, DebugDarkActivity.class);
            }
        }));

        findViewById(R.id.tv_tripletap).setOnTouchListener(Hood.ext().createArbitraryTapListener(3, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DebugLightActivity.start(MoreActivity.this, DebugLightActivity.class);
            }
        }));

    }
}
