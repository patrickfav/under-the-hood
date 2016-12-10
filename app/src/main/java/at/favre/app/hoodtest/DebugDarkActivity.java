package at.favre.app.hoodtest;

import android.support.annotation.NonNull;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import at.favre.lib.hood.defaults.DefaultActions;
import at.favre.lib.hood.defaults.DefaultConfigActions;
import at.favre.lib.hood.defaults.DefaultProperties;
import at.favre.lib.hood.extended.PopHoodActivity;
import at.favre.lib.hood.page.Config;
import at.favre.lib.hood.page.DebugPage;
import at.favre.lib.hood.page.Page;
import at.favre.lib.hood.page.entries.ActionEntry;
import at.favre.lib.hood.page.entries.ConfigBoolEntry;
import at.favre.lib.hood.page.entries.ConfigSpinnerEntry;
import at.favre.lib.hood.page.entries.HeaderEntry;
import at.favre.lib.hood.page.entries.KeyValueEntry;
import at.favre.lib.hood.page.values.SpinnerElement;
import at.favre.lib.hood.util.HoodUtil;

public class DebugDarkActivity extends PopHoodActivity {
    private static final String TAG = DebugDarkActivity.class.getName();

    @NonNull
    @Override
    public Page getPageData(DebugPage page) {
        page.addEntries(DefaultProperties.createAppVersionInfo(at.favre.lib.hood.BuildConfig.class, true));
        page.addEntries(DefaultProperties.createSignatureHashInfo(this));

        page.addTitle("Debug Config");
        page.addEntry(new ConfigSpinnerEntry(DefaultConfigActions.getDefaultSharedPrefBackedSpinnerAction(getPreferences(MODE_PRIVATE), "BACKEND_ID", null, getBackendElements())));
        page.addEntry(new ConfigBoolEntry(DefaultConfigActions.getBoolSharedPreferencesConfigAction(getPreferences(MODE_PRIVATE), "KEY_TEST", "Enable debug feat#1", false)));
        page.addEntry(new ConfigBoolEntry(DefaultConfigActions.getBoolSharedPreferencesConfigAction(getPreferences(MODE_PRIVATE), "KEY_TEST2", "Enable debug feat#2", false)));
        page.addEntry(new ConfigBoolEntry(DefaultConfigActions.getBoolSharedPreferencesConfigAction(getPreferences(MODE_PRIVATE), "KEY_TEST3", "Enable debug feat#3", false)));

        page.addEntries(DefaultProperties.createBasicDeviceInfo(true));
        page.addEntries(DefaultProperties.createDetailedDeviceInfo(this));

        page.addEntry(new KeyValueEntry("MultiLine Test", "I am displaying text in a textview that appears to\nbe too long to fit into one screen. \nI need to make my TextView scrollable. How can i do\nthat? Here is the code\nbe too long to fit into one screen. \nI need to make my TextView scrollable. How can i do\nthat? Here is the code\ne too long to fit into one screen. \nI need to make my TextView scrollable. How can i do\nthat? Here is the code", true));


        page.addEntries(DefaultProperties.createTelephonyMangerInfo(this, true));

        page.addEntry(new HeaderEntry("Misc Action", true));
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
        page.addAction(DefaultActions.getCrashAction(), DefaultActions.getInstalledAppSettings(this));
        page.addAction(DefaultActions.getKillProcessAction(this), DefaultActions.getClearAppDataAction(this));
        page.addAction(HoodUtil.getConditionally(DefaultActions.getKillProcessAction(this), at.favre.lib.hood.BuildConfig.DEBUG));

        page.addTitle("Lib BuildConfig");
        page.addEntries(DefaultProperties.createStaticFieldsInfo(at.favre.lib.hood.BuildConfig.class));

        page.addEntry(new HeaderEntry("Settings", true));
        page.addAction(DefaultActions.getGlobalSettingsAction(this), DefaultActions.getNfcSettingsAction(this));
        page.addAction(DefaultActions.getNfcPaymentSettingsAction(this), DefaultActions.getDevSettingsAction(this));
        page.addAction(DefaultActions.getDateSettingsAction(this), DefaultActions.getAirplaneModeSettingsAction(this));
        page.addAction(DefaultActions.getSetLockScreenAction(this), DefaultActions.getDeviceInfoSettingsAction(this));
        page.addAction(DefaultActions.getBattSaverSettingsAction(this), DefaultActions.getDisplaySettingsAction(this));
        page.addAction(DefaultActions.getInputModeSettingsAction(this), DefaultActions.getStorageSettingsAction(this));
        page.addAction(DefaultActions.getSecuritySettingsAction(this), DefaultActions.getSyncSettingsAction(this));

        page.addEntries(DefaultProperties.createRuntimePermissionInfo(this, true));

        page.addEntries(DefaultProperties.createDeclaredSystemFeatureInfo(this, true));
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
        return p;
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
        return new Config.Builder().setLogTag(TAG).build();
    }
}
