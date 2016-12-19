package at.favre.lib.hood.defaults;


import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;

import at.favre.lib.hood.interfaces.actions.ButtonAction;
import at.favre.lib.hood.page.entries.ActionEntry;
import at.favre.lib.hood.util.DebugCrashException;

/**
 * A couple of default action implementations used with {@link ActionEntry}
 */
public class DefaultActions {

    /**
     * Will open the app's OS info screen
     */
    public static ButtonAction getAppInfoAction(final Activity activity) {
        return new ButtonAction("App Info", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(DefaultMiscActions.getAppInfoIntent(activity));
            }
        });
    }

    /**
     * Will start the OS' uninstall activity for this app
     */
    public static ButtonAction getUninstallAction(final Activity activity) {
        return new ButtonAction("Uninstall", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(DefaultMiscActions.getAppUninstallIntent(activity));
            }
        });
    }

    /**
     * Will through a runtime exception (to test crash recovery of the app)
     */
    public static ButtonAction getCrashAction() {
        return new ButtonAction("Crash Activity", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throw new DebugCrashException();
            }
        });
    }

    /**
     * Will open the "Set lock screen" wizard of the OS
     */
    public static ButtonAction getSetLockScreenAction(final Activity activity) {
        return new ButtonAction("Set Lockscreen", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(DevicePolicyManager.ACTION_SET_NEW_PASSWORD));
            }
        });
    }

    public static ButtonAction getNfcPaymentSettingsAction(final Activity activity) {
        return getGenericAndroidSettingsAction(activity, "Nfc Payment Settings", android.provider.Settings.ACTION_NFC_PAYMENT_SETTINGS, Build.VERSION_CODES.KITKAT);
    }

    public static ButtonAction getNfcSettingsAction(final Activity activity) {
        return getGenericAndroidSettingsAction(activity, "Nfc Settings", android.provider.Settings.ACTION_NFC_SETTINGS, Build.VERSION_CODES.JELLY_BEAN);
    }

    public static ButtonAction getGlobalSettingsAction(final Activity activity) {
        return getGenericAndroidSettingsAction(activity, "Settings", Settings.ACTION_SETTINGS, null);
    }

    public static ButtonAction getAirplaneModeSettingsAction(final Activity activity) {
        return getGenericAndroidSettingsAction(activity, "Airplane Settings", Settings.ACTION_AIRPLANE_MODE_SETTINGS, null);
    }

    public static ButtonAction getDevSettingsAction(final Activity activity) {
        return getGenericAndroidSettingsAction(activity, "Developer Settings", Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS, null);
    }

    public static ButtonAction getBattSaverSettingsAction(final Activity activity) {
        return getGenericAndroidSettingsAction(activity, "Battery Saver Settings", Settings.ACTION_BATTERY_SAVER_SETTINGS, Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    public static ButtonAction getDisplaySettingsAction(final Activity activity) {
        return getGenericAndroidSettingsAction(activity, "Device Info Settings", Settings.ACTION_DEVICE_INFO_SETTINGS, null);
    }

    public static ButtonAction getDeviceInfoSettingsAction(final Activity activity) {
        return getGenericAndroidSettingsAction(activity, "Display Settings", Settings.ACTION_DISPLAY_SETTINGS, null);
    }

    public static ButtonAction getStorageSettingsAction(final Activity activity) {
        return getGenericAndroidSettingsAction(activity, "Storage Settings", Settings.ACTION_INTERNAL_STORAGE_SETTINGS, null);
    }

    public static ButtonAction getSecuritySettingsAction(final Activity activity) {
        return getGenericAndroidSettingsAction(activity, "Security Settings", Settings.ACTION_SECURITY_SETTINGS, null);
    }

    public static ButtonAction getSyncSettingsAction(final Activity activity) {
        return getGenericAndroidSettingsAction(activity, "Sync Settings", Settings.ACTION_SYNC_SETTINGS, null);
    }

    public static ButtonAction getInputModeSettingsAction(final Activity activity) {
        return getGenericAndroidSettingsAction(activity, "Input Mode Settings", Settings.ACTION_INPUT_METHOD_SETTINGS, null);
    }

    public static ButtonAction getDateSettingsAction(final Activity activity) {
        return getGenericAndroidSettingsAction(activity, "Date Settings", Settings.ACTION_DATE_SETTINGS, null);
    }

    public static ButtonAction getInstalledAppSettings(final Activity activity) {
        return getGenericAndroidSettingsAction(activity, "Apps Settings", android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS, null);
    }

    /**
     * Will open an Android setting
     * @param activity the context
     * @param label ui text
     * @param settingsIntentAction the settings name wrapped as intent
     * @param minSdkVersion if non-null a sdk-int that is requried to use this settings
     * @return a non-null action if sdk-int is null or greater or equal than minSdkVersion
     */
    @Nullable
    public static ButtonAction getGenericAndroidSettingsAction(final Activity activity, String label, final String settingsIntentAction, Integer minSdkVersion) {
        if (minSdkVersion == null || android.os.Build.VERSION.SDK_INT >= minSdkVersion) {
            return new ButtonAction(label, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(settingsIntentAction));
                }
            });
        } else {
            return null;
        }
    }

    /**
     * Will kill all associated processes (similar behavior as force-stop in the app's info menu) and
     * therefor forcefully ends the app
     */
    public static ButtonAction getKillProcessAction(final Activity activity) {
        return new ButtonAction("Kill Process", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefaultMiscActions.killProcessesAround(activity);
            }
        });
    }

    /**
     * Will open a prompt to ask the user if he/she wants to clear the whole app data or null
     * if SDK < 19
     */
    @Nullable
    public static ButtonAction getClearAppDataAction(final Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return new ButtonAction("Clear App Data", new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
                    DefaultMiscActions.promptUserToClearData(v.getContext());
                }
            });
        } else {
            return null;
        }
    }
}
