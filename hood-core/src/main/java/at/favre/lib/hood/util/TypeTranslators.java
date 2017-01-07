package at.favre.lib.hood.util;

import android.content.pm.PackageInfo;
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
            default:
                return "UNKNOWN (" + installLocationType + ")";
        }
    }
}
