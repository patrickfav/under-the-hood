package at.favre.app.hood.demo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import at.favre.app.hood.demo.databinding.ActivityMainBinding;
import at.favre.lib.hood.extended.PopHoodActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setSupportActionBar(binding.toolbar);

        binding.btnStartDarkMultiPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopHoodActivity.start(MainActivity.this, true, DebugDarkMultiPageActivity.class);
            }
        });
        binding.btnStartBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopHoodActivity.start(MainActivity.this, false, DebugDarkBackgroundValuesActivity.class);
            }
        });
        binding.btnStartLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopHoodActivity.start(MainActivity.this, true, DebugLightActivity.class);
            }
        });

        binding.btnStartDark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopHoodActivity.start(MainActivity.this, false, DebugDarkActivity.class);
            }
        });

        binding.btnStartDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DebugDrawerActivity.start(MainActivity.this);
            }
        });

        binding.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MoreActivity.start(MainActivity.this);
            }
        });
        Log.d(TAG, "Main Activity started - Test debug log");
    }
}
