package at.favre.lib.hood.defaults;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Toast;

import at.favre.lib.hood.util.DebugCrashException;
import at.favre.lib.hood.page.entries.ActionEntry;
import at.favre.lib.hood.util.HoodUtil;

import static android.content.Context.ACTIVITY_SERVICE;

public class DefaultActions {

    public static ActionEntry.Action getAppInfoAction(final Activity activity) {
        return new ActionEntry.Action("App Info", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(DefaultIntents.getAppInfoIntent(activity));
            }
        });
    }

    public static ActionEntry.Action getUninstallAction(final Activity activity) {
        return new ActionEntry.Action("Uninstall", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(DefaultIntents.getAppUnisntallIntent(activity));
            }
        });
    }

    public static ActionEntry.Action getCrashAction() {
        return new ActionEntry.Action("Crash Activity", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throw new DebugCrashException();
            }
        });
    }

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
        return getGenericAndroidSettingsAction(activity, "Device Info Settings", Settings.ACTION_DISPLAY_SETTINGS, null);
    }

    public static ActionEntry.Action getDeviceInfoSettingsAction(final Activity activity) {
        return getGenericAndroidSettingsAction(activity, "Display Settings", Settings.ACTION_DEVICE_INFO_SETTINGS, null);
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

    public static ActionEntry.Action getKilleProcessAction(final Activity activity) {
        return new ActionEntry.Action("Kill Process", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HoodUtil.killProcessesAround(activity);
            }
        });
    }

    public static ActionEntry.Action getClearAppDataAction(final Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return new ActionEntry.Action("Clear App Data", new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("Clear App Data")
                            .setMessage("Do you really want to clear the whole app data? This cannot be undone.")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    ((ActivityManager) context.getSystemService(ACTIVITY_SERVICE))
                                            .clearApplicationUserData();
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                }
            });
        } else {
            return null;
        }
    }
}
