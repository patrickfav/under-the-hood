package at.favre.lib.hood.defaults;

import android.content.SharedPreferences;

import at.favre.lib.hood.page.entries.ConfigBoolEntry;
import at.favre.lib.hood.page.values.ChangeableValue;

/**
 * Default implementations for Config* type entries
 */
public class DefaultConfigActions {

    /**
     * Used with {@link ConfigBoolEntry}. A changeable boolean value backed by shared preferences,
     * @param prefs shared preference containing the key
     * @param booleKey the key in the pref
     * @param defaultValue the default value if none is found
     * @return the action encapsolating t
     */
    public static ConfigBoolEntry.BoolConfigAction getBoolSharedPreferencesConfigAction(final SharedPreferences prefs, final String booleKey, final boolean defaultValue) {
        return getBoolSharedPreferencesConfigAction(prefs, booleKey, booleKey, defaultValue);
    }

    /**
     * Used with {@link ConfigBoolEntry}. A changeable boolean value backed by shared preferences,
     * @param prefs shared preference containing the key
     * @param booleKey the key in the pref
     * @param label label in the ui
     * @param defaultValue the default value if none is found
     * @return the action encapsolating t
     */
    public static ConfigBoolEntry.BoolConfigAction getBoolSharedPreferencesConfigAction(final SharedPreferences prefs, final String booleKey, String label, final boolean defaultValue) {
        return new ConfigBoolEntry.BoolConfigAction(label, new ChangeableValue<Boolean, Boolean>() {
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
}
