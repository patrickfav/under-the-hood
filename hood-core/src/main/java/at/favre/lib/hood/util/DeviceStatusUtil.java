package at.favre.lib.hood.util;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.support.annotation.RequiresPermission;
import android.support.v4.content.ContextCompat;

public class DeviceStatusUtil {
    public enum Status {
        UNSUPPORTED, NEEDS_PERMISSION, ENABLED, DISABLED
    }

    public enum ConnectionState {
        PERMISSION_NEEDED, DISCONNECTED, CONNECTED_WIFI, CONNECTED_MOBILE, CONNECTED_ETHERNET, CONNECTED_BT, ONNECTED_VPN, CONNECTED_OTHER
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static ConnectionState getNetworkConnectivityState(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

            if (activeNetwork != null) {
                if (activeNetwork.isConnectedOrConnecting()) {
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                        return ConnectionState.CONNECTED_WIFI;
                    }
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE_DUN) {
                        return ConnectionState.CONNECTED_MOBILE;
                    }
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_ETHERNET) {
                        return ConnectionState.CONNECTED_ETHERNET;
                    }
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_BLUETOOTH) {
                        return ConnectionState.CONNECTED_BT;
                    }
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_VPN) {
                        return ConnectionState.CONNECTED_BT;
                    }
                    return ConnectionState.CONNECTED_OTHER;
                }
            }

            return ConnectionState.DISCONNECTED;
        }
        return ConnectionState.PERMISSION_NEEDED;
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH)
    public static Status getBluetoothStatus(Context context) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            return Status.UNSUPPORTED;
        } else if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            return Status.NEEDS_PERMISSION;
        } else if (bluetoothAdapter.isEnabled()) {
            return Status.ENABLED;
        } else {
            return Status.DISABLED;
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_WIFI_STATE)
    public static Status getWlanStatus(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifi == null) {
            return Status.UNSUPPORTED;
        } else if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
            return Status.NEEDS_PERMISSION;
        } else if (wifi.isWifiEnabled()) {
            return Status.ENABLED;
        } else {
            return Status.DISABLED;
        }
    }

    @RequiresPermission(Manifest.permission.NFC)
    public static Status getNfcState(Context context) {
        NfcManager nfcManager = (NfcManager) context.getSystemService(Context.NFC_SERVICE);
        NfcAdapter adapter = nfcManager.getDefaultAdapter();
        if (adapter == null) {
            return Status.UNSUPPORTED;
        } else if (ContextCompat.checkSelfPermission(context, Manifest.permission.NFC) != PackageManager.PERMISSION_GRANTED) {
            return Status.NEEDS_PERMISSION;
        } else if (adapter.isEnabled()) {
            return Status.ENABLED;
        } else {
            return Status.DISABLED;
        }
    }
}
