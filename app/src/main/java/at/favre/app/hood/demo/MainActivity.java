/*
 *  Copyright 2016 Patrick Favre-Bulle
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package at.favre.app.hood.demo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import at.favre.app.hood.demo.databinding.ActivityMainBinding;
import at.favre.lib.hood.Hood;
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
                PopHoodActivity.start(MainActivity.this, DebugDarkMultiPageActivity.class);
            }
        });
        binding.btnStartBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopHoodActivity.start(MainActivity.this, DebugDarkBackgroundValuesActivity.class);
            }
        });
        binding.btnStartLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopHoodActivity.start(MainActivity.this, DebugLightActivity.class);
            }
        });
        binding.btnStartDark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopHoodActivity.start(MainActivity.this, DebugDarkActivity.class);
            }
        });
        binding.btnStartCustomTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopHoodActivity.start(MainActivity.this, DebugCustomThemeActivity.class);
            }
        });
        binding.btnStartPerf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopHoodActivity.start(MainActivity.this, DebugPerformanceTestActivity.class);
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

        if (!Hood.isLibEnabled()) {
            findViewById(R.id.main_content).setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.disabled_background, null));
            setTitle(getTitle() + " (no-op)");
        }
    }
}
