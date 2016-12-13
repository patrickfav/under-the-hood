package at.favre.app.hoodtest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import at.favre.lib.hood.defaults.DefaultActions;
import at.favre.lib.hood.defaults.DefaultConfigActions;
import at.favre.lib.hood.defaults.DefaultProperties;
import at.favre.lib.hood.defaults.misc.Backend;
import at.favre.lib.hood.page.Config;
import at.favre.lib.hood.page.DebugPage;
import at.favre.lib.hood.page.Page;
import at.favre.lib.hood.page.entries.ActionEntry;
import at.favre.lib.hood.page.entries.ConfigBoolEntry;
import at.favre.lib.hood.page.entries.ConfigSpinnerEntry;
import at.favre.lib.hood.page.entries.HeaderEntry;
import at.favre.lib.hood.page.values.SpinnerElement;
import at.favre.lib.hood.views.HoodDebugPageView;
import at.favre.lib.hood.views.IHoodDebugController;

public class DebugDrawerActivity extends AppCompatActivity implements IHoodDebugController {

    private DrawerLayout mDrawerLayout;
    private HoodDebugPageView hoodDebugPageView;
    private Toolbar toolbar;

    public static void start(Context context) {
        Intent starter = new Intent(context, DebugDrawerActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debugdrawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        hoodDebugPageView = (HoodDebugPageView) findViewById(R.id.left_drawer);
        hoodDebugPageView.setPageData(getPage());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.toggle_drawer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(Gravity.RIGHT);
            }
        });
    }

    @NonNull
    @Override
    public Page getPage() {
        Page page = DebugPage.Factory.create(new Config.Builder().setShowHighlightContent(false).build());
        page.add(DefaultProperties.createAppVersionInfo(at.favre.lib.hood.BuildConfig.class, true));
        page.add(DefaultProperties.createSignatureHashInfo(this));
        page.add(DefaultProperties.createBasicDeviceInfo(true));

        page.add(new HeaderEntry("Debug Config"));
        page.add(new ConfigBoolEntry(DefaultConfigActions.getBoolSharedPreferencesConfigAction(getPreferences(MODE_PRIVATE), "KEY_TEST", "Enable debug feat#1", false)));
        page.add(new ConfigBoolEntry(DefaultConfigActions.getBoolSharedPreferencesConfigAction(getPreferences(MODE_PRIVATE), "KEY_TEST2", "Enable debug feat#2", false)));
        page.add(new ConfigBoolEntry(DefaultConfigActions.getBoolSharedPreferencesConfigAction(getPreferences(MODE_PRIVATE), "KEY_TEST3", "Enable debug feat#3", false)));
        page.add(new ConfigSpinnerEntry(DefaultConfigActions.getDefaultSharedPrefBackedSpinnerAction(null, getPreferences(MODE_PRIVATE), "BACKEND_ID", null, getBackendElements())));

        page.add(new ActionEntry(new ActionEntry.Action("Test Loading", new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                view.setEnabled(false);
                hoodDebugPageView.setProgressBarVisible(true);
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.setEnabled(true);
                        hoodDebugPageView.setProgressBarVisible(false);
                    }
                }, 3000);
            }
        })));
        page.add(new ActionEntry(DefaultActions.getCrashAction()));
        page.add(new ActionEntry(DefaultActions.getKillProcessAction(this), DefaultActions.getClearAppDataAction(this)));
        page.add(new ActionEntry(DefaultActions.getKillProcessAction(this)));

        page.add(DefaultProperties.createSectionRuntimePermissions(this, false));
        page.add(DefaultProperties.createConnectivityStatusInfo(this, true));

        return page;
    }

    private List<SpinnerElement> getBackendElements() {
        List<SpinnerElement> elements = new ArrayList<>();
        elements.add(new Backend(1, "dev.backend.com", 443));
        elements.add(new Backend(2, "dev2.backend.com", 80));
        elements.add(new Backend(3, "dev3.backend.com", 80));
        elements.add(new Backend(4, "stage.backend.com", 443));
        elements.add(new Backend(5, "prod.backend.com", 443));
        return elements;
    }
}
