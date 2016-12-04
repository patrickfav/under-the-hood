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

import at.favre.lib.hood.page.entries.ActionEntry;
import at.favre.lib.hood.util.DebugCrashException;

/**
 * A couple of default action implementations used with {@link ActionEntry}
 */
public class DefaultActions {

    /**
     * Will open the app's OS info screen
     */
    public static ActionEntry.Action getAppInfoAction(final Activity activity) {
        return new ActionEntry.Action("App Info", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(DefaultMiscActions.getAppInfoIntent(activity));
            }
        });
    }

    /**
     * Will start the OS' uninstall activity for this app
     */
    public static ActionEntry.Action getUninstallAction(final Activity activity) {
        return new ActionEntry.Action("Uninstall", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(DefaultMiscActions.getAppUninstallIntent(activity));
            }
        });
    }

    /**
     * Will through a runtime exception (to test crash recovery of the app)
     */
    public static ActionEntry.Action getCrashAction() {
        return new ActionEntry.Action("Crash Activity", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throw new DebugCrashException();
            }
        });
    }

    /**
     * Will open the "Set lock screen" wizard of the OS
     */
    public static ActionEntry.Action getSetLockScreenAction(final Activity activity) {
        return new ActionEntry.Action("Set Lockscreen", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(DevicePolicyManager.ACTION_SET_NEW_PASSWORD));
            }
        });
    }

    public static ActionEntry.Action getNfcPaymentSettingsAction(final Activity activity) {
        return getGenericAndroidSettingsAction(activity, "Nfc Payment Settings", android.provider.Settings.ACTION_NFC_PAYMENT_SETTINGS, Build.VERSION_CODES.KITKAT);
    }

    public static ActionEntry.Action getNfcSettingsAction(final Activity activity) {
        return getGenericAndroidSettingsAction(activity, "Nfc Settings", android.provider.Settings.ACTION_NFC_SETTINGS, Build.VERSION_CODES.JELLY_BEAN);
    }

    public static ActionEntry.Action getGlobalSettingsAction(final Activity activity) {
        return getGenericAndroidSettingsAction(activity, "Settings", Settings.ACTION_SETTINGS, null);
    }

    public static ActionEntry.Action getAirplaneModeSettingsAction(final Activity activity) {
        return getGenericAndroidSettingsAction(activity, "Airplane Settings", Settings.ACTION_AIRPLANE_MODE_SETTINGS, null);
    }

    public static ActionEntry.Action getDevSettingsAction(final Activity activity) {
        return getGenericAndroidSettingsAction(activity, "Developer Settings", Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS, null);
    }

    public static ActionEntry.Action getBattSaverSettingsAction(final Activity activity) {
        return getGenericAndroidSettingsAction(activity, "Battery Saver Settings", Settings.ACTION_BATTERY_SAVER_SETTINGS, Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    public static ActionEntry.Action getDisplaySettingsAction(final Activity activity) {
        return getGenericAndroidSettingsAction(activity, "Device Info Settings", Settings.ACTION_DEVICE_INFO_SETTINGS, null);
    }

    public static ActionEntry.Action getDeviceInfoSettingsAction(final Activity activity) {
        return getGenericAndroidSettingsAction(activity, "Display Settings", Settings.ACTION_DISPLAY_SETTINGS, null);
    }

    public static ActionEntry.Action getStorageSettingsAction(final Activity activity) {
        return getGenericAndroidSettingsAction(activity, "Storage Settings", Settings.ACTION_INTERNAL_STORAGE_SETTINGS, null);
    }

    public static ActionEntry.Action getSecuritySettingsAction(final Activity activity) {
        return getGenericAndroidSettingsAction(activity, "Security Settings", Settings.ACTION_SECURITY_SETTINGS, null);
    }

    public static ActionEntry.Action getSyncSettingsAction(final Activity activity) {
        return getGenericAndroidSettingsAction(activity, "Sync Settings", Settings.ACTION_SYNC_SETTINGS, null);
    }

    public static ActionEntry.Action getInputModeSettingsAction(final Activity activity) {
        return getGenericAndroidSettingsAction(activity, "Input Mode Settings", Settings.ACTION_INPUT_METHOD_SETTINGS, null);
    }

    public static ActionEntry.Action getDateSettingsAction(final Activity activity) {
        return getGenericAndroidSettingsAction(activity, "Date Settings", Settings.ACTION_DATE_SETTINGS, null);
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
    public static ActionEntry.Action getGenericAndroidSettingsAction(final Activity activity, String label, final String settingsIntentAction, Integer minSdkVersion) {
        if (minSdkVersion == null || android.os.Build.VERSION.SDK_INT >= minSdkVersion) {
            return new ActionEntry.Action(label, new View.OnClickListener() {
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
    public static ActionEntry.Action getKillProcessAction(final Activity activity) {
        return new ActionEntry.Action("Kill Process", new View.OnClickListener() {
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
    public static ActionEntry.Action getClearAppDataAction(final Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return new ActionEntry.Action("Clear App Data", new View.OnClickListener() {
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
