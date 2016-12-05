package at.favre.app.hoodtest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import at.favre.lib.hood.*;
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

public class DebugLightActivity extends PopHoodActivity {

    public Page getPageData() {
        DebugPage page = new DebugPage();

        page.addEntries(DefaultProperties.createAppVersionInfo(at.favre.lib.hood.BuildConfig.class, true));
        page.addEntries(DefaultProperties.createSignatureHashInfo(this));

        page.addEntries(DefaultProperties.createBasicDeviceInfo(true));
        page.addEntries(DefaultProperties.createDetailedDeviceInfo(this));

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

        page.addEntries(DefaultProperties.createTelephonyMangerInfo(this, true));

        page.addTitle("Misc Actions");
        page.addAction(DefaultActions.getAppInfoAction(this));
        page.addAction(DefaultActions.getCrashAction(), DefaultActions.getUninstallAction(this));
        page.addAction(DefaultActions.getKillProcessAction(this), DefaultActions.getClearAppDataAction(this));
        page.addAction(HoodUtil.getConditionally(DefaultActions.getKillProcessAction(this), at.favre.lib.hood.BuildConfig.DEBUG));

        page.addEntries(DefaultProperties.createRuntimePermissionInfo(this, true));

        page.addTitle("System Features");
        Map<String, String> systemFeatureMap = new HashMap<>();
        systemFeatureMap.put("hasHce", "android.hardware.nfc.hce");
        systemFeatureMap.put("hasCamera", "android.hardware.camera");
        systemFeatureMap.put("hasWebview", "android.software.webview");
        page.addEntries(DefaultProperties.createSystemFeatureInfo(this, systemFeatureMap));

        page.addEntries(DefaultProperties.createConnectivityStatusInfo(this, true));

        return page;
    }
}
