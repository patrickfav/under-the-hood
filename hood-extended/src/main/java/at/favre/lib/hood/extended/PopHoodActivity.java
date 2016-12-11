package at.favre.lib.hood.extended;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import at.favre.lib.hood.defaults.DefaultMiscActions;
import at.favre.lib.hood.page.Config;
import at.favre.lib.hood.page.DebugPage;
import at.favre.lib.hood.page.Page;
import at.favre.lib.hood.views.HoodDebugPageView;
import at.favre.lib.hood.views.IHoodDebugController;

public abstract class PopHoodActivity extends AppCompatActivity implements IHoodDebugController {
    private static final String KEY_HEADLESS = "HEADLESS";

    private HoodDebugPageView debugView;
    private Toolbar toolbar;

    public static void start(Context context, Class<?> activityClass) {
        Intent starter = new Intent(context, activityClass);
        starter.putExtra(KEY_HEADLESS, false);
        starter.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Config config = getConfig();
        if (getIntent().getBooleanExtra(KEY_HEADLESS, false)) {
            getPageData(DebugPage.Factory.create(config)).logPage();
            finish();
        } else {
            setContentView(R.layout.hoodlib_activity_hood);
            debugView = (HoodDebugPageView) findViewById(R.id.debug_view);
            debugView.setPageData(getPageData(DebugPage.Factory.create(config)), config);
            toolbar = ((Toolbar) findViewById(R.id.toolbar));
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        debugView.refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_pophood, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_refresh) {
            debugView.refresh();
        } else if (i == R.id.action_app_info) {
            startActivity(DefaultMiscActions.getAppInfoIntent(this));
        } else if (i == R.id.action_uninstall) {
            startActivity(DefaultMiscActions.getAppUninstallIntent(this));
        } else if (i == R.id.action_kill_process) {
            DefaultMiscActions.killProcessesAround(this);
        } else if (i == R.id.action_clear_date) {
            DefaultMiscActions.promptUserToClearData(this);
        } else if (i == R.id.action_log) {
            debugView.getPage().logPage();
            Toast.makeText(this, R.string.hood_toast_log_to_console, Toast.LENGTH_SHORT).show();
        } else if (i == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        debugView.refresh();
    }

    protected Toolbar getToolbar() {
        return toolbar;
    }

    @NonNull
    @Override
    public abstract Page getPageData(@NonNull DebugPage emptyPage);

    @NonNull
    @Override
    public Config getConfig() {
        return new Config.Builder().build();
    }

    protected HoodDebugPageView getDebugView() {
        return debugView;
    }

    @NonNull
    @Override
    public Page getPage() {
        return debugView.getPage();
    }
}
