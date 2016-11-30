package at.favre.lib.hood.util;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import at.favre.lib.hood.R;


public class HoodUtil {
    private static final String TAG = HoodUtil.class.getName();
    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String byteToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars).toLowerCase();
    }

    public static void copyToClipboard(String label, String value, Context ctx) {
        ClipboardManager clipboard = (ClipboardManager) ctx.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(label, value);
        clipboard.setPrimaryClip(clip);
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({GRANTED, DENIED, BLOCKED})
    public @interface PermissionStatus {
    }

    public static final int GRANTED = 0;
    public static final int DENIED = 1;
    public static final int BLOCKED = 2;

    @PermissionStatus
    public static int getPermissionStatus(Activity activity, String androidPermissionName) {
        if (ContextCompat.checkSelfPermission(activity, androidPermissionName) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, androidPermissionName)) {
                return BLOCKED;
            }
            return DENIED;
        }
        return GRANTED;
    }

    public static void setZebraToView(View view, boolean hasZebra) {
        Drawable zebra = null;

        if (hasZebra) {
            zebra = new ColorDrawable(ContextCompat.getColor(view.getContext(), R.color.zebra_color));
        }

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            view.findViewById(R.id.inner_wrapper).setBackgroundDrawable(zebra);
        } else {
            view.findViewById(R.id.inner_wrapper).setBackground(zebra);
        }
    }

    public static <T> T getConditionally(T object, boolean shouldReturnEntry) {
        return shouldReturnEntry ? object : null;
    }

    public static void killProcessesAround(Activity activity) {
        try {
            ActivityManager am = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
            String myProcessPrefix = activity.getApplicationInfo().processName;
            String myProcessName = activity.getPackageManager().getActivityInfo(activity.getComponentName(), 0).processName;
            for (ActivityManager.RunningAppProcessInfo proc : am.getRunningAppProcesses()) {
                if (proc.processName.startsWith(myProcessPrefix) && !proc.processName.equals(myProcessName)) {
                    android.os.Process.killProcess(proc.pid);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "could not kill process", e);
        } finally {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
