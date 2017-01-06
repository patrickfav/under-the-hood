package at.favre.app.hood.demo;

import android.support.annotation.NonNull;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import at.favre.lib.hood.BuildConfig;
import at.favre.lib.hood.Hood;
import at.favre.lib.hood.defaults.DefaultActions;
import at.favre.lib.hood.defaults.DefaultConfigActions;
import at.favre.lib.hood.defaults.DefaultProperties;
import at.favre.lib.hood.defaults.misc.Backend;
import at.favre.lib.hood.extended.PopHoodActivity;
import at.favre.lib.hood.interfaces.Config;
import at.favre.lib.hood.interfaces.Page;
import at.favre.lib.hood.interfaces.Pages;
import at.favre.lib.hood.interfaces.actions.ButtonAction;
import at.favre.lib.hood.interfaces.values.SpinnerElement;
import at.favre.lib.hood.util.HoodUtil;
import at.favre.lib.hood.util.PackageInfoAssembler;
import at.favre.lib.hood.util.PageUtil;

public class DebugDarkActivity extends PopHoodActivity {
    private static final String TAG = DebugDarkActivity.class.getName();

    @NonNull
    @Override
    public Pages getPageData(@NonNull Pages pages) {
        Page firstPage = pages.addNewPage("General");

        firstPage.add(DefaultProperties.createSectionAppVersionInfoFromBuildConfig(BuildConfig.class));

        PageUtil.addHeader(firstPage, "Debug Config");
        firstPage.add(Hood.createSpinnerEnry(DefaultConfigActions.getDefaultSharedPrefBackedSpinnerAction("Backend", getPreferences(MODE_PRIVATE), "BACKEND_ID", null, getBackendElements())));
        firstPage.add(Hood.createSwitchEntry(DefaultConfigActions.getBoolSharedPreferencesConfigAction(getPreferences(MODE_PRIVATE), "KEY_TEST", "Enable debug feat#1", false)));
        firstPage.add(Hood.createSwitchEntry(DefaultConfigActions.getBoolSharedPreferencesConfigAction(getPreferences(MODE_PRIVATE), "KEY_TEST2", "Enable debug feat#2", false)));
        firstPage.add(Hood.createSwitchEntry(DefaultConfigActions.getBoolSharedPreferencesConfigAction(getPreferences(MODE_PRIVATE), "KEY_TEST3", "Enable debug feat#3", false)));

        firstPage.add(DefaultProperties.createSectionBasicDeviceInfo());
        firstPage.add(DefaultProperties.createDetailedDeviceInfo(this));

        firstPage.add(Hood.createPropertyEntry("MultiLine Test", "I am displaying text in a textview that appears to\nbe too long to fit into one screen. \nI need to make my TextView scrollable. How can i do\nthat? Here is the code\nbe too long to fit into one screen. \nI need to make my TextView scrollable. How can i do\nthat? Here is the code\ne too long to fit into one screen. \nI need to make my TextView scrollable. How can i do\nthat? Here is the code", true));

        firstPage.add(Hood.createHeaderEntry("Misc Action", true));
        PageUtil.addAction(firstPage, new ButtonAction("Test Loading", new View.OnClickListener() {
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

        PageUtil.addHeader(firstPage, "Lib BuildConfig");
        firstPage.add(DefaultProperties.createStaticFieldsInfo(BuildConfig.class));
        firstPage.add(new PackageInfoAssembler(PackageInfoAssembler.Type.USES_FEATURE, PackageInfoAssembler.Type.PERMISSIONS).createSection(this, true));
        PageUtil.addHeader(firstPage, "Property File");
        firstPage.add(DefaultProperties.createPropertiesEntries(getTestProperties()));

        firstPage.add(DefaultProperties.createSectionConnectivityStatusInfo(this));

        PageUtil.addHeader(firstPage, "System Features");
        Map<CharSequence, String> systemFeatureMap = new HashMap<>();
        systemFeatureMap.put("hasHce", "android.hardware.nfc.hce");
        systemFeatureMap.put("hasCamera", "android.hardware.camera");
        systemFeatureMap.put("hasWebview", "android.software.webview");
        firstPage.add(DefaultProperties.createSystemFeatureInfo(this, systemFeatureMap));

        firstPage.add(DefaultProperties.createInternalProcessDebugInfo(this));
        firstPage.add(DefaultProperties.createSectionBasicDeviceInfo());
        firstPage.add(DefaultProperties.createDetailedDeviceInfo(this));
        firstPage.add(DefaultProperties.createSectionAppVersionInfoFromBuildConfig(BuildConfig.class));
        firstPage.add(new PackageInfoAssembler(PackageInfoAssembler.Type.SIGNATURE).createSection(this, false));
        firstPage.add(DefaultProperties.createSectionTelephonyManger(this));
        firstPage.add(Hood.createHeaderEntry("Settings", true));

        PageUtil.addAction(firstPage, DefaultActions.getGlobalSettingsAction(this), DefaultActions.getNfcSettingsAction(this));
        PageUtil.addAction(firstPage, DefaultActions.getNfcPaymentSettingsAction(this), DefaultActions.getDevSettingsAction(this));
        PageUtil.addAction(firstPage, DefaultActions.getDateSettingsAction(this), DefaultActions.getAirplaneModeSettingsAction(this));
        PageUtil.addAction(firstPage, DefaultActions.getSetLockScreenAction(this), DefaultActions.getDeviceInfoSettingsAction(this));
        PageUtil.addAction(firstPage, DefaultActions.getBattSaverSettingsAction(this), DefaultActions.getDisplaySettingsAction(this));
        PageUtil.addAction(firstPage, DefaultActions.getInputModeSettingsAction(this), DefaultActions.getStorageSettingsAction(this));
        PageUtil.addAction(firstPage, DefaultActions.getSecuritySettingsAction(this), DefaultActions.getInstalledAppSettings(this));

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
