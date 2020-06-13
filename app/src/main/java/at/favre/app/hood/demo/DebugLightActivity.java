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

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.favre.lib.hood.Hood;
import at.favre.lib.hood.extended.PopHoodActivity;
import at.favre.lib.hood.interfaces.Config;
import at.favre.lib.hood.interfaces.Page;
import at.favre.lib.hood.interfaces.Pages;
import at.favre.lib.hood.interfaces.actions.ButtonDefinition;
import at.favre.lib.hood.interfaces.actions.OnClickAction;
import at.favre.lib.hood.interfaces.values.SpinnerElement;
import at.favre.lib.hood.util.HoodUtil;
import at.favre.lib.hood.util.PageUtil;
import at.favre.lib.hood.util.defaults.DefaultButtonDefinitions;
import at.favre.lib.hood.util.defaults.DefaultConfigActions;
import at.favre.lib.hood.util.defaults.DefaultProperties;

public class DebugLightActivity extends PopHoodActivity {
    private static final String TAG = DebugLightActivity.class.getName();

    @NonNull
    @Override
    public Pages getPageData(@NonNull Pages pages) {
        Page firstPage = pages.addNewPage();

        firstPage.add(DefaultProperties.createSectionSourceControlAndCI(BuildConfig.GIT_REV, BuildConfig.GIT_BRANCH, BuildConfig.GIT_DATE, BuildConfig.BUILD_NUMBER, null, BuildConfig.BUILD_DATE));
        firstPage.add(DefaultProperties.createSectionAppVersionInfoFromBuildConfig(at.favre.lib.hood.BuildConfig.class));

        firstPage.add(DefaultProperties.createSectionBasicDeviceInfo());
        firstPage.add(DefaultProperties.createDetailedDeviceInfo(this));

        firstPage.add(Hood.get().createSwitchEntry(DefaultConfigActions.getBoolSharedPreferencesConfigAction(getPreferences(MODE_PRIVATE), "KEY_TEST", false)));
        firstPage.add(Hood.get().createSwitchEntry(DefaultConfigActions.getBoolSharedPreferencesConfigAction(getPreferences(MODE_PRIVATE), "KEY_TEST2", false)));
        firstPage.add(Hood.get().createSwitchEntry(DefaultConfigActions.getBoolSharedPreferencesConfigAction(getPreferences(MODE_PRIVATE), "KEY_TEST3", "a debug feature", false)));

        firstPage.add(Hood.get().createSpinnerEntry(DefaultConfigActions.getDefaultSharedPrefBackedSpinnerAction(null, getPreferences(MODE_PRIVATE), "W_BACKEND_KEY", null, getBackendElements())));

        firstPage.add(DefaultProperties.createSectionTelephonyManger(this));
        firstPage.add(DefaultProperties.createSectionBatteryInfo(this));

        PageUtil.addHeader(firstPage, "Misc Actions");
        PageUtil.addAction(firstPage, DefaultButtonDefinitions.getAppInfoAction());
        PageUtil.addAction(firstPage, DefaultButtonDefinitions.getCrashAction(), DefaultButtonDefinitions.getUninstallAction());
        PageUtil.addAction(firstPage, DefaultButtonDefinitions.getKillProcessAction(this), DefaultButtonDefinitions.getClearAppDataAction());
        PageUtil.addAction(firstPage, HoodUtil.getConditionally(DefaultButtonDefinitions.getKillProcessAction(this), at.favre.lib.hood.BuildConfig.DEBUG));

        PageUtil.addHeader(firstPage, "System Features");
        Map<CharSequence, String> systemFeatureMap = new HashMap<>();
        systemFeatureMap.put("hasHce", "android.hardware.nfc.hce");
        systemFeatureMap.put("hasCamera", "android.hardware.camera");
        systemFeatureMap.put("hasWebview", "android.software.webview");

        PageUtil.addAction(firstPage, new ButtonDefinition("Test Loading", new OnClickAction() {
            @Override
            public void onClick(final View view, Map.Entry<CharSequence, String> value) {
                view.setEnabled(false);
                getDebugView().setProgressBarVisible(true);
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.setEnabled(true);
                        getDebugView().setProgressBarVisible(false);
                    }
                }, 3000);
            }
        }));

        firstPage.add(DefaultProperties.createSystemFeatureInfo(this, systemFeatureMap));

        firstPage.add(DefaultProperties.createSectionConnectivityStatusInfo(this));

        logDataMap(pages);
        return pages;
    }

    private void logDataMap(Pages pages) {
        Map<String, String> map = pages.createDataMap();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            Log.v(TAG, entry.getKey() + " - " + entry.getValue());
        }
    }

    private List<SpinnerElement> getBackendElements() {
        List<SpinnerElement> elements = new ArrayList<>();
        elements.add(new SpinnerElement.Default("1", "dev.backend.com"));
        elements.add(new SpinnerElement.Default("2", "dev2.backend.com"));
        elements.add(new SpinnerElement.Default("3", "dev3.backend.com"));
        elements.add(new SpinnerElement.Default("4", "stage.backend.com"));
        elements.add(new SpinnerElement.Default("5", "prod.backend.com"));
        return elements;
    }

    @NonNull
    @Override
    public Config getConfig() {
        return Config.newBuilder().setLogTag(TAG).setAutoLog(false).setAutoRefresh(true).build();
    }
}
