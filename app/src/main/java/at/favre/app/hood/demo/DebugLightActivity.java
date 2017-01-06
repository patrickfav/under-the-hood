package at.favre.app.hood.demo;

import android.support.annotation.NonNull;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.favre.lib.hood.Hood;
import at.favre.lib.hood.defaults.DefaultActions;
import at.favre.lib.hood.defaults.DefaultConfigActions;
import at.favre.lib.hood.defaults.DefaultProperties;
import at.favre.lib.hood.extended.PopHoodActivity;
import at.favre.lib.hood.interfaces.Config;
import at.favre.lib.hood.interfaces.Page;
import at.favre.lib.hood.interfaces.Pages;
import at.favre.lib.hood.interfaces.actions.ButtonAction;
import at.favre.lib.hood.interfaces.values.SpinnerElement;
import at.favre.lib.hood.util.HoodUtil;
import at.favre.lib.hood.util.PageUtil;

public class DebugLightActivity extends PopHoodActivity {
    private static final String TAG = DebugLightActivity.class.getName();

    @NonNull
    @Override
    public Pages getPageData(@NonNull Pages pages) {
        Page firstPage = pages.addNewPage();

        firstPage.add(DefaultProperties.createSectionAppVersionInfoFromBuildConfig(at.favre.lib.hood.BuildConfig.class));

        firstPage.add(DefaultProperties.createSectionBasicDeviceInfo());
        firstPage.add(DefaultProperties.createDetailedDeviceInfo(this));

        firstPage.add(Hood.createSwitchEntry(DefaultConfigActions.getBoolSharedPreferencesConfigAction(getPreferences(MODE_PRIVATE), "KEY_TEST", false)));
        firstPage.add(Hood.createSwitchEntry(DefaultConfigActions.getBoolSharedPreferencesConfigAction(getPreferences(MODE_PRIVATE), "KEY_TEST2", false)));
        firstPage.add(Hood.createSwitchEntry(DefaultConfigActions.getBoolSharedPreferencesConfigAction(getPreferences(MODE_PRIVATE), "KEY_TEST3", "a debug feature", false)));

        firstPage.add(Hood.createSpinnerEnry(DefaultConfigActions.getDefaultSharedPrefBackedSpinnerAction(null, getPreferences(MODE_PRIVATE), "W_BACKEND_KEY", null, getBackendElements())));

        firstPage.add(DefaultProperties.createSectionTelephonyManger(this));

        PageUtil.addHeader(firstPage, "Misc Actions");
        PageUtil.addAction(firstPage, DefaultActions.getAppInfoAction(this));
        PageUtil.addAction(firstPage, DefaultActions.getCrashAction(), DefaultActions.getUninstallAction(this));
        PageUtil.addAction(firstPage, DefaultActions.getKillProcessAction(this), DefaultActions.getClearAppDataAction(this));
        PageUtil.addAction(firstPage, HoodUtil.getConditionally(DefaultActions.getKillProcessAction(this), at.favre.lib.hood.BuildConfig.DEBUG));

        PageUtil.addHeader(firstPage, "System Features");
        Map<CharSequence, String> systemFeatureMap = new HashMap<>();
        systemFeatureMap.put("hasHce", "android.hardware.nfc.hce");
        systemFeatureMap.put("hasCamera", "android.hardware.camera");
        systemFeatureMap.put("hasWebview", "android.software.webview");
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
        firstPage.add(DefaultProperties.createSystemFeatureInfo(this, systemFeatureMap));

        firstPage.add(DefaultProperties.createConnectivityStatusInfo(this, true));

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
