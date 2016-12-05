package at.favre.app.hoodtest;

import android.view.View;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import at.favre.lib.hood.defaults.DefaultActions;
import at.favre.lib.hood.defaults.DefaultConfigActions;
import at.favre.lib.hood.defaults.DefaultProperties;
import at.favre.lib.hood.extended.PopHoodActivity;
import at.favre.lib.hood.page.DebugPage;
import at.favre.lib.hood.page.Page;
import at.favre.lib.hood.page.entries.ActionEntry;
import at.favre.lib.hood.page.entries.ConfigBoolEntry;
import at.favre.lib.hood.page.entries.ConfigSpinnerEntry;
import at.favre.lib.hood.page.entries.KeyValueEntry;
import at.favre.lib.hood.page.values.SpinnerElement;
import at.favre.lib.hood.page.values.SpinnerValue;
import at.favre.lib.hood.util.HoodUtil;

public class DebugDarkActivity extends PopHoodActivity {

    public Page getPageData() {
        DebugPage page = new DebugPage();

        page.addEntries(DefaultProperties.createAppVersionInfo(at.favre.lib.hood.BuildConfig.class, true));
        page.addEntries(DefaultProperties.createSignatureHashInfo(this));

        page.addEntries(DefaultProperties.createBasicDeviceInfo(true));
        page.addEntries(DefaultProperties.createDetailedDeviceInfo(this));

        page.addEntry(new KeyValueEntry("MultiLine Test", "I am displaying text in a textview that appears to\nbe too long to fit into one screen. \nI need to make my TextView scrollable. How can i do\nthat? Here is the code\n" +
                "be too long to fit into one screen. \n" +
                "I need to make my TextView scrollable. How can i do\n" +
                "that? Here is the code\n" +
                "be too long to fit into one screen. \n" +
                "I need to make my TextView scrollable. How can i do\n" +
                "that? Here is the code", true));
        page.addTitle("Debug Config");
        page.addEntry(new ConfigBoolEntry(DefaultConfigActions.getBoolSharedPreferencesConfigAction(getPreferences(MODE_PRIVATE), "KEY_TEST", false)));
        page.addEntry(new ConfigBoolEntry(DefaultConfigActions.getBoolSharedPreferencesConfigAction(getPreferences(MODE_PRIVATE), "KEY_TEST2", false)));
        page.addEntry(new ConfigBoolEntry(DefaultConfigActions.getBoolSharedPreferencesConfigAction(getPreferences(MODE_PRIVATE), "KEY_TEST3", "a debug feature", false)));

        page.addEntry(new ConfigSpinnerEntry(new ConfigSpinnerEntry.SingleSelectListConfigAction("Backend Selector", new SpinnerValue<List<SpinnerElement>, SpinnerElement>() {
            SpinnerElement backend = new SpinnerElement("2", "backend 2");

            @Override
            public SpinnerElement getValue() {
                return backend;
            }

            @Override
            public void onChange(SpinnerElement value) {
                backend = value;
            }

            @Override
            public List<SpinnerElement> getAlPossibleValues() {
                return Arrays.asList(new SpinnerElement("1", "backend 1"), new SpinnerElement("2", "backend 2"), new SpinnerElement("3", "backend 3"), new SpinnerElement("4", "backend 4"));
            }
        })));

        page.addEntry(new ConfigSpinnerEntry(new ConfigSpinnerEntry.SingleSelectListConfigAction(null, new SpinnerValue<List<SpinnerElement>, SpinnerElement>() {
            SpinnerElement backend;

            @Override
            public SpinnerElement getValue() {
                return backend;
            }

            @Override
            public void onChange(SpinnerElement value) {
                backend = value;
            }

            @Override
            public List<SpinnerElement> getAlPossibleValues() {
                return Arrays.asList(new SpinnerElement("1", "backend 1"), new SpinnerElement("2", "backend 2"), new SpinnerElement("3", "backend 3"), new SpinnerElement("4", "backend 4"));
            }
        })));

        page.addEntries(DefaultProperties.createTelephonyMangerInfo(this, true));

        page.addTitle("Misc Actions");
        page.addAction(new ActionEntry.Action("Test Loading", new View.OnClickListener() {
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
                }, 2000);
            }
        }));
        page.addAction(DefaultActions.getCrashAction(), DefaultActions.getUninstallAction(this));
        page.addAction(DefaultActions.getKillProcessAction(this), DefaultActions.getClearAppDataAction(this));
        page.addAction(HoodUtil.getConditionally(DefaultActions.getKillProcessAction(this), at.favre.lib.hood.BuildConfig.DEBUG));

        page.addTitle("Lib BuildConfig");
        page.addEntries(DefaultProperties.createStaticFieldsInfo(at.favre.lib.hood.BuildConfig.class));

        page.addTitle("Settings");
        page.addAction(DefaultActions.getGlobalSettingsAction(this), DefaultActions.getNfcSettingsAction(this));
        page.addAction(DefaultActions.getNfcPaymentSettingsAction(this), DefaultActions.getDevSettingsAction(this));
        page.addAction(DefaultActions.getDateSettingsAction(this), DefaultActions.getAirplaneModeSettingsAction(this));
        page.addAction(DefaultActions.getSetLockScreenAction(this), DefaultActions.getDeviceInfoSettingsAction(this));
        page.addAction(DefaultActions.getBattSaverSettingsAction(this), DefaultActions.getDisplaySettingsAction(this));
        page.addAction(DefaultActions.getInputModeSettingsAction(this), DefaultActions.getStorageSettingsAction(this));
        page.addAction(DefaultActions.getSecuritySettingsAction(this), DefaultActions.getSyncSettingsAction(this));

        page.addEntries(DefaultProperties.createRuntimePermissionInfo(this, true));

        page.addTitle("System Features");
        Map<String, String> systemFeatureMap = new HashMap<>();
        systemFeatureMap.put("hasHce", "android.hardware.nfc.hce");
        systemFeatureMap.put("hasCamera", "android.hardware.camera");
        systemFeatureMap.put("hasGPS", "android.hardware.location.gps");
        systemFeatureMap.put("hasWebview", "android.software.webview");
        page.addEntries(DefaultProperties.createSystemFeatureInfo(this, systemFeatureMap));

        page.addTitle("Property File");
        page.addEntries(DefaultProperties.createPropertiesEntries(getTestProperties()));

        page.addEntries(DefaultProperties.createConnectivityStatusInfo(this, true));

        return page;
    }

    private Properties getTestProperties() {
        Properties p = new Properties();
        p.setProperty("propKey1", "value1");
        p.setProperty("propKey2", "value2");
        p.setProperty("propKey3", "value3");
        p.setProperty("propKey4", "value4");
        p.setProperty("propKey5", "value5");
        return p;
    }
}
