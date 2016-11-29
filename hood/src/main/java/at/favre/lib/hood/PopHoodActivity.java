package at.favre.lib.hood;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

import at.favre.lib.hood.defaults.DefaultActions;
import at.favre.lib.hood.defaults.DefaultProperties;
import at.favre.lib.hood.page.DebugPage;
import at.favre.lib.hood.page.Page;
import at.favre.lib.hood.page.entries.ConfigBoolEntry;
import at.favre.lib.hood.page.entries.ConfigSpinnerEntry;
import at.favre.lib.hood.page.entries.KeyValueEntry;
import at.favre.lib.hood.page.values.ChangeableValue;
import at.favre.lib.hood.page.values.SpinnerElement;
import at.favre.lib.hood.views.HoodDebugPageView;

public class PopHoodActivity extends AppCompatActivity {

    private HoodDebugPageView debugView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hood);

        debugView = (HoodDebugPageView) findViewById(R.id.debug_view);
        debugView.setPageData(getPageData());

        debugView.log("test");
    }

    public Page getPageData() {
        DebugPage page = new DebugPage();

        page.addEntries(DefaultProperties.createAppVersionInfo(BuildConfig.class, true));

        page.addEntry(new KeyValueEntry("MultiLine Test", "I am displaying text in a textview that appears to\n be too long to fit into one screen. \nI need to make my TextView scrollable. How can i do\nthat? Here is the code", true));

        page.addEntries(DefaultProperties.createSignatureHashInfo(this));

        page.addEntry(new ConfigBoolEntry(new ConfigBoolEntry.BoolConfigAction("enable some debug feat", new ChangeableValue<Boolean, Boolean>() {
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

        page.addEntry(new ConfigSpinnerEntry(new ConfigSpinnerEntry.SingleSelectListConfigAction(null, new ChangeableValue<List<SpinnerElement>, SpinnerElement>() {
            String backend;

            @Override
            public void onChange(SpinnerElement value) {
                backend = value.getId();
            }

            @Override
            public List<SpinnerElement> getValue() {
                return Arrays.asList(new SpinnerElement("1", "backend 1"), new SpinnerElement("2", "backend 2"), new SpinnerElement("3", "backend 3"), new SpinnerElement("4", "backend 4"));
            }
        })));

        page.addEntries(DefaultProperties.createDeviceInfo(true));
        page.addEntries(DefaultProperties.createRuntimePermissionInfo(this, true, Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA));

        page.addEntries(DefaultProperties.createDeviceInfo(true));

        page.addEntry(new ConfigBoolEntry(new ConfigBoolEntry.BoolConfigAction("enable some other debug feat", new ChangeableValue<Boolean, Boolean>() {
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

        page.addEntry(new ConfigSpinnerEntry(new ConfigSpinnerEntry.SingleSelectListConfigAction("Backends", new ChangeableValue<List<SpinnerElement>, SpinnerElement>() {
            String backend;

            @Override
            public void onChange(SpinnerElement value) {
                backend = value.getId();
            }

            @Override
            public List<SpinnerElement> getValue() {
                return Arrays.asList(new SpinnerElement("1", "backend 1"), new SpinnerElement("2", "backend 2"), new SpinnerElement("3", "backend 3"), new SpinnerElement("4", "backend 4"));
            }
        })));

        page.addAction(DefaultActions.getAppInfoAction(this), DefaultActions.getUninstallAction(this));


        return page;
    }
}
