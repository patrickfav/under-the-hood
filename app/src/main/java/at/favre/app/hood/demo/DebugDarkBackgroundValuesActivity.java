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

import android.os.SystemClock;

import androidx.annotation.NonNull;

import java.util.Random;

import at.favre.lib.hood.Hood;
import at.favre.lib.hood.extended.PopHoodActivity;
import at.favre.lib.hood.interfaces.Config;
import at.favre.lib.hood.interfaces.Page;
import at.favre.lib.hood.interfaces.Pages;
import at.favre.lib.hood.interfaces.values.DynamicValue;
import at.favre.lib.hood.util.PageUtil;
import at.favre.lib.hood.util.defaults.DefaultButtonDefinitions;
import at.favre.lib.hood.util.defaults.DefaultProperties;

public class DebugDarkBackgroundValuesActivity extends PopHoodActivity {
    private static final String TAG = DebugDarkBackgroundValuesActivity.class.getName();

    @NonNull
    @Override
    public Pages getPageData(@NonNull Pages pages) {
        Page firstPage = pages.addNewPage();

        firstPage.add(DefaultProperties.createSectionAppVersionInfoFromBuildConfig(BuildConfig.class));

        for (int i = 0; i < 35; i++) {
            final String id = String.valueOf(i);
            firstPage.add(Hood.get().createPropertyEntry("Test Loading " + id, new DynamicValue.Async<String>() {
                @Override
                public String getValue() {
                    int wait = new Random().nextInt(2000) + 25;
                    SystemClock.sleep(wait);
                    return id + " done (" + wait + "ms)";
                }
            }));
        }

        firstPage.add(Hood.get().createPropertyEntry("Test Loading ML", new DynamicValue.Async<String>() {
            @Override
            public String getValue() {
                int wait = new Random().nextInt(2000) + 25;
                SystemClock.sleep(wait);
                return "done ml (" + wait + "ms)";
            }
        }, Hood.ext().createOnClickActionToast(), true));

        PageUtil.addAction(firstPage, DefaultButtonDefinitions.getGlobalSettingsAction(), DefaultButtonDefinitions.getNfcSettingsAction());
        PageUtil.addAction(firstPage, DefaultButtonDefinitions.getNfcPaymentSettingsAction(), DefaultButtonDefinitions.getDevSettingsAction());
        PageUtil.addAction(firstPage, DefaultButtonDefinitions.getDateSettingsAction(), DefaultButtonDefinitions.getAirplaneModeSettingsAction());
        PageUtil.addAction(firstPage, DefaultButtonDefinitions.getSetLockScreenAction(), DefaultButtonDefinitions.getDeviceInfoSettingsAction());
        PageUtil.addAction(firstPage, DefaultButtonDefinitions.getBatterySaverSettingsAction(), DefaultButtonDefinitions.getDisplaySettingsAction());

        return pages;
    }

    @NonNull
    @Override
    public Config getConfig() {
        return Config.newBuilder()
                .setShowHighlightContent(false)
                .setLogTag(TAG).build();
    }
}
