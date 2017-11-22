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
public final class PermissionTranslator {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({GRANTED, GRANTED_ON_INSTALL, DENIED, BLOCKED})
    public @interface PermissionState {
    }

    public static final int GRANTED = 0;
    public static final int GRANTED_ON_INSTALL = 1;
    public static final int DENIED = 2;
    public static final int BLOCKED = 3;

    private PermissionTranslator() {
    }

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
