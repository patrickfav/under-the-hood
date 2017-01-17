package at.favre.app.hood.demo;

import android.os.SystemClock;
import android.support.annotation.NonNull;

import java.util.Random;

import at.favre.lib.hood.extended.PopHoodActivity;
import at.favre.lib.hood.interfaces.Config;
import at.favre.lib.hood.interfaces.Page;
import at.favre.lib.hood.interfaces.Pages;
import at.favre.lib.hood.interfaces.values.DynamicValue;
import at.favre.lib.hood.page.entries.KeyValueEntry;
import at.favre.lib.hood.util.PageUtil;
import at.favre.lib.hood.util.defaults.DefaultButtonDefinitions;
import at.favre.lib.hood.util.defaults.DefaultProperties;

public class DebugDarkBackgroundValuesActivity extends PopHoodActivity {
    private static final String TAG = DebugDarkBackgroundValuesActivity.class.getName();

    @NonNull
    @Override
    public Pages getPageData(@NonNull Pages pages) {
        Page firstPage = pages.addNewPage("General");

        firstPage.add(DefaultProperties.createSectionAppVersionInfoFromBuildConfig(BuildConfig.class));


        for (int i = 0; i < 35; i++) {
            firstPage.add(new KeyValueEntry("Test Loading " + i, new DynamicValue<String>() {
                @Override
                public String getValue() {
                    int wait = new Random().nextInt(2000) + 2000;
                    SystemClock.sleep(wait);
                    return "done (" + wait + "ms)";
                }
            }, new KeyValueEntry.DialogClickAction(), false, true));
        }

        firstPage.add(new KeyValueEntry("Test Loading ML", new DynamicValue<String>() {
            @Override
            public String getValue() {
                int wait = new Random().nextInt(2000) + 2000;
                SystemClock.sleep(wait);
                return "done ml (" + wait + "ms)";
            }
        }, new KeyValueEntry.DialogClickAction(), true, true));

        PageUtil.addAction(firstPage, DefaultButtonDefinitions.getGlobalSettingsAction(), DefaultButtonDefinitions.getNfcSettingsAction());
        PageUtil.addAction(firstPage, DefaultButtonDefinitions.getNfcPaymentSettingsAction(), DefaultButtonDefinitions.getDevSettingsAction());
        PageUtil.addAction(firstPage, DefaultButtonDefinitions.getDateSettingsAction(), DefaultButtonDefinitions.getAirplaneModeSettingsAction());
        PageUtil.addAction(firstPage, DefaultButtonDefinitions.getSetLockScreenAction(), DefaultButtonDefinitions.getDeviceInfoSettingsAction());
        PageUtil.addAction(firstPage, DefaultButtonDefinitions.getBatterySaverSettingsAction(), DefaultButtonDefinitions.getDisplaySettingsAction());
        PageUtil.addAction(firstPage, DefaultButtonDefinitions.getInputModeSettingsAction(), DefaultButtonDefinitions.getStorageSettingsAction());
        PageUtil.addAction(firstPage, DefaultButtonDefinitions.getSecuritySettingsAction(), DefaultButtonDefinitions.getInstalledAppSettings());

        return pages;
    }

    @NonNull
    @Override
    public Config getConfig() {
        return new Config.Builder()
                .setShowHighlightContent(false)
                .setLogTag(TAG).build();
    }
}
