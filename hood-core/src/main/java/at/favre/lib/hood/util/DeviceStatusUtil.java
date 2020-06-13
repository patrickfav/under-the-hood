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

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

/**
 * Reads and abstracts Android OS device states (wifi, bt, etc.)
 */
public final class DeviceStatusUtil {
    public enum Status {
        UNSUPPORTED, NEEDS_PERMISSION, ENABLED, DISABLED
    }

    public enum ConnectionState {
        PERMISSION_NEEDED, DISCONNECTED, CONNECTED_WIFI, CONNECTED_MOBILE, CONNECTED_ETHERNET, CONNECTED_BT, CONNECTED_VPN, CONNECTED_OTHER
    }

    private DeviceStatusUtil() {
    }

    /**
     * The current network (ie. internet) connectivity state. Needs correct permission to work.
     *
     * @param context
     * @return state
     */
    //@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static ConnectionState getNetworkConnectivityState(@NonNull Context context) {
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

    /**
     * Current BT hardware state. Needs correct permission to work.
     *
     * @param context
     * @return state
     */
    //@RequiresPermission(Manifest.permission.BLUETOOTH)
    public static Status getBluetoothStatus(@NonNull Context context) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            return Status.UNSUPPORTED;
        } else if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            return Status.NEEDS_PERMISSION;
        } else //noinspection MissingPermission
            if (bluetoothAdapter.isEnabled()) {
                return Status.ENABLED;
            } else {
                return Status.DISABLED;
            }
    }

    /**
     * Current Wifi hardware state. Needs correct permission to work.
     *
     * @param context
     * @return state
     */
    //@RequiresPermission(Manifest.permission.ACCESS_WIFI_STATE)
    public static Status getWifiStatus(Context context) {
        WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
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

    /**
     * Current NFC hardware state. Needs correct permission to work.
     *
     * @param context
     * @return state
     */
    //@RequiresPermission(Manifest.permission.NFC)
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
