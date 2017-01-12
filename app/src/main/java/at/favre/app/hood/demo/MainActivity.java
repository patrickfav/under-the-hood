package at.favre.app.hood.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.btn_start_dark_multi_page).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DebugDarkMultiPageActivity.start(MainActivity.this, DebugDarkMultiPageActivity.class);
            }
        });

        findViewById(R.id.btn_start_light).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DebugLightActivity.start(MainActivity.this, DebugLightActivity.class);
            }
        });

        findViewById(R.id.btn_start_dark).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DebugDarkActivity.start(MainActivity.this, DebugDarkActivity.class);
            }
        });

        findViewById(R.id.btn_start_drawer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DebugDrawerActivity.start(MainActivity.this);
            }
        });

        findViewById(R.id.btn_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MoreActivity.start(MainActivity.this);
            }
        });
        Log.d(TAG, "Main Activity started - Test debug log");
    }
}
