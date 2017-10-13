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

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import at.favre.lib.hood.Hood;
import at.favre.lib.hood.extended.PopHoodActivity;
import at.favre.lib.hood.interfaces.Config;
import at.favre.lib.hood.interfaces.Page;
import at.favre.lib.hood.interfaces.Pages;
import at.favre.lib.hood.interfaces.actions.ButtonDefinition;
import at.favre.lib.hood.interfaces.actions.OnClickAction;
import at.favre.lib.hood.interfaces.values.SpinnerElement;
import at.favre.lib.hood.util.Backend;
import at.favre.lib.hood.util.HoodUtil;
import at.favre.lib.hood.util.PackageInfoAssembler;
import at.favre.lib.hood.util.PageUtil;
import at.favre.lib.hood.util.defaults.DefaultButtonDefinitions;
import at.favre.lib.hood.util.defaults.DefaultConfigActions;
import at.favre.lib.hood.util.defaults.DefaultProperties;

public class DebugDarkMultiPageActivity extends PopHoodActivity {
    private static final String TAG = DebugDarkMultiPageActivity.class.getName();

    @NonNull
    @Override
    public Pages getPageData(@NonNull Pages pages) {
        Page firstPage = pages.addNewPage("General");

        firstPage.add(DefaultProperties.createSectionAppVersionInfoFromBuildConfig(BuildConfig.class));
        firstPage.add(new PackageInfoAssembler(PackageInfoAssembler.Type.SIGNATURE).createSection(this, false));
        firstPage.add(DefaultProperties.createSectionSourceControlAndCI(BuildConfig.GIT_REV, BuildConfig.GIT_BRANCH, BuildConfig.GIT_DATE, BuildConfig.BUILD_NUMBER, null, BuildConfig.BUILD_DATE));

        PageUtil.addHeader(firstPage, "Debug Config");
        firstPage.add(Hood.get().createSpinnerEntry(DefaultConfigActions.getDefaultSharedPrefBackedSpinnerAction("Backend", getPreferences(MODE_PRIVATE), "BACKEND_ID", null, getBackendElements())));
        firstPage.add(Hood.get().createSwitchEntry(DefaultConfigActions.getBoolSharedPreferencesConfigAction(getPreferences(MODE_PRIVATE), "KEY_TEST", "Enable debug feat#1", false)));
        firstPage.add(Hood.get().createSwitchEntry(DefaultConfigActions.getBoolSharedPreferencesConfigAction(getPreferences(MODE_PRIVATE), "KEY_TEST2", "Enable debug feat#2", false)));
        firstPage.add(Hood.get().createSwitchEntry(DefaultConfigActions.getBoolSharedPreferencesConfigAction(getPreferences(MODE_PRIVATE), "KEY_TEST3", "Enable debug feat#3", false)));

        firstPage.add(DefaultProperties.createSectionBasicDeviceInfo());
        firstPage.add(DefaultProperties.createDetailedDeviceInfo(this));

        firstPage.add(Hood.get().createPropertyEntry("MultiLine Test", "I am displaying text in a textview that appears to\nbe too long to fit into one screen. \nI need to make my TextView scrollable. How can i do\nthat? Here is the code\nbe too long to fit into one screen. \nI need to make my TextView scrollable. How can i do\nthat? Here is the code\ne too long to fit into one screen. \nI need to make my TextView scrollable. How can i do\nthat? Here is the code", true));

        firstPage.add(Hood.get().createHeaderEntry("Misc Action"));
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

        PageUtil.addAction(firstPage, DefaultButtonDefinitions.getCrashAction());
        PageUtil.addAction(firstPage, DefaultButtonDefinitions.getKillProcessAction(this), DefaultButtonDefinitions.getClearAppDataAction());
        PageUtil.addAction(firstPage, HoodUtil.getConditionally(DefaultButtonDefinitions.getKillProcessAction(this), BuildConfig.DEBUG));

        PageUtil.addHeader(firstPage, "Lib BuildConfig");
        firstPage.add(DefaultProperties.createStaticFieldsInfo(at.favre.lib.hood.BuildConfig.class));
        firstPage.add(new PackageInfoAssembler(PackageInfoAssembler.Type.USES_FEATURE, PackageInfoAssembler.Type.PERMISSIONS).createSection(this, true));

        PageUtil.addHeader(firstPage, "Property File");
        firstPage.add(DefaultProperties.createPropertiesEntries(getTestProperties()));
        firstPage.add(Hood.get().createPropertyEntry("obfuscate-1", HoodUtil.obfuscateAndShorten(UUID.randomUUID().toString(), 7, '*')));
        firstPage.add(Hood.get().createPropertyEntry("obfuscate-2", HoodUtil.obfuscateAndShorten(UUID.randomUUID().toString(), 7, '*')));
        firstPage.add(Hood.get().createPropertyEntry("obfuscate-3", HoodUtil.obfuscateAndShorten(UUID.randomUUID().toString(), 7, '*')));
        firstPage.add(PackageInfoAssembler.createPmSignatureHashInfo(PackageInfoAssembler.getPackageInfo(this, PackageManager.GET_SIGNATURES), createSigRefMap()));

        firstPage.add(DefaultProperties.createSectionConnectivityStatusInfo(this));

        Page secondPage = pages.addNewPage("Details");
        PageUtil.addHeader(secondPage, "System Features");
        Map<CharSequence, String> systemFeatureMap = new HashMap<>();
        systemFeatureMap.put("hasHce", "android.hardware.nfc.hce");
        systemFeatureMap.put("hasCamera", "android.hardware.camera");
        systemFeatureMap.put("hasWebview", "android.software.webview");
        secondPage.add(DefaultProperties.createSystemFeatureInfo(this, systemFeatureMap));

        secondPage.add(DefaultProperties.createInternalProcessDebugInfo(this));
        secondPage.add(DefaultProperties.createSectionBasicDeviceInfo());
        secondPage.add(DefaultProperties.createDetailedDeviceInfo(this));
        secondPage.add(DefaultProperties.createSectionAppVersionInfoFromBuildConfig(BuildConfig.class));
        secondPage.add(DefaultProperties.createSectionTelephonyManger(this));
        secondPage.add(DefaultProperties.createSectionStrictMode());
        secondPage.add(DefaultProperties.createSectionBatteryInfo(this));
        secondPage.add(DefaultProperties.createSectionAndroidDebugSettings(this));
        secondPage.add(Hood.get().createHeaderEntry("Settings"));

        PageUtil.addAction(secondPage, DefaultButtonDefinitions.getGlobalSettingsAction(), DefaultButtonDefinitions.getNfcSettingsAction());
        secondPage.add(Hood.get().createSpacer());
        secondPage.add(Hood.get().createSpacer());
        PageUtil.addAction(secondPage, DefaultButtonDefinitions.getNfcPaymentSettingsAction(), DefaultButtonDefinitions.getDevSettingsAction());
        PageUtil.addAction(secondPage, DefaultButtonDefinitions.getDateSettingsAction(), DefaultButtonDefinitions.getAirplaneModeSettingsAction());
        PageUtil.addAction(secondPage, DefaultButtonDefinitions.getSetLockScreenAction(), DefaultButtonDefinitions.getDeviceInfoSettingsAction());
        PageUtil.addAction(secondPage, DefaultButtonDefinitions.getBatterySaverSettingsAction(), DefaultButtonDefinitions.getDisplaySettingsAction());
        PageUtil.addAction(secondPage, DefaultButtonDefinitions.getInputModeSettingsAction(), DefaultButtonDefinitions.getStorageSettingsAction());
        PageUtil.addAction(secondPage, DefaultButtonDefinitions.getSecuritySettingsAction(), DefaultButtonDefinitions.getInstalledAppSettings());
        PageUtil.addAction(secondPage, DefaultButtonDefinitions.getPlayStoreLink(BuildConfig.APPLICATION_ID));

        secondPage.add(new PackageInfoAssembler(PackageInfoAssembler.Type.APK_VERSION_INFO, PackageInfoAssembler.Type.APK_INSTALL_INFO).createSection(this, true));

        Page thirdPage = pages.addNewPage("Package Manager");
        thirdPage.add(new PackageInfoAssembler(PackageInfoAssembler.Type.USES_FEATURE, PackageInfoAssembler.Type.APK_VERSION_INFO,
                PackageInfoAssembler.Type.APK_INSTALL_INFO, PackageInfoAssembler.Type.ACTIVITIES,
                PackageInfoAssembler.Type.SIGNATURE, PackageInfoAssembler.Type.PERMISSIONS, PackageInfoAssembler.Type.PROVIDER,
                PackageInfoAssembler.Type.RECEIVERS, PackageInfoAssembler.Type.SERVICES, PackageInfoAssembler.Type.ACTIVITIES).createSection(this, true));

        return pages;
    }

    private Map<String, String> createSigRefMap() {
        Map<String, String> refMap = new HashMap<>(2);
        refMap.put("debug", "da33c57");
        refMap.put("release", "1b9803b");
        return refMap;
    }

    private Properties getTestProperties() {
        Properties p = new Properties();
        p.setProperty("propKey1", "value1");
        p.setProperty("propKey2", "value2");
        p.setProperty("propKey3", "value3");
        return p;
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
    public Config getConfig() {
        return Config.newBuilder()
                .setShowHighlightContent(false)
                .setLogTag(TAG).build();
    }
}
