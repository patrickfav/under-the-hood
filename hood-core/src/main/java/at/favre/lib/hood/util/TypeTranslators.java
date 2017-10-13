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

package at.favre.lib.hood.util;

import android.content.pm.PackageInfo;
import android.os.BatteryManager;
import android.telephony.TelephonyManager;

/**
 * Translates Android int states to human-readable Strings
 */
public class TypeTranslators {

    /**
     * @param simState expected to be from {@link TelephonyManager#getSimState()}
     * @return human-readable state
     */
    public static String translateSimState(int simState) {
        switch (simState) {
            case TelephonyManager.SIM_STATE_READY:
                return "STATE_READY";
            case TelephonyManager.SIM_STATE_PUK_REQUIRED:
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
            case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                return "STATE_LOCKED (" + simState + ")";
            case TelephonyManager.SIM_STATE_ABSENT:
                return "STATE_ABSENT";
            default:
            case TelephonyManager.SIM_STATE_UNKNOWN:
                return "UNKNOWN (" + simState + ")";
        }
    }

    /**
     * @param networkType expected to from {@link TelephonyManager#getDataNetworkType()}
     * @return human-readable state
     */
    public static String translateTelephonyNetworkType(int networkType) {
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return "2G (" + networkType + ")";
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return "3G (" + networkType + ")";
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return "3.5G (" + networkType + ")";
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "4G (" + networkType + ")";
            default:
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return "UNKNOWN (" + networkType + ")";
        }
    }

    public static String translatePermissionState(@PermissionTranslator.PermissionState int permissionState) {
        switch (permissionState) {
            case PermissionTranslator.GRANTED:
                return "GRANTED";
            case PermissionTranslator.GRANTED_ON_INSTALL:
                return "GRANTED (INSTALL)";
            case PermissionTranslator.DENIED:
                return "DENIED";
            case PermissionTranslator.BLOCKED:
                return "BLOCKED/NOT ASKED";
            default:
                return "UNKNOWN";
        }
    }

    public static String translatePMInstallLocation(int installLocationType) {
        switch (installLocationType) {
            case PackageInfo.INSTALL_LOCATION_AUTO:
                return "AUTO";
            case PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY:
                return "INTERNAL_ONLY";
            case PackageInfo.INSTALL_LOCATION_PREFER_EXTERNAL:
                return "PREFER_EXTERNAL";
            case -1:
                return "UNSPECIFIED";
            default:
                return "UNKNOWN (" + installLocationType + ")";
        }
    }

    public static String translateBatteryStatus(int batteryStatus) {
        switch (batteryStatus) {
            case BatteryManager.BATTERY_STATUS_CHARGING:
                return "CHARGING";
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                return "DISCHARGING";
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                return "NOT CHARGING";
            case BatteryManager.BATTERY_STATUS_FULL:
                return "FULL";
            case BatteryManager.BATTERY_STATUS_UNKNOWN:
                return "STATUS UNKNOWN";
            default:
                return "UNKNOWN (" + batteryStatus + ")";
        }
    }

    public static String translateBatteryHealth(int batteryHealth) {
        switch (batteryHealth) {
            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                return "UNKNOWN HEALTH";
            case BatteryManager.BATTERY_HEALTH_GOOD:
                return "GOOD";
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                return "OVERHEAT";
            case BatteryManager.BATTERY_HEALTH_DEAD:
                return "DEAD";
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                return "OVER VOLTAGE";
            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                return "UNSPECIFIED FAILURE";
            case BatteryManager.BATTERY_HEALTH_COLD:
                return "COLD";
            default:
                return "UNKNOWN (" + batteryHealth + ")";
        }
    }

    public static String translateBatteryPlugged(int batteryPlugged) {
        switch (batteryPlugged) {
            case 0:
                return "UNPLUGGED";
            case BatteryManager.BATTERY_PLUGGED_AC:
                return "AC";
            case BatteryManager.BATTERY_PLUGGED_USB:
                return "USB";
            case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                return "WIRELESS";
            default:
                return "UNKNOWN (" + batteryPlugged + ")";
        }
    }
}
