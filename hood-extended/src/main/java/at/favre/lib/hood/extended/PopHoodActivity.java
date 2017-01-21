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

    /**
     * Starts the activity with given settings.
     * <p>
     * See {@link #createIntent(Context, Class)}
     */
    public static void start(Context context, Class<?> activityClass) {
        context.startActivity(createIntent(context, activityClass));
    }

    /**
     * Creates the intent for starting this
     *
     * @param context           non-null
     * @param activityClass     the actual implementation class (cannot be figured out in static context)
     * @return the intent ready to start
     */
    public static Intent createIntent(@NonNull Context context, Class<?> activityClass) {
        Intent starter = new Intent(context, activityClass);
        starter.putExtra(KEY_HEADLESS, false);
        starter.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return starter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Hood.isLibEnabled()) {
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

                debugView.addViewPagerChangeListener(new ViewPager.OnPageChangeListener() {
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
                    //noinspection deprecation
                    ((AppBarLayout) findViewById(R.id.app_bar_layout)).setTargetElevation(0);
                }
            }
        } else {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (debugView != null) {
            debugView.refresh();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.hoodlib_menu_pophood, menu);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        debugView.refresh();
    }

    protected Toolbar getToolbar() {
        return toolbar;
    }

    protected HoodDebugPageView getDebugView() {
        return debugView;
    }

    /**
     * Implement this method to pass a {@link Pages} filled with pages entries.
     *
     * @param pages use this to add entries (or create new one)
     * @return non-null set up page
     */
    @NonNull
    public abstract Pages getPageData(@NonNull Pages pages);

    /**
     * Create a config with this method. See {@link Config.Builder}
     *
     * @return the config
     */
    @NonNull
    public Config getConfig() {
        return Config.newBuilder().build();
    }


    @NonNull
    @Override
    public Pages getCurrentPagesFromThisView() {
        return debugView.getPages();
    }
}
