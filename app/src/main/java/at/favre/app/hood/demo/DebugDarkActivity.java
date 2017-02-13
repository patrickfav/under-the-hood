package at.favre.app.hood.demo;

import android.content.pm.FeatureInfo;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import at.favre.lib.hood.Hood;
import at.favre.lib.hood.extended.PopHoodActivity;
import at.favre.lib.hood.interfaces.Config;
import at.favre.lib.hood.interfaces.Page;
import at.favre.lib.hood.interfaces.Pages;
import at.favre.lib.hood.interfaces.actions.ButtonDefinition;
import at.favre.lib.hood.interfaces.actions.OnClickAction;
import at.favre.lib.hood.interfaces.values.DynamicValue;
import at.favre.lib.hood.interfaces.values.SpinnerElement;
import at.favre.lib.hood.util.Backend;
import at.favre.lib.hood.util.HoodUtil;
import at.favre.lib.hood.util.PackageInfoAssembler;
import at.favre.lib.hood.util.PageUtil;
import at.favre.lib.hood.util.defaults.DefaultButtonDefinitions;
import at.favre.lib.hood.util.defaults.DefaultConfigActions;
import at.favre.lib.hood.util.defaults.DefaultProperties;
import timber.log.Timber;

public class DebugDarkActivity extends PopHoodActivity {
    private static final String TAG = DebugDarkActivity.class.getName();

    @NonNull
    @Override
    public Pages getPageData(@NonNull Pages pages) {
        Page firstPage = pages.addNewPage("General");

        firstPage.add(DefaultProperties.createSectionAppVersionInfoFromBuildConfig(BuildConfig.class));

        PageUtil.addHeader(firstPage, "Debug Config");
        firstPage.add(Hood.get().createSpinnerEntry(DefaultConfigActions.getDefaultSharedPrefBackedSpinnerAction("Backend", getPreferences(MODE_PRIVATE), "BACKEND_ID", null, getBackendElements())));
        firstPage.add(Hood.get().createSwitchEntry(DefaultConfigActions.getBoolSharedPreferencesConfigAction(getPreferences(MODE_PRIVATE), "KEY_TEST", "Enable debug feat#1", false)));
        firstPage.add(Hood.get().createSwitchEntry(DefaultConfigActions.getBoolSharedPreferencesConfigAction(getPreferences(MODE_PRIVATE), "KEY_TEST2", "Enable debug feat#2", false)));
        firstPage.add(Hood.get().createSwitchEntry(DefaultConfigActions.getBoolSharedPreferencesConfigAction(getPreferences(MODE_PRIVATE), "KEY_TEST3", "Enable debug feat#3", false)));
        firstPage.add(Hood.get().createSpacer());
        firstPage.add(Hood.get().createPropertyEntry("Test Loading3", new DynamicValue.Async<String>() {
            @Override
            public String getValue() {
                SystemClock.sleep(3800);
                return "done3";
            }
        }));

        Hood.get().createPropertyEntry("uptime", new DynamicValue<String>() {
            @Override
            public String getValue() {
                return HoodUtil.millisToDaysHoursMinString(SystemClock.elapsedRealtime());
            }
        }, Hood.ext().createOnClickActionToast(), false);

        firstPage.add(DefaultProperties.createSectionBasicDeviceInfo());
        firstPage.add(DefaultProperties.createDetailedDeviceInfo(this));

        firstPage.add(Hood.get().createPropertyEntry("Test Loading", new DynamicValue.Async<String>() {
            @Override
            public String getValue() {
                SystemClock.sleep(4000);
                return "done";
            }
        }));

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

        for (FeatureInfo featureInfo : getPackageManager().getSystemAvailableFeatures()) {
            Timber.d(String.valueOf(featureInfo) + "\n");
        }

        PageUtil.addAction(firstPage, DefaultButtonDefinitions.getCrashAction());
        PageUtil.addAction(firstPage, DefaultButtonDefinitions.getKillProcessAction(this), DefaultButtonDefinitions.getClearAppDataAction());
        PageUtil.addAction(firstPage, HoodUtil.getConditionally(DefaultButtonDefinitions.getKillProcessAction(this), BuildConfig.DEBUG));

        PageUtil.addHeader(firstPage, "Lib BuildConfig");
        firstPage.add(DefaultProperties.createStaticFieldsInfo(at.favre.lib.hood.BuildConfig.class));
        firstPage.add(new PackageInfoAssembler(PackageInfoAssembler.Type.USES_FEATURE, PackageInfoAssembler.Type.PERMISSIONS).createSection(this, true));
        PageUtil.addHeader(firstPage, "Property File");
        firstPage.add(DefaultProperties.createPropertiesEntries(getTestProperties()));

        firstPage.add(DefaultProperties.createSectionConnectivityStatusInfo(this));
        firstPage.add(Hood.get().createHeaderEntry("System Features"));
        firstPage.add(Hood.get().createPropertyEntry("The Key", "The value"));
        firstPage.add(DefaultProperties.createSectionBasicDeviceInfo());
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
        firstPage.add(Hood.get().createHeaderEntry("Settings"));
        firstPage.add(Hood.get().createActionEntry(new ButtonDefinition("Click me", new OnClickAction() {
            @Override
            public void onClick(View v, Map.Entry<CharSequence, String> value) {
                Toast.makeText(v.getContext(), "On button clicked", Toast.LENGTH_SHORT).show();
            }
        })));
        PageUtil.addAction(firstPage, DefaultButtonDefinitions.getGlobalSettingsAction(), DefaultButtonDefinitions.getNfcSettingsAction());
        PageUtil.addAction(firstPage, DefaultButtonDefinitions.getNfcPaymentSettingsAction(), DefaultButtonDefinitions.getDevSettingsAction());
        PageUtil.addAction(firstPage, DefaultButtonDefinitions.getDateSettingsAction(), DefaultButtonDefinitions.getAirplaneModeSettingsAction());
        PageUtil.addAction(firstPage, DefaultButtonDefinitions.getSetLockScreenAction(), DefaultButtonDefinitions.getDeviceInfoSettingsAction());
        PageUtil.addAction(firstPage, DefaultButtonDefinitions.getBatterySaverSettingsAction(), DefaultButtonDefinitions.getDisplaySettingsAction());
        PageUtil.addAction(firstPage, DefaultButtonDefinitions.getInputModeSettingsAction(), DefaultButtonDefinitions.getStorageSettingsAction());
        PageUtil.addAction(firstPage, DefaultButtonDefinitions.getSecuritySettingsAction(), DefaultButtonDefinitions.getInstalledAppSettings());

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
        return Config.newBuilder()
                .setShowHighlightContent(false)
                .setLogTag(TAG).build();
    }
}
