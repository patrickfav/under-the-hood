/*
 *  Copyright 2016 Patrick Favre-Bulle
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package at.favre.app.hood.demo;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import at.favre.lib.hood.Hood;
import at.favre.lib.hood.extended.PopHoodActivity;
import at.favre.lib.hood.interfaces.Config;
import at.favre.lib.hood.interfaces.Page;
import at.favre.lib.hood.interfaces.Pages;
import at.favre.lib.hood.interfaces.values.SpinnerElement;
import at.favre.lib.hood.util.Backend;
import at.favre.lib.hood.util.PageUtil;
import at.favre.lib.hood.util.defaults.DefaultButtonDefinitions;
import at.favre.lib.hood.util.defaults.DefaultConfigActions;

public class DebugPerformanceTestActivity extends PopHoodActivity {
    private static final String TAG = DebugPerformanceTestActivity.class.getName();

    @NonNull
    @Override
    public Pages getPageData(@NonNull Pages pages) {
        final Random random = new Random();

        for (int i = 0; i < 10; i++) {
            Page page = pages.addNewPage("Page " + i);

            for (int j = 0; j < 1000; j++) {
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
                } else if (rand == 4) {
                    page.add(Hood.get().createSpinnerEntry(DefaultConfigActions.getDefaultSharedPrefBackedSpinnerAction("Backend", getPreferences(MODE_PRIVATE), "BACKEND_ID", null, getBackendElements())));
                } else {
                    page.add(Hood.get().createPropertyEntry("key " + j, "value " + j));
                }
            }
        }

        return pages;
    }

    @NonNull
    @Override
    public Config getConfig() {
        return Config.newBuilder()
                .setAutoLog(false)
                .setAutoRefresh(false)
                .setShowZebra(true)
                .setShowHighlightContent(false)
                .setLogTag(TAG).build();
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
