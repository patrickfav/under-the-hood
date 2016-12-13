package at.favre.app.hoodtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import at.favre.lib.hood.BuildConfig;
import at.favre.lib.hood.defaults.DefaultActions;
import at.favre.lib.hood.defaults.DefaultConfigActions;
import at.favre.lib.hood.defaults.DefaultProperties;
import at.favre.lib.hood.extended.PopHoodActivity;
import at.favre.lib.hood.page.DebugPage;
import at.favre.lib.hood.page.Page;
import at.favre.lib.hood.page.entries.ConfigBoolEntry;
import at.favre.lib.hood.page.entries.ConfigSpinnerEntry;
import at.favre.lib.hood.page.entries.KeyValueEntry;
import at.favre.lib.hood.page.values.SpinnerElement;
import at.favre.lib.hood.page.values.SpinnerValue;
import at.favre.lib.hood.util.HoodUtil;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_start_dark).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DebugDarkActivity.start(MainActivity.this, DebugDarkActivity.class);
            }
        });

        findViewById(R.id.btn_start_light).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DebugLightActivity.start(MainActivity.this, DebugLightActivity.class);
            }
        });

        findViewById(R.id.btn_start_drawer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DebugDrawerActivity.start(MainActivity.this);
            }
        });
    }
}
