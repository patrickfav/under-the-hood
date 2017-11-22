package at.favre.lib.hood.extended;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;

import java.util.Random;

import at.favre.lib.hood.Hood;
import at.favre.lib.hood.interfaces.Page;
import at.favre.lib.hood.interfaces.Pages;
import at.favre.lib.hood.util.PageUtil;
import at.favre.lib.hood.util.defaults.DefaultButtonDefinitions;
import at.favre.lib.hood.util.defaults.DefaultConfigActions;

public class TestActivity extends PopHoodActivity {
    private static final String KEY_PAGES_COUNT = "PAGES_COUNT";
    private static final String KEY_PAGE_COUNT = "PAGE_COUNT";
    private static final String KEY_SEED = "SEED";

    @Rule
    public ActivityTestRule<TestActivity> mActivityRule = new ActivityTestRule<>(TestActivity.class);

    public static void start(Context context, int pages, int page, long seed) {
        Intent starter = new Intent(context, TestActivity.class);
        starter.putExtra(KEY_PAGES_COUNT, pages);
        starter.putExtra(KEY_PAGE_COUNT, page);
        starter.putExtra(KEY_SEED, seed);
        context.startActivity(starter);
    }

    @NonNull
    @Override
    public Pages getPageData(@NonNull Pages pages) {
        final Random random = new Random(getIntent().getLongExtra(KEY_SEED, 0));

        for (int i = 0; i < getIntent().getIntExtra(KEY_PAGES_COUNT, 0); i++) {
            Page page = pages.addNewPage("Page " + i);

            for (int j = 0; j < getIntent().getIntExtra(KEY_PAGE_COUNT, 0); j++) {
                int rand = random.nextInt(15);

                if (rand == 0) {
                    page.add(Hood.get().createSwitchEntry(
                            DefaultConfigActions.getBoolSharedPreferencesConfigAction(
                                    getPreferences(MODE_PRIVATE), "KEY_TEST", "switch " + j, false)));
                } else if (rand == 1) {
                    page.add(Hood.get().createHeaderEntry("header" + j));
                } else if (rand == 2) {
                    PageUtil.addAction(page, DefaultButtonDefinitions.getGlobalSettingsAction(),
                            DefaultButtonDefinitions.getNfcSettingsAction());
                } else if (rand == 3) {
                    PageUtil.addAction(page, DefaultButtonDefinitions.getBatterySaverSettingsAction());
                } else {
                    page.add(Hood.get().createPropertyEntry("key " + j, "value " + j));
                }
            }
        }
        return pages;
    }
}
