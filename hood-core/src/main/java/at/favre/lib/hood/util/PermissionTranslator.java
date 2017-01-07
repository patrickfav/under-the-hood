package at.favre.lib.hood.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Gets current runtime permission status
 */
public class PermissionTranslator {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({GRANTED, GRANTED_ON_INSTALL, DENIED, BLOCKED})
    public @interface PermissionState {
    }

    public static final int GRANTED = 0;
    public static final int GRANTED_ON_INSTALL = 1;
    public static final int DENIED = 2;
    public static final int BLOCKED = 3;

    /**
     * Gets the current status. Caveat: returns {@link #BLOCKED} the first time before the user accepts or denies a permission
     *
     * @param activity              needed for the check
     * @param androidPermissionName the permission name as written in manifest
     * @return the current state
     */
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


}
