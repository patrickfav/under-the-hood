package at.favre.app.hood.demo;

import android.support.annotation.NonNull;

import at.favre.lib.hood.interfaces.Config;
import at.favre.lib.hood.interfaces.Pages;

public class DebugCustomThemeActivity extends DebugDarkMultiPageActivity {
    private static final String TAG = DebugCustomThemeActivity.class.getName();

    @NonNull
    @Override
    public Pages getPageData(@NonNull Pages pages) {
        return super.getPageData(pages);
    }

    @NonNull
    @Override
    public Config getConfig() {
        return Config.newBuilder().setLogTag(TAG).setAutoLog(false).setAutoRefresh(true).build();
    }
}
