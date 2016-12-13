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

import at.favre.lib.hood.defaults.DefaultProperties;
import at.favre.lib.hood.page.Config;
import at.favre.lib.hood.page.DebugPage;
import at.favre.lib.hood.page.Page;
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
        Page page = DebugPage.Factory.create(new Config.Builder().setShowZebra(false).build());
        page.add(DefaultProperties.createAppVersionInfo(at.favre.lib.hood.BuildConfig.class, true));
        page.add(DefaultProperties.createSignatureHashInfo(this));
        page.add(DefaultProperties.createBasicDeviceInfo(true));
        page.add(DefaultProperties.createSectionRuntimePermissions(this, false));
        page.add(DefaultProperties.createConnectivityStatusInfo(this, true));

        return page;
    }
}
