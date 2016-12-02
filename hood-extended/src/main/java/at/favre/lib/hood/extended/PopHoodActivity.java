package at.favre.lib.hood.extended;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import at.favre.lib.hood.defaults.DefaultIntents;
import at.favre.lib.hood.page.Page;
import at.favre.lib.hood.views.HoodDebugPageView;

public abstract class PopHoodActivity extends AppCompatActivity {

    private HoodDebugPageView debugView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hood);

        debugView = (HoodDebugPageView) findViewById(R.id.debug_view);
        debugView.setPageData(getPageData());

        debugView.log("test");

        toolbar = ((Toolbar) findViewById(R.id.toolbar));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
            startActivity(DefaultIntents.getAppInfoIntent(this));
        } else if (i == R.id.action_uninstall) {
            startActivity(DefaultIntents.getAppUnisntallIntent(this));
        } else if (i == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        debugView.refresh();
    }

    protected abstract Page getPageData();
}
