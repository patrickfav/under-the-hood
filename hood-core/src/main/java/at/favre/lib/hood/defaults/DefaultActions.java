package at.favre.lib.hood.defaults;


import android.app.Activity;
import android.view.View;

import at.favre.lib.hood.util.DebugCrashException;
import at.favre.lib.hood.page.entries.ActionEntry;

public class DefaultActions {

    public static ActionEntry.Action getAppInfoAction(final Activity activity) {
        return new ActionEntry.Action("App Info", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(DefaultIntents.getAppInfoIntent(activity));
            }
        });
    }

    public static ActionEntry.Action getUninstallAction(final Activity activity) {
        return new ActionEntry.Action("Uninstall", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(DefaultIntents.getAppUnisntallIntent(activity));
            }
        });
    }

    public static ActionEntry.Action getCrashAction() {
        return new ActionEntry.Action("Crash Activity", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throw new DebugCrashException();
            }
        });
    }
}
