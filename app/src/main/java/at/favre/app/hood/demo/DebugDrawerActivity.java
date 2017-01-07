package at.favre.app.hood.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.favre.lib.hood.Hood;
import at.favre.lib.hood.defaults.DefaultButtonDefinitions;
import at.favre.lib.hood.defaults.DefaultConfigActions;
import at.favre.lib.hood.defaults.DefaultProperties;
import at.favre.lib.hood.defaults.misc.Backend;
import at.favre.lib.hood.interfaces.Config;
import at.favre.lib.hood.interfaces.Page;
import at.favre.lib.hood.interfaces.Pages;
import at.favre.lib.hood.interfaces.actions.ButtonDefinition;
import at.favre.lib.hood.interfaces.actions.OnClickAction;
import at.favre.lib.hood.interfaces.values.SpinnerElement;
import at.favre.lib.hood.util.PackageInfoAssembler;
import at.favre.lib.hood.util.PageUtil;
import at.favre.lib.hood.view.HoodController;
import at.favre.lib.hood.view.HoodDebugPageView;

public class DebugDrawerActivity extends AppCompatActivity implements HoodController {
    private static final int DRAWER_POSITION = GravityCompat.END;

    private DrawerLayout drawerLayout;
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
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        hoodDebugPageView = (HoodDebugPageView) findViewById(R.id.left_drawer);
        hoodDebugPageView.setPageData(getPages());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        findViewById(R.id.toggle_drawer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(DRAWER_POSITION);
            }
        });
        drawerLayout.setOnTouchListener(hoodDebugPageView.getTouchInterceptorListener());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @NonNull
    @Override
    public Pages getPages() {
        Pages pages = Hood.createPages(new Config.Builder().setShowHighlightContent(false).build());
        Page firstPage = pages.addNewPage("Debug Info");
        firstPage.add(DefaultProperties.createSectionAppVersionInfoFromBuildConfig(at.favre.lib.hood.BuildConfig.class));
        firstPage.add(DefaultProperties.createSectionBasicDeviceInfo());
        firstPage.add(DefaultProperties.createSectionConnectivityStatusInfo(this));
        firstPage.add(new PackageInfoAssembler(PackageInfoAssembler.Type.APK_INSTALL_INFO, PackageInfoAssembler.Type.PERMISSIONS, PackageInfoAssembler.Type.SIGNATURE).createSection(this, true));

        Page secondPage = pages.addNewPage("Debug Features");
        PageUtil.addHeader(secondPage, "System Features");
        Map<CharSequence, String> systemFeatureMap = new HashMap<>();
        systemFeatureMap.put("hasHce", "android.hardware.nfc.hce");
        systemFeatureMap.put("hasCamera", "android.hardware.camera");
        systemFeatureMap.put("hasWebview", "android.software.webview");
        secondPage.add(DefaultProperties.createSystemFeatureInfo(this, systemFeatureMap));

        secondPage.add(Hood.createHeaderEntry("Debug Config"));
        secondPage.add(Hood.createSwitchEntry(DefaultConfigActions.getBoolSharedPreferencesConfigAction(getPreferences(MODE_PRIVATE), "KEY_TEST", "Enable debug feat#1", false)));
        secondPage.add(Hood.createSwitchEntry(DefaultConfigActions.getBoolSharedPreferencesConfigAction(getPreferences(MODE_PRIVATE), "KEY_TEST2", "Enable debug feat#2", false)));
        secondPage.add(Hood.createSwitchEntry(DefaultConfigActions.getBoolSharedPreferencesConfigAction(getPreferences(MODE_PRIVATE), "KEY_TEST3", "Enable debug feat#3", false)));
        secondPage.add(Hood.createSpinnerEnry(DefaultConfigActions.getDefaultSharedPrefBackedSpinnerAction(null, getPreferences(MODE_PRIVATE), "BACKEND_ID", null, getBackendElements())));

        PageUtil.addAction(secondPage, new ButtonDefinition("Test Loading", new OnClickAction() {
            @Override
            public void onClick(final View view, Map.Entry<CharSequence, String> value) {
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
        }));
        secondPage.add(Hood.createActionEntry(DefaultButtonDefinitions.getCrashAction()));
        secondPage.add(Hood.createActionEntry(DefaultButtonDefinitions.getKillProcessAction(this), DefaultButtonDefinitions.getClearAppDataAction()));
        secondPage.add(Hood.createActionEntry(DefaultButtonDefinitions.getKillProcessAction(this)));

        return pages;
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

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(DRAWER_POSITION)) {
            drawerLayout.closeDrawer(DRAWER_POSITION, true);
        } else {
            super.onBackPressed();
        }
    }
}
