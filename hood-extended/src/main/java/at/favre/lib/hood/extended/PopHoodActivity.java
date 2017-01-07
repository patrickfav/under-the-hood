package at.favre.lib.hood.extended;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import at.favre.lib.hood.Hood;
import at.favre.lib.hood.interfaces.Config;
import at.favre.lib.hood.interfaces.Pages;
import at.favre.lib.hood.util.defaults.DefaultMiscActions;
import at.favre.lib.hood.view.HoodController;
import at.favre.lib.hood.view.HoodDebugPageView;

public abstract class PopHoodActivity extends AppCompatActivity implements HoodController {
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
        if (getIntent().getBooleanExtra(KEY_HEADLESS, false)) {
            getPageData(getPageData(Hood.get().createPages(getConfig()))).logPages();
            finish();
        } else {
            setContentView(R.layout.hoodlib_activity_hood);
            debugView = (HoodDebugPageView) findViewById(R.id.debug_view);
            debugView.setPageData(getPageData(Hood.get().createPages(getConfig())));
            toolbar = ((Toolbar) findViewById(R.id.toolbar));
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            debugView.addViewPagerChangeListner(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    ((AppBarLayout) findViewById(R.id.app_bar_layout)).setExpanded(true, true);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && debugView.getPages().size() > 1) {
                //because of a lack of a better API to disable the elevation in the AppBarLayout, uses deprecated method
                ((AppBarLayout) findViewById(R.id.app_bar_layout)).setTargetElevation(0);
            }
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
            debugView.getPages().logPages();
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

    /**
     * Implement this method to pass a {@link Pages} filled with pages entries.
     *
     * @param emptyPages use this to add entries (or create new one)
     * @return non-null set up page
     */
    @NonNull
    public abstract Pages getPageData(@NonNull Pages emptyPages);

    /**
     * Create a config with this method. See {@link Config.Builder}
     *
     * @return the config
     */
    @NonNull
    public Config getConfig() {
        return new Config.Builder().build();
    }

    protected HoodDebugPageView getDebugView() {
        return debugView;
    }

    @NonNull
    @Override
    public Pages getPages() {
        return debugView.getPages();
    }
}
