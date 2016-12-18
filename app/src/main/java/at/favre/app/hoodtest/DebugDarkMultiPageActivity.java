package at.favre.app.hoodtest;

import android.support.annotation.NonNull;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import at.favre.lib.hood.BuildConfig;
import at.favre.lib.hood.defaults.DefaultActions;
import at.favre.lib.hood.defaults.DefaultConfigActions;
import at.favre.lib.hood.defaults.DefaultProperties;
import at.favre.lib.hood.defaults.misc.Backend;
import at.favre.lib.hood.extended.PopHoodActivity;
import at.favre.lib.hood.page.Config;
import at.favre.lib.hood.page.Page;
import at.favre.lib.hood.page.Pages;
import at.favre.lib.hood.page.entries.ActionEntry;
import at.favre.lib.hood.page.entries.ConfigBoolEntry;
import at.favre.lib.hood.page.entries.ConfigSpinnerEntry;
import at.favre.lib.hood.page.entries.HeaderEntry;
import at.favre.lib.hood.page.entries.KeyValueEntry;
import at.favre.lib.hood.page.values.SpinnerElement;
import at.favre.lib.hood.util.HoodUtil;
import at.favre.lib.hood.util.PackageInfoAssembler;
import at.favre.lib.hood.util.PageUtil;

public class DebugDarkMultiPageActivity extends PopHoodActivity {
    private static final String TAG = DebugDarkMultiPageActivity.class.getName();

    @NonNull
    @Override
    public Pages getPageData(@NonNull Pages pages) {
        Page firstPage = pages.addNewPage("General");

        firstPage.add(DefaultProperties.createSectionAppVersionInfoFromBuildConfig(at.favre.lib.hood.BuildConfig.class));
        firstPage.add(new PackageInfoAssembler(PackageInfoAssembler.Type.SIGNATURE).createSection(this, false));

        PageUtil.addTitle(firstPage, "Debug Config");
        firstPage.add(new ConfigSpinnerEntry(DefaultConfigActions.getDefaultSharedPrefBackedSpinnerAction("Backend", getPreferences(MODE_PRIVATE), "BACKEND_ID", null, getBackendElements())));
        firstPage.add(new ConfigBoolEntry(DefaultConfigActions.getBoolSharedPreferencesConfigAction(getPreferences(MODE_PRIVATE), "KEY_TEST", "Enable debug feat#1", false)));
        firstPage.add(new ConfigBoolEntry(DefaultConfigActions.getBoolSharedPreferencesConfigAction(getPreferences(MODE_PRIVATE), "KEY_TEST2", "Enable debug feat#2", false)));
        firstPage.add(new ConfigBoolEntry(DefaultConfigActions.getBoolSharedPreferencesConfigAction(getPreferences(MODE_PRIVATE), "KEY_TEST3", "Enable debug feat#3", false)));

        firstPage.add(DefaultProperties.createSectionBasicDeviceInfo());
        firstPage.add(DefaultProperties.createDetailedDeviceInfo(this));

        firstPage.add(new KeyValueEntry("MultiLine Test", "I am displaying text in a textview that appears to\nbe too long to fit into one screen. \nI need to make my TextView scrollable. How can i do\nthat? Here is the code\nbe too long to fit into one screen. \nI need to make my TextView scrollable. How can i do\nthat? Here is the code\ne too long to fit into one screen. \nI need to make my TextView scrollable. How can i do\nthat? Here is the code", true));

        firstPage.add(new HeaderEntry("Misc Action", true));
        PageUtil.addAction(firstPage, new ActionEntry.Action("Test Loading", new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
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
        PageUtil.addAction(firstPage, DefaultActions.getCrashAction());
        PageUtil.addAction(firstPage, DefaultActions.getKillProcessAction(this), DefaultActions.getClearAppDataAction(this));
        PageUtil.addAction(firstPage, HoodUtil.getConditionally(DefaultActions.getKillProcessAction(this), BuildConfig.DEBUG));

        PageUtil.addTitle(firstPage, "Lib BuildConfig");
        firstPage.add(DefaultProperties.createStaticFieldsInfo(BuildConfig.class));
        firstPage.add(new PackageInfoAssembler(PackageInfoAssembler.Type.USES_FEATURE, PackageInfoAssembler.Type.PERMISSIONS).createSection(this, true));

        PageUtil.addTitle(firstPage, "Property File");
        firstPage.add(DefaultProperties.createPropertiesEntries(getTestProperties()));

        firstPage.add(DefaultProperties.createConnectivityStatusInfo(this, true));

        Page secondPage = pages.addNewPage("Details");
        PageUtil.addTitle(secondPage, "System Features");
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
        secondPage.add(new HeaderEntry("Settings", true));

        PageUtil.addAction(secondPage, DefaultActions.getGlobalSettingsAction(this), DefaultActions.getNfcSettingsAction(this));
        PageUtil.addAction(secondPage, DefaultActions.getNfcPaymentSettingsAction(this), DefaultActions.getDevSettingsAction(this));
        PageUtil.addAction(secondPage, DefaultActions.getDateSettingsAction(this), DefaultActions.getAirplaneModeSettingsAction(this));
        PageUtil.addAction(secondPage, DefaultActions.getSetLockScreenAction(this), DefaultActions.getDeviceInfoSettingsAction(this));
        PageUtil.addAction(secondPage, DefaultActions.getBattSaverSettingsAction(this), DefaultActions.getDisplaySettingsAction(this));
        PageUtil.addAction(secondPage, DefaultActions.getInputModeSettingsAction(this), DefaultActions.getStorageSettingsAction(this));
        PageUtil.addAction(secondPage, DefaultActions.getSecuritySettingsAction(this), DefaultActions.getInstalledAppSettings(this));

        secondPage.add(new PackageInfoAssembler(PackageInfoAssembler.Type.APK_VERSION_INFO, PackageInfoAssembler.Type.APK_INSTALL_INFO).createSection(this, true));

        Page thirdPage = pages.addNewPage("Package Manager");
        thirdPage.add(new PackageInfoAssembler(PackageInfoAssembler.Type.APK_VERSION_INFO, PackageInfoAssembler.Type.APK_INSTALL_INFO, PackageInfoAssembler.Type.ACTIVITIES, PackageInfoAssembler.Type.PERMISSIONS, PackageInfoAssembler.Type.PROVIDER, PackageInfoAssembler.Type.RECEIVERS, PackageInfoAssembler.Type.SERVICES, PackageInfoAssembler.Type.SIGNATURE, PackageInfoAssembler.Type.USES_FEATURE).createSection(this, true));

        return pages;
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
        return new Config.Builder()
                .setShowHighlightContent(false)
                .setLogTag(TAG).build();
    }
}
