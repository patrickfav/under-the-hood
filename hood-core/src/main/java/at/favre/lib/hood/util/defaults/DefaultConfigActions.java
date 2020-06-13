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

package at.favre.lib.hood.util.defaults;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import at.favre.lib.hood.interfaces.actions.BoolConfigAction;
import at.favre.lib.hood.interfaces.actions.SingleSelectListConfigAction;
import at.favre.lib.hood.interfaces.values.ChangeableValue;
import at.favre.lib.hood.interfaces.values.SpinnerElement;
import at.favre.lib.hood.interfaces.values.SpinnerValue;

/**
 * Default implementations for Config* type entries
 */
public final class DefaultConfigActions {

    private DefaultConfigActions() {
    }

    /**
     * Used with {@link at.favre.lib.hood.interfaces.HoodAPI#createSwitchEntry(BoolConfigAction)}. A changeable boolean value backed by shared preferences,
     *
     * @param prefs        shared preference containing the key
     * @param booleKey     the key in the pref
     * @param defaultValue the default value if none is found
     * @return the action encapsulating
     */
    public static BoolConfigAction getBoolSharedPreferencesConfigAction(final SharedPreferences prefs, final String booleKey, final boolean defaultValue) {
        return getBoolSharedPreferencesConfigAction(prefs, booleKey, booleKey, defaultValue);
    }

    /**
     * Used with {@link at.favre.lib.hood.interfaces.HoodAPI#createSwitchEntry(BoolConfigAction)}. A changeable boolean value backed by shared preferences,
     *
     * @param prefs        shared preference containing the key
     * @param booleKey     the key in the pref
     * @param label        label in the ui
     * @param defaultValue the default value if none is found
     * @return the action encapsulating
     */
    public static BoolConfigAction getBoolSharedPreferencesConfigAction(final SharedPreferences prefs, final String booleKey, String label, final boolean defaultValue) {
        return new BoolConfigAction(label, new ChangeableValue<Boolean, Boolean>() {
            @Override
            public void onChange(Boolean value) {
                prefs.edit().putBoolean(booleKey, value).apply();
            }

            @Override
            public Boolean getValue() {
                return prefs.getBoolean(booleKey, defaultValue);
            }
        });
    }

    public static SingleSelectListConfigAction getDefaultSharedPrefBackedSpinnerAction(@Nullable String label, @NonNull final SharedPreferences prefs, final @NonNull String backendIdPrefKey, final @Nullable String defaultId, @NonNull final List<SpinnerElement> elements) {
        return new SingleSelectListConfigAction(label, new SpinnerValue<List<SpinnerElement>, SpinnerElement>() {

            @SuppressLint("CommitPrefEdits")
            @Override
            public void onChange(SpinnerElement value) {
                prefs.edit().putString(backendIdPrefKey, value.getId()).commit();
            }

            @Override
            public SpinnerElement getValue() {
                String currentId = prefs.getString(backendIdPrefKey, defaultId);
                if (currentId != null) {
                    for (SpinnerElement element : elements) {
                        if (currentId.equals(element.getId())) {
                            return element;
                        }
                    }
                }
                return null;
            }

            @Override
            public List<SpinnerElement> getAllPossibleValues() {
                return elements;
            }
        });
    }
}
