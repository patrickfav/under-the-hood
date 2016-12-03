package at.favre.lib.hood.util;

import android.telephony.TelephonyManager;

public class TypeTranslators {
    public static String translateSimState(int simstate) {
        switch (simstate) {
            case TelephonyManager.SIM_STATE_READY:
                return "STATE_READY";
            case TelephonyManager.SIM_STATE_PUK_REQUIRED:
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
            case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                return "STATE_LOCKED (" + simstate + ")";
            case TelephonyManager.SIM_STATE_ABSENT:
                return "STATE_ABSENT";
            default:
            case TelephonyManager.SIM_STATE_UNKNOWN:
                return "UNKNOWN (" + simstate + ")";
        }
    }

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
}
