package at.favre.lib.hood;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import at.favre.lib.hood.data.DefaultActions;
import at.favre.lib.hood.data.Defaults;
import at.favre.lib.hood.views.DebugDataAdapter;
import at.favre.lib.hood.views.Page;

public class HoodActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hood);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DebugDataAdapter mAdapter = new DebugDataAdapter(getPageData());
        mRecyclerView.setAdapter(mAdapter);
    }

    public Page getPageData() {
        Page page = new Page();

        page.addEntries(Defaults.createAppVersionInfo(BuildConfig.class, true));
        page.addEntries(Defaults.createSignatureHashInfo(this));

        page.addAction(DefaultActions.getAppInfoAction(this));

        page.addEntries(Defaults.createDeviceInfo(true));
        page.addEntries(Defaults.createDeviceInfo(true));
        page.addEntries(Defaults.createDeviceInfo(true));
        page.addAction(DefaultActions.getAppInfoAction(this), DefaultActions.getUninstallAction(this));
        page.addEntries(Defaults.createDeviceInfo(true));

        return page;
    }
}
