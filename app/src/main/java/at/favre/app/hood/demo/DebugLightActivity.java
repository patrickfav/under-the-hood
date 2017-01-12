package at.favre.app.hood.demo;

import android.support.annotation.NonNull;
import android.view.View;

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


        firstPage.add(DefaultProperties.createSectionSourceControlAndCI(BuildConfig.GIT_REV, BuildConfig.GIT_BRANCH, BuildConfig.GIT_DATE, null, null, null));
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

        return pages;
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
        return new Config.Builder().setLogTag(TAG).setAutoLog(false).build();
    }
}
