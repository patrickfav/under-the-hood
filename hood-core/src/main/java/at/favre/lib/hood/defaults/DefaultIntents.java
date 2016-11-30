package at.favre.lib.hood.defaults;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;


public class DefaultIntents {

    public static Intent getAppUnisntallIntent(Context ctx) {
        Intent intent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE);
        intent.setData(Uri.parse("package:" + ctx.getPackageName()));
        return intent;
    }

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
}
