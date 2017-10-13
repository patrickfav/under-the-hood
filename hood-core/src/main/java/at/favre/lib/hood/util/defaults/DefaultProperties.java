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


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ConfigurationInfo;
import android.content.pm.PackageManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Debug;
import android.os.PowerManager;
import android.os.StrictMode;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;

import at.favre.lib.hood.Hood;
import at.favre.lib.hood.interfaces.PageEntry;
import at.favre.lib.hood.interfaces.Section;
import at.favre.lib.hood.interfaces.values.DynamicValue;
import at.favre.lib.hood.util.DeviceStatusUtil;
import at.favre.lib.hood.util.HoodUtil;
import at.favre.lib.hood.util.PermissionTranslator;
import at.favre.lib.hood.util.TypeTranslators;
import timber.log.Timber;

/**
 * A set of methods that returns default {@link at.favre.lib.hood.interfaces.HoodAPI#createPropertyEntry(CharSequence, DynamicValue)} type page entries.
 */
public class DefaultProperties {

    private static final String TAG = DefaultProperties.class.getName();

    /**
     * Returns entries of some basic device data like model number and sdk version.
     *
     * @return list of entries
     */
    public static Section.HeaderSection createSectionBasicDeviceInfo() {
        Section.ModifiableHeaderSection section = Hood.ext().createSection("Device");
        section.add(Hood.get().createPropertyEntry("model", Build.MODEL));
        section.add(Hood.get().createPropertyEntry("name", Build.DEVICE));
        section.add(Hood.get().createPropertyEntry("brand", Build.MANUFACTURER));
        section.add(Hood.get().createPropertyEntry("version", Build.VERSION.RELEASE));
        section.add(Hood.get().createPropertyEntry("version_minor", Build.VERSION.INCREMENTAL));
        section.add(Hood.get().createPropertyEntry("build-id", Build.ID));
        section.add(Hood.get().createPropertyEntry("sdk-int", String.valueOf(Build.VERSION.SDK_INT)));
        section.add(Hood.get().createPropertyEntry("serial", Build.SERIAL));

        return section;
    }

    /**
     * Additional device info
     *
     * @param activity can be null, but will just return an empty list
     * @return list of page-entries
     */
    public static List<PageEntry<?>> createDetailedDeviceInfo(@Nullable final Activity activity) {
        List<PageEntry<?>> entries = new ArrayList<>();
        if (activity != null) {
            DisplayMetrics metrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            entries.add(Hood.get().createPropertyEntry("dpi", "x" + metrics.density + "/" + metrics.densityDpi + "dpi"));
            entries.add(Hood.get().createPropertyEntry("resolution", metrics.heightPixels + "x" + metrics.widthPixels));
            entries.add(Hood.get().createPropertyEntry("locale/timezone", new DynamicValue<String>() {
                @Override
                public String getValue() {
                    return HoodUtil.getCurrentLocale(activity).toString() + "/" + TimeZone.getDefault().getDisplayName();
                }
            }));
            entries.add(Hood.get().createPropertyEntry("uptime", new DynamicValue<String>() {
                @Override
                public String getValue() {
                    return HoodUtil.millisToDaysHoursMinString(SystemClock.elapsedRealtime());
                }
            }));
            entries.add(Hood.get().createPropertyEntry("android-id", Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID)));
        }
        return entries;
    }

    /**
     * Very technical memory & classloader process states - this data is usually very volatile
     *
     * @param context
     * @return section for info
     */
    public static Section.HeaderSection createInternalProcessDebugInfo(@Nullable final Context context) {
        Section.ModifiableHeaderSection section = Hood.ext().createSection("Process Debug Info");

        if (context != null) {
            section.add(Hood.get().createPropertyEntry("heap-native", new DynamicValue<String>() {
                @Override
                public String getValue() {
                    return "used " + HoodUtil.humanReadableByteCount(Debug.getNativeHeapAllocatedSize(), false) + " of " + HoodUtil.humanReadableByteCount(Debug.getNativeHeapSize(), false);
                }
            }));

            section.add(Hood.get().createPropertyEntry("memory", new DynamicValue<String>() {
                @Override
                public String getValue() {
                    return "used " + HoodUtil.humanReadableByteCount(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory(), false) + " (max:" + HoodUtil.humanReadableByteCount(Runtime.getRuntime().maxMemory(), false) + ")";
                }
            }));
            section.add(Hood.get().createPropertyEntry("pss", new DynamicValue<String>() {
                @Override
                public String getValue() {
                    return HoodUtil.humanReadableByteCount(Debug.getPss(), false);
                }
            }));
            section.add(Hood.get().createPropertyEntry("loaded-classes", new DynamicValue<String>() {
                @Override
                public String getValue() {
                    return String.valueOf(Debug.getLoadedClassCount());
                }
            }));
            section.add(Hood.get().createPropertyEntry("local/death/proxy objs", new DynamicValue<String>() {
                @Override
                public String getValue() {
                    return String.valueOf(Debug.getBinderLocalObjectCount()) + "/" + String.valueOf(Debug.getBinderDeathObjectCount()) + "/" + String.valueOf(Debug.getBinderProxyObjectCount());
                }
            }));
            section.add(Hood.get().createPropertyEntry("debugger-connected", new DynamicValue<String>() {
                @Override
                public String getValue() {
                    return String.valueOf(Debug.isDebuggerConnected());
                }
            }));
        }
        return section;
    }


    /**
     * Traverses the static fields of given arbitrary class. Will create an entry for each non-null
     * static public field. Ignores "serialVersionUID".
     * <p>
     * NOTE: this uses reflection to traverse to class, so if you want to keep it after proguard, use
     * a keep rule like:
     * <p>
     * <pre>
     *     -keep public class your.package.name.BuildConfig { public *;}
     * </pre>
     *
     * @param clazz the BuildConfig.java class you want the info
     * @return list of page-entries
     */
    public static List<PageEntry<?>> createStaticFieldsInfo(Class<?> clazz) {
        List<PageEntry<?>> entries = new ArrayList<>();

        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            if (Modifier.isStatic(field.getModifiers()))
                try {
                    String key = field.getName();
                    String value = String.valueOf(field.get(null));

                    if (key != null && !key.equals("serialVersionUID") &&
                            value != null && !value.equals("null") && !value.trim().isEmpty()) {
                        entries.add(Hood.get().createPropertyEntry(key, String.valueOf(field.get(null))));
                    }
                } catch (Exception e) {
                    Timber.w("could not get field from class (" + field + ")");
                }
        }

        return entries;
    }

    /**
     * Traverses the static fields of given class (which must be of Type BuildConfig) and parses the main
     * fields that is create by the android gradle plugin (ie. version, app_id, etc.)
     * NOTE: this uses reflection to traverse to class, so if you want to keep it after proguard, use
     * a keep rule like:
     * <p>
     * <pre>
     *     -keep public class your.package.name.BuildConfig { public *;}
     * </pre>
     *
     * @param buildConfig the BuildConfig.java class you want the info
     * @return the section containing header, entries etc.
     */
    public static Section.HeaderSection createSectionAppVersionInfoFromBuildConfig(Class<?> buildConfig) {
        Section.ModifiableHeaderSection section = Hood.ext().createSection("App Version");
        Field[] declaredFields = buildConfig.getDeclaredFields();
        for (Field field : declaredFields) {
            if (Modifier.isStatic(field.getModifiers())) {
                try {
                    String key = null;
                    String value = String.valueOf(field.get(null));
                    switch (field.getName().toLowerCase()) {
                        case "version_name":
                            key = "version";
                            break;
                        case "version_code":
                            key = "version-code";
                            break;
                        case "application_id":
                            key = "app-id";
                            break;
                        case "build_type":
                            key = "build-type";
                            break;
                        case "flavor":
                            key = "flavor";
                            break;
                        case "debug":
                            key = "debug";
                            break;
                    }

                    if (key != null && value != null && !value.trim().isEmpty()) {
                        section.add(Hood.get().createPropertyEntry(key, String.valueOf(field.get(null))));
                    }

                } catch (Exception e) {
                    Timber.w("could not get field from BuildConfig (" + field + ")");
                }
            }
        }

        return section;
    }

    /**
     * Returns for each provided permission a page-entry containing the current dynamic state (granted, denied, etc.) including click
     * actions to request the permission.
     *
     * @param activity           can be null, but will just return an empty list
     * @param androidPermissions see {@link android.Manifest.permission}
     * @return the section containing header, entries etc.
     */
    public static Section.HeaderSection createSectionRuntimePermissions(@Nullable final Activity activity, List<String> androidPermissions) {
        Section.ModifiableHeaderSection section = Hood.ext().createSection("Permissions");

        if (activity != null && !androidPermissions.isEmpty()) {
            for (final String perm : androidPermissions) {
                section.add(Hood.get().createPropertyEntry(perm.replace("android.permission.", ""), new DynamicValue<String>() {
                    @Override
                    public String getValue() {
                        return TypeTranslators.translatePermissionState(PermissionTranslator.getPermissionStatus(activity, perm));
                    }
                }, Hood.ext().createOnClickActionAskPermission(perm, activity), false));
            }
        }
        return section;
    }

    /**
     * Get current state of various connectivity adapters (network, wifi, bt, nfc,...). Requires to have the specific
     * permission to be able to show the actual state (see eg. {@link DeviceStatusUtil#getNetworkConnectivityState(Context)}.
     * <p>
     * Click action is opening the system settings for each adapter.
     *
     * @param context can be null, but will just return an empty list
     * @return the section containing header, entries etc.
     */
    public static Section.HeaderSection createSectionConnectivityStatusInfo(@Nullable final Context context) {
        return createSectionConnectivityStatusInfo(context, true, true, true, true);
    }

    /**
     * See {@link #createSectionConnectivityStatusInfo(Context)}
     *
     * @param context             can be null, but will just return an empty list
     * @param includeNetworkState if network state should be included
     * @param includeWifiState    if wifi state should be included
     * @param includeBtState      if bluetooth state should be included
     * @param includeNfcState     if nfc state should be included
     * @return the section containing header, entries etc.
     */
    public static Section.HeaderSection createSectionConnectivityStatusInfo(@Nullable final Context context, boolean includeNetworkState, boolean includeWifiState, boolean includeBtState, boolean includeNfcState) {
        Section.ModifiableHeaderSection section = Hood.ext().createSection("Connectivity Status");

        if (context != null) {
            if (includeNetworkState) {
                section.add(Hood.get().createPropertyEntry("internet", new DynamicValue<String>() {
                    @Override
                    public String getValue() {
                        return String.valueOf(DeviceStatusUtil.getNetworkConnectivityState(context));
                    }
                }, Hood.ext().createOnClickActionStartIntent(new Intent(Settings.ACTION_SETTINGS)), false));
            }


            if (includeWifiState) {
                final DeviceStatusUtil.Status wifiState = DeviceStatusUtil.getWifiStatus(context);
                section.add(Hood.get().createPropertyEntry("wifi", new DynamicValue<String>() {
                    @Override
                    public String getValue() {
                        return String.valueOf(wifiState);
                    }
                }, wifiState == DeviceStatusUtil.Status.UNSUPPORTED ?
                        Hood.ext().createOnClickActionToast() :
                        Hood.ext().createOnClickActionStartIntent(new Intent(Settings.ACTION_WIFI_SETTINGS)), false));
            }

            if (includeBtState) {
                final DeviceStatusUtil.Status btState = DeviceStatusUtil.getBluetoothStatus(context);
                section.add(Hood.get().createPropertyEntry("bluetooth", new DynamicValue<String>() {
                    @Override
                    public String getValue() {
                        return String.valueOf(btState);
                    }
                }, btState == DeviceStatusUtil.Status.UNSUPPORTED ?
                        Hood.ext().createOnClickActionToast() :
                        Hood.ext().createOnClickActionStartIntent(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS)), false));
            }

            if (includeNfcState) {
                final DeviceStatusUtil.Status nfcState = DeviceStatusUtil.getNfcState(context);
                section.add(Hood.get().createPropertyEntry("nfc", new DynamicValue<String>() {
                    @Override
                    public String getValue() {
                        return String.valueOf(nfcState);
                    }
                }, nfcState == DeviceStatusUtil.Status.UNSUPPORTED || Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN ?
                        Hood.ext().createOnClickActionToast() :
                        Hood.ext().createOnClickActionStartIntent(new Intent(Settings.ACTION_NFC_SETTINGS)), false));
            }
        }
        return section;
    }

    /**
     * Convince feature to add state of multiple system features.
     * Uses {@link PackageManager#hasSystemFeature(String)} call.
     * <p>
     * See https://developer.android.com/guide/topics/manifest/uses-feature-element.html#features-reference for
     * available system features.
     *
     * @param context               can be null, but will just return an empty list
     * @param labelSystemFeatureMap a map which has ui labels as key and android system feature string
     *                              (as returned as name by {@link PackageManager#getSystemAvailableFeatures()}) as value
     * @return list of page-entries (one for each map entry)
     */
    public static List<PageEntry<?>> createSystemFeatureInfo(@Nullable Context context, Map<CharSequence, String> labelSystemFeatureMap) {
        List<PageEntry<?>> entries = new ArrayList<>();
        if (context != null) {

            for (Map.Entry<CharSequence, String> entry : labelSystemFeatureMap.entrySet()) {
                boolean supported;
                if (entry.getValue().matches("^-?\\d+$")) {
                    final ConfigurationInfo configurationInfo = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getDeviceConfigurationInfo();
                    supported = configurationInfo.reqGlEsVersion >= Integer.valueOf(entry.getValue());
                } else {
                    supported = context.getPackageManager().hasSystemFeature(entry.getValue());
                }
                entries.add(Hood.get().createPropertyEntry(entry.getKey(), String.valueOf(supported)));
            }
        }
        return entries;
    }

    /**
     * Returns a list of the most important {@link TelephonyManager} states and ids. Will only
     * work if the app has the {@link Manifest.permission#READ_PHONE_STATE} permission or just
     * returns an empty list.
     *
     * @param context can be null, but will just return an empty list
     * @return the list of entries
     */
    //@RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static Section.HeaderSection createSectionTelephonyManger(@Nullable Context context) {
        Section.ModifiableHeaderSection section = Hood.ext().createSection("Telephony Status");
        if (context != null) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                section.setErrorMessage("Cannot display data - requires READ_PHONE_STATE permission.");
            } else {
                final TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                section.add(Hood.get().createPropertyEntry("sim-serial", new DynamicValue<String>() {
                    @Override
                    public String getValue() {
                        return telephonyManager.getSimSerialNumber();
                    }
                }));
                section.add(Hood.get().createPropertyEntry("sim-state", new DynamicValue<String>() {
                    @Override
                    public String getValue() {
                        return TypeTranslators.translateSimState(telephonyManager.getSimState());
                    }
                }));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    section.add(Hood.get().createPropertyEntry("sim-slots", new DynamicValue<String>() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public String getValue() {
                            return String.valueOf(telephonyManager.getPhoneCount());
                        }
                    }));
                }
                section.add(Hood.get().createPropertyEntry("subscriber-id", new DynamicValue<String>() {
                    @Override
                    public String getValue() {
                        return telephonyManager.getSubscriberId();
                    }
                }));
                section.add(Hood.get().createPropertyEntry("operator", new DynamicValue<String>() {
                    @Override
                    public String getValue() {
                        return telephonyManager.getNetworkOperatorName();
                    }
                }));
                section.add(Hood.get().createPropertyEntry("connection-type", new DynamicValue<String>() {
                    @Override
                    public String getValue() {
                        return TypeTranslators.translateTelephonyNetworkType(telephonyManager.getNetworkType());
                    }
                }));
                section.add(Hood.get().createPropertyEntry("MSISDN", new DynamicValue<String>() {
                    @Override
                    public String getValue() {
                        return telephonyManager.getLine1Number();
                    }
                }));
                section.add(Hood.get().createPropertyEntry("IMEI", new DynamicValue<String>() {
                    @Override
                    public String getValue() {
                        return telephonyManager.getDeviceId();
                    }
                }));
            }
        }
        return section;
    }

    /**
     * Adds info for current {@link StrictMode} config. Currently only shows the bitmask used to
     * store the policies states. Unfortunately there is not much public API to examine StrictMode
     *
     * @return section
     */
    public static Section.HeaderSection createSectionStrictMode() {
        Section.ModifiableHeaderSection section = Hood.ext().createSection("StrictMode");
        section.add(Hood.get().createPropertyEntry("thread-policy", new DynamicValue<String>() {
            @Override
            public String getValue() {
                return String.valueOf(StrictMode.getThreadPolicy());
            }
        }, true));
        section.add(Hood.get().createPropertyEntry("vm-policy", new DynamicValue<String>() {
            @Override
            public String getValue() {
                return StrictMode.getVmPolicy().toString();
            }
        }, true));
        return section;
    }

    public static Section.HeaderSection createSectionAndroidDebugSettings(final Context context) {
        Section.ModifiableHeaderSection section = Hood.ext().createSection("Android Debug Settings");
        if (context != null) {
            section.add(Hood.get().createPropertyEntry("developer-mode", new DynamicValue<String>() {
                @Override
                public String getValue() {
                    int settingsInt;
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        settingsInt = Settings.Global.getInt(context.getContentResolver(), Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0);
                    } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                        settingsInt = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.DEVELOPMENT_SETTINGS_ENABLED, 0);
                    } else {
                        return "unknown (SDK 16+ only)";
                    }
                    return String.valueOf(settingsInt == 1);
                }
            }, DefaultButtonDefinitions.getDevSettingsAction().onClickAction, false));
            section.add(Hood.get().createPropertyEntry("usb-debugging", new DynamicValue<String>() {
                @Override
                public String getValue() {
                    int settingsInt;
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        settingsInt = Settings.Global.getInt(context.getContentResolver(), Settings.Global.ADB_ENABLED, 0);
                    } else {
                        settingsInt = Settings.System.getInt(context.getContentResolver(), Settings.System.ADB_ENABLED, 0);
                    }
                    return String.valueOf(settingsInt == 1);
                }
            }, DefaultButtonDefinitions.getDevSettingsAction().onClickAction, false));
            section.add(Hood.get().createPropertyEntry("don't keep activities", new DynamicValue<String>() {
                @Override
                public String getValue() {
                    int settingsInt;
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        settingsInt = Settings.Global.getInt(context.getContentResolver(), Settings.Global.ALWAYS_FINISH_ACTIVITIES, 0);
                    } else {
                        settingsInt = Settings.System.getInt(context.getContentResolver(), Settings.System.ALWAYS_FINISH_ACTIVITIES, 0);
                    }
                    return String.valueOf(settingsInt == 1);
                }
            }, DefaultButtonDefinitions.getDevSettingsAction().onClickAction, false));
        }
        return section;
    }

    /**
     * Convenience to create a CI and source control section. Pass everything you want and keep the rest null.
     *
     * @param scmRev      git hash e.g. "git log -1 --format=%H"
     * @param scmBranch   git branch e.g. "git rev-parse --abbrev-ref HEAD"
     * @param ciBuildId   build number
     * @param ciBuildJob  job name
     * @param ciBuildTime time on ci server
     * @return the section containing all the data
     */
    public static Section.HeaderSection createSectionSourceControlAndCI(@Nullable String scmRev, @Nullable String scmBranch, @Nullable String scmCommitDate,
                                                                        @Nullable String ciBuildId, @Nullable String ciBuildJob, @Nullable String ciBuildTime) {
        Section.ModifiableHeaderSection section = Hood.ext().createSection("Source Control & CI");
        if (scmBranch != null) {
            section.add(Hood.get().createPropertyEntry("scm-branch", scmBranch));
        }
        if (scmRev != null) {
            section.add(Hood.get().createPropertyEntry("scm-rev", scmRev));
        }
        if (scmCommitDate != null) {
            section.add(Hood.get().createPropertyEntry("scm-date", scmCommitDate));
        }
        if (ciBuildJob != null) {
            section.add(Hood.get().createPropertyEntry("ci-job", ciBuildJob));
        }
        if (ciBuildId != null) {
            section.add(Hood.get().createPropertyEntry("ci-build-id", ciBuildId));
        }
        if (ciBuildTime != null) {
            section.add(Hood.get().createPropertyEntry("ci-build-time", ciBuildTime));
        }
        return section;
    }

    /**
     * Creates a section with current battery status and health
     *
     * @param context can be null, but will just return an empty section
     * @return section
     */
    public static Section.HeaderSection createSectionBatteryInfo(@Nullable final Context context) {
        Section.ModifiableHeaderSection section = Hood.ext().createSection("Battery Info");
        if (context != null) {
            final IntentFilter battIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            final Intent batteryStatus = context.registerReceiver(null, battIntentFilter);
            if (batteryStatus != null) {
                section.add(Hood.get().createPropertyEntry("level", new DynamicValue<String>() {
                    @Override
                    public String getValue() {
                        final Intent batteryStatus = context.registerReceiver(null, battIntentFilter);
                        float level = (float) batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                        float maxScale = (float) batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                        float battPercent = level / maxScale * 100;
                        return String.valueOf(battPercent) + "%";
                    }
                }, DefaultButtonDefinitions.getBatterySummarySettingsAction().onClickAction, false));
                section.add(Hood.get().createPropertyEntry("status", new DynamicValue<String>() {
                    @Override
                    public String getValue() {
                        final Intent batteryStatus = context.registerReceiver(null, battIntentFilter);
                        return TypeTranslators.translateBatteryStatus(batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1))
                                + " (" + TypeTranslators.translateBatteryPlugged(batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)) + ")";
                    }
                }));
                section.add(Hood.get().createPropertyEntry("health", new DynamicValue<String>() {
                    @Override
                    public String getValue() {
                        final Intent batteryStatus = context.registerReceiver(null, battIntentFilter);
                        return TypeTranslators.translateBatteryHealth(batteryStatus.getIntExtra(BatteryManager.EXTRA_HEALTH, -1));
                    }
                }));
                section.add(Hood.get().createPropertyEntry("temp/volt", new DynamicValue<String>() {
                            @Override
                            public String getValue() {
                                final Intent batteryStatus = context.registerReceiver(null, battIntentFilter);
                                return String.valueOf((float) batteryStatus.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1) / 10f + "°C/" +
                                        String.valueOf(batteryStatus.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1))) + "mV";
                            }
                        }
                ));
                section.add(Hood.get().createPropertyEntry("batt-saving", new DynamicValue<String>() {
                    @Override
                    public String getValue() {
                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
                            return String.valueOf(powerManager.isPowerSaveMode());
                        }
                        return "unsupported";
                    }
                }, DefaultButtonDefinitions.getBatterySaverSettingsAction().onClickAction, false));
            }
        }
        return section;
    }

    /**
     * Converts a {@link Properties} object to page-entries
     *
     * @param properties
     * @return list of page-entries
     */
    public static List<PageEntry<?>> createPropertiesEntries(@NonNull Properties properties) {
        return createFromMap(properties);
    }

    /**
     * Converts an arbitrary map to page-entries. Uses {@link #toString()} on the contained objects.
     *
     * @param hashMap
     * @return list of page-entries
     */
    public static List<PageEntry<?>> createFromMap(Map<?, ?> hashMap) {
        return createFromEntrySet(hashMap.entrySet());
    }

    private static List<PageEntry<?>> createFromEntrySet(Set<? extends Map.Entry<?, ?>>
                                                                 entrySet) {
        List<PageEntry<?>> entries = new ArrayList<>();
        for (Map.Entry propEntry : entrySet) {
            if (propEntry != null && propEntry.getKey() != null && propEntry.getValue() != null) {
                entries.add(Hood.get().createPropertyEntry(propEntry.getKey().toString(), propEntry.getValue().toString()));
            }
        }
        return entries;
    }
}
