package at.favre.lib.hood;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import at.favre.lib.hood.data.DefaultActions;
import at.favre.lib.hood.data.DefaultProperties;
import at.favre.lib.hood.views.ChangeableValue;
import at.favre.lib.hood.views.ConfigBoolEntry;
import at.favre.lib.hood.views.DebugViewPage;
import at.favre.lib.hood.views.Page;

public class HoodActivity extends AppCompatActivity {

    private HoodDebugView debugView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hood);

        debugView = (HoodDebugView) findViewById(R.id.debug_view);
        debugView.setPageData(getPageData());

        debugView.log("test");
    }

    public Page getPageData() {
        DebugViewPage page = new DebugViewPage();

        page.addEntries(DefaultProperties.createAppVersionInfo(BuildConfig.class, true));
        page.addEntries(DefaultProperties.createSignatureHashInfo(this));

        page.addEntry(new ConfigBoolEntry(new ConfigBoolEntry.BoolConfigAction("enable some debug feat", new ChangeableValue<Boolean>() {
            private boolean debug = false;

            @Override
            public Boolean getValue() {
                return debug;
            }

            @Override
            public void onChange(Boolean value) {
                debug = value;
            }
        })));

        page.addAction(DefaultActions.getAppInfoAction(this));

        page.addEntries(DefaultProperties.createDeviceInfo(true));
        page.addEntries(DefaultProperties.createDeviceInfo(true));
        page.addEntries(DefaultProperties.createDeviceInfo(true));

        page.addEntry(new ConfigBoolEntry(new ConfigBoolEntry.BoolConfigAction("enable some other debug feat", new ChangeableValue<Boolean>() {
            private boolean debug = true;

            @Override
            public Boolean getValue() {
                return debug;
            }

            @Override
            public void onChange(Boolean value) {
                debug = value;
            }
        })));

        page.addAction(DefaultActions.getAppInfoAction(this), DefaultActions.getUninstallAction(this));
        page.addEntries(DefaultProperties.createDeviceInfo(true));

        return page;
    }
}
