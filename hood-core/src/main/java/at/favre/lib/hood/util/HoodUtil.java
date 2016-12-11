package at.favre.lib.hood.util;


import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.IntDef;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;

import at.favre.lib.hood.R;


public class HoodUtil {
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
    @IntDef({GRANTED, GRANTED_ON_INSTALL, DENIED, BLOCKED})
    public @interface PermissionState {
    }

    public static final int GRANTED = 0;
    public static final int GRANTED_ON_INSTALL = 1;
    public static final int DENIED = 2;
    public static final int BLOCKED = 3;

    @PermissionState
    public static int getPermissionStatus(Activity activity, String androidPermissionName) {
        if (ContextCompat.checkSelfPermission(activity, androidPermissionName) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, androidPermissionName)) {
                return BLOCKED;
            }
            return DENIED;
        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return GRANTED;
        } else {
            return GRANTED_ON_INSTALL;
        }
    }

    public static void setZebraToView(View view, @ColorInt int zebraColor, boolean isOdd) {
        Drawable zebra = null;

        if (isOdd) {
            zebra = new ColorDrawable(zebraColor);
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

    public static Locale getCurrentLocale(Context ctx) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return ctx.getResources().getConfiguration().getLocales().get(0);
        } else {
            //noinspection deprecation
            return ctx.getResources().getConfiguration().locale;
        }
    }

    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format(Locale.US, "%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }
}
