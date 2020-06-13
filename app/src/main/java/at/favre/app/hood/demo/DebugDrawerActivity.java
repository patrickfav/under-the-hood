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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.favre.app.hood.demo.databinding.ActivityDebugdrawerBinding;
import at.favre.lib.hood.Hood;
import at.favre.lib.hood.interfaces.Config;
import at.favre.lib.hood.interfaces.Page;
import at.favre.lib.hood.interfaces.Pages;
import at.favre.lib.hood.interfaces.actions.ButtonDefinition;
import at.favre.lib.hood.interfaces.actions.OnClickAction;
import at.favre.lib.hood.interfaces.values.SpinnerElement;
import at.favre.lib.hood.util.Backend;
import at.favre.lib.hood.util.PackageInfoAssembler;
import at.favre.lib.hood.util.PageUtil;
import at.favre.lib.hood.util.defaults.DefaultButtonDefinitions;
import at.favre.lib.hood.util.defaults.DefaultConfigActions;
import at.favre.lib.hood.util.defaults.DefaultProperties;
import at.favre.lib.hood.view.HoodController;

public class DebugDrawerActivity extends AppCompatActivity implements HoodController {
    private static final int DRAWER_POSITION = GravityCompat.END;

    private ActivityDebugdrawerBinding binding;

    public static void start(Context context) {
        Intent starter = new Intent(context, DebugDrawerActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_debugdrawer);

        binding.debugView.setPageData(createPages());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        findViewById(R.id.toggle_drawer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.drawerLayout.openDrawer(DRAWER_POSITION);
            }
        });
        binding.drawerLayout.setOnTouchListener(binding.debugView.getTouchInterceptorListener());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    public Pages createPages() {
        Pages pages = Hood.get().createPages(Config.newBuilder().setShowHighlightContent(false).build());
        Page firstPage = pages.addNewPage("Debug Info");
        firstPage.add(DefaultProperties.createSectionAppVersionInfoFromBuildConfig(at.favre.lib.hood.BuildConfig.class));
        firstPage.add(DefaultProperties.createSectionBasicDeviceInfo());
        firstPage.add(DefaultProperties.createSectionConnectivityStatusInfo(this));
        firstPage.add(new PackageInfoAssembler(PackageInfoAssembler.Type.APK_INSTALL_INFO, PackageInfoAssembler.Type.PERMISSIONS, PackageInfoAssembler.Type.SIGNATURE).createSection(this, true));

        Page secondPage = pages.addNewPage("Debug Features");
        PageUtil.addHeader(secondPage, "System Features");
        Map<CharSequence, String> systemFeatureMap = new HashMap<>();
        systemFeatureMap.put("hasHce", "android.hardware.nfc.hce");
        systemFeatureMap.put("hasCamera", "android.hardware.camera");
        systemFeatureMap.put("hasWebview", "android.software.webview");
        secondPage.add(DefaultProperties.createSystemFeatureInfo(this, systemFeatureMap));

        secondPage.add(Hood.get().createHeaderEntry("Debug Config"));
        secondPage.add(Hood.get().createSwitchEntry(DefaultConfigActions.getBoolSharedPreferencesConfigAction(getPreferences(MODE_PRIVATE), "KEY_TEST", "Enable debug feat#1", false)));
        secondPage.add(Hood.get().createSwitchEntry(DefaultConfigActions.getBoolSharedPreferencesConfigAction(getPreferences(MODE_PRIVATE), "KEY_TEST2", "Enable debug feat#2", false)));
        secondPage.add(Hood.get().createSwitchEntry(DefaultConfigActions.getBoolSharedPreferencesConfigAction(getPreferences(MODE_PRIVATE), "KEY_TEST3", "Enable debug feat#3", false)));
        secondPage.add(Hood.get().createSpinnerEntry(DefaultConfigActions.getDefaultSharedPrefBackedSpinnerAction(null, getPreferences(MODE_PRIVATE), "BACKEND_ID", null, getBackendElements())));

        PageUtil.addAction(secondPage, new ButtonDefinition("Test Loading", new OnClickAction() {
            @Override
            public void onClick(final View view, Map.Entry<CharSequence, String> value) {
                view.setEnabled(false);
                binding.debugView.setProgressBarVisible(true);
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.setEnabled(true);
                        binding.debugView.setProgressBarVisible(false);
                    }
                }, 3000);
            }
        }));
        secondPage.add(Hood.get().createActionEntry(DefaultButtonDefinitions.getCrashAction()));
        secondPage.add(Hood.get().createActionEntry(DefaultButtonDefinitions.getKillProcessAction(this), DefaultButtonDefinitions.getClearAppDataAction()));
        secondPage.add(Hood.get().createActionEntry(DefaultButtonDefinitions.getKillProcessAction(this)));

        return pages;
    }

    private List<SpinnerElement> getBackendElements() {
        List<SpinnerElement> elements = new ArrayList<>();
        elements.add(new Backend(1, "dev.backend.com", 443));
        elements.add(new Backend(2, "dev2.backend.com", 80));
        elements.add(new Backend(3, "dev3.backend.com", 80));
        elements.add(new Backend(4, "stage.backend.com", 443));
        elements.add(new Backend(5, "prod.backend.com", 443));
        return elements;
    }

    @NonNull
    @Override
    public Pages getCurrentPagesFromThisView() {
        return binding.debugView.getPages();
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(DRAWER_POSITION)) {
            binding.drawerLayout.closeDrawer(DRAWER_POSITION, true);
        } else {
            super.onBackPressed();
        }
    }
}
