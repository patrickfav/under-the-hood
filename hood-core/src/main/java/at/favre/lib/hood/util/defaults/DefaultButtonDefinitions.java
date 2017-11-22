/*
 *  Copyright 2016 Patrick Favre-Bulle
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package at.favre.lib.hood.util.defaults;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;

import java.util.Map;

import at.favre.lib.hood.interfaces.actions.ButtonDefinition;
import at.favre.lib.hood.interfaces.actions.OnClickAction;
import at.favre.lib.hood.util.DebugCrashException;

/**
 * A couple of default action implementations used with {@link at.favre.lib.hood.interfaces.HoodAPI#createActionEntry(ButtonDefinition)}
 */
public final class DefaultButtonDefinitions {

    private DefaultButtonDefinitions() {
    }

    /**
     * Will open the app's OS info screen
     */
    public static ButtonDefinition getAppInfoAction() {
        return new ButtonDefinition("App Info", new OnClickAction() {
            @Override
            public void onClick(View v, Map.Entry<CharSequence, String> value) {
                v.getContext().startActivity(DefaultMiscActions.getAppInfoIntent(v.getContext()));
            }
        });
    }

    /**
     * Will start the OS' uninstall activity for this app
     */
    public static ButtonDefinition getUninstallAction() {
        return new ButtonDefinition("Uninstall", new OnClickAction() {
            @Override
            public void onClick(View v, Map.Entry<CharSequence, String> value) {
                v.getContext().startActivity(DefaultMiscActions.getAppUninstallIntent(v.getContext()));
            }
        });
    }

    /**
     * Will through a runtime exception (to test crash recovery of the app)
     */
    public static ButtonDefinition getCrashAction() {
        return new ButtonDefinition("Crash Activity", new OnClickAction() {
            @Override
            public void onClick(View v, Map.Entry<CharSequence, String> value) {
                throw new DebugCrashException();
            }
        });
    }

    /**
     * Will open the "Set lock screen" wizard of the OS
     */
    public static ButtonDefinition getSetLockScreenAction() {
        return new ButtonDefinition("Set Lock-Screen", new OnClickAction() {
            @Override
            public void onClick(View v, Map.Entry<CharSequence, String> value) {
                v.getContext().startActivity(new Intent(DevicePolicyManager.ACTION_SET_NEW_PASSWORD));
            }
        });
    }

    public static ButtonDefinition getNfcPaymentSettingsAction() {
        return getGenericAndroidSettingsAction("Nfc Payment Settings", android.provider.Settings.ACTION_NFC_PAYMENT_SETTINGS, Build.VERSION_CODES.KITKAT);
    }

    public static ButtonDefinition getNfcSettingsAction() {
        return getGenericAndroidSettingsAction("Nfc Settings", android.provider.Settings.ACTION_NFC_SETTINGS, Build.VERSION_CODES.JELLY_BEAN);
    }

    public static ButtonDefinition getGlobalSettingsAction() {
        return getGenericAndroidSettingsAction("Settings", Settings.ACTION_SETTINGS, null);
    }

    public static ButtonDefinition getAirplaneModeSettingsAction() {
        return getGenericAndroidSettingsAction("Airplane Settings", Settings.ACTION_AIRPLANE_MODE_SETTINGS, null);
    }

    public static ButtonDefinition getDevSettingsAction() {
        return getGenericAndroidSettingsAction("Developer Settings", Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS, null);
    }

    public static ButtonDefinition getBatterySaverSettingsAction() {
        return getGenericAndroidSettingsAction("Battery Saver Settings", Settings.ACTION_BATTERY_SAVER_SETTINGS, Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    public static ButtonDefinition getBatterySummarySettingsAction() {
        return getGenericAndroidSettingsAction("Battery Settings", Intent.ACTION_POWER_USAGE_SUMMARY, Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    public static ButtonDefinition getDisplaySettingsAction() {
        return getGenericAndroidSettingsAction("Device Info Settings", Settings.ACTION_DEVICE_INFO_SETTINGS, null);
    }

    public static ButtonDefinition getDeviceInfoSettingsAction() {
        return getGenericAndroidSettingsAction("Display Settings", Settings.ACTION_DISPLAY_SETTINGS, null);
    }

    public static ButtonDefinition getStorageSettingsAction() {
        return getGenericAndroidSettingsAction("Storage Settings", Settings.ACTION_INTERNAL_STORAGE_SETTINGS, null);
    }

    public static ButtonDefinition getSecuritySettingsAction() {
        return getGenericAndroidSettingsAction("Security Settings", Settings.ACTION_SECURITY_SETTINGS, null);
    }

    public static ButtonDefinition getSyncSettingsAction() {
        return getGenericAndroidSettingsAction("Sync Settings", Settings.ACTION_SYNC_SETTINGS, null);
    }

    public static ButtonDefinition getInputModeSettingsAction() {
        return getGenericAndroidSettingsAction("Input Mode Settings", Settings.ACTION_INPUT_METHOD_SETTINGS, null);
    }

    public static ButtonDefinition getDateSettingsAction() {
        return getGenericAndroidSettingsAction("Date Settings", Settings.ACTION_DATE_SETTINGS, null);
    }

    public static ButtonDefinition getInstalledAppSettings() {
        return getGenericAndroidSettingsAction("Apps Settings", android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS, null);
    }

    /**
     * Will open an Android setting
     *
     * @param label                ui text
     * @param settingsIntentAction the settings name wrapped as intent
     * @param minSdkVersion        if non-null a sdk-int that is required to use this settings
     * @return a non-null action if sdk-int is null or greater or equal than minSdkVersion
     */
    @Nullable
    public static ButtonDefinition getGenericAndroidSettingsAction(String label, final String settingsIntentAction, Integer minSdkVersion) {
        if (minSdkVersion == null || android.os.Build.VERSION.SDK_INT >= minSdkVersion) {
            return new ButtonDefinition(label, new OnClickAction() {
                @Override
                public void onClick(View v, Map.Entry<CharSequence, String> value) {
                    v.getContext().startActivity(new Intent(settingsIntentAction));
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
    public static ButtonDefinition getKillProcessAction(final Activity activity) {
        return new ButtonDefinition("Kill Process", new OnClickAction() {
            @Override
            public void onClick(View v, Map.Entry<CharSequence, String> value) {
                DefaultMiscActions.killProcessesAround(activity);
            }
        });
    }

    /**
     * Will open a prompt to ask the user if he/she wants to clear the whole app data or null
     * if SDK < 19
     */
    @Nullable
    public static ButtonDefinition getClearAppDataAction() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return new ButtonDefinition("Clear App Data", new OnClickAction() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v, Map.Entry<CharSequence, String> value) {
                    DefaultMiscActions.promptUserToClearData(v.getContext());
                }
            });
        } else {
            return null;
        }
    }

    /**
     * Opens the app with given package name either in play store app or as a normal link
     *
     * @param packageName ie. the applicationId
     * @return the button def
     */
    public static ButtonDefinition getPlayStoreLink(final String packageName) {
        return new ButtonDefinition("Open in Playstore", new OnClickAction() {
            @Override
            public void onClick(View v, Map.Entry<CharSequence, String> value) {
                try {
                    v.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    v.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
                }
            }
        });
    }
}
