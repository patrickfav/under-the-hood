package at.favre.lib.hood.defaults;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Some default actions (also used in other Default* classes)
 */
public class DefaultMiscActions {
    private static final String TAG = DefaultMiscActions.class.getName();

    /**
     * Raw intent to open app's uninstall prompt
     */
    public static Intent getAppUninstallIntent(Context ctx) {
        Intent intent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE);
        intent.setData(Uri.parse("package:" + ctx.getPackageName()));
        return intent;
    }

    /**
     * Raw intent to open app's info menu
     */
    public static Intent getAppInfoIntent(Context ctx) {
        try {
            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + ctx.getPackageName()));
            return intent;
        } catch (ActivityNotFoundException e) {
            //Open the generic Apps page:
            Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
            return intent;
        }
    }

    /**
     * Starts an {@link AlertDialog} prompt and if accepted will clear the app's data or just opens
     * the app's info menu if SDK < 19
     */
    public static void promptUserToClearData(final Context ctx) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            new AlertDialog.Builder(ctx)
                    .setTitle("Clear App Data")
                    .setMessage("Do you really want to clear the whole app data? This cannot be undone.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.KITKAT)
                        public void onClick(DialogInterface dialog, int whichButton) {
                            ((ActivityManager) ctx.getSystemService(ACTIVITY_SERVICE))
                                    .clearApplicationUserData();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();
        } else {
            ctx.startActivity(getAppInfoIntent(ctx));
        }
    }

    /**
     * Kills all associated processes
     */
    public static void killProcessesAround(Activity activity) {
        try {
            ActivityManager am = (ActivityManager) activity.getSystemService(ACTIVITY_SERVICE);
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
