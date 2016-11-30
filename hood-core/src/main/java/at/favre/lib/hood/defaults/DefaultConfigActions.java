package at.favre.lib.hood.defaults;

import android.content.SharedPreferences;

import at.favre.lib.hood.page.entries.ConfigBoolEntry;
import at.favre.lib.hood.page.values.ChangeableValue;

public class DefaultConfigActions {
    public static ConfigBoolEntry.BoolConfigAction getBoolSharedPreferencesConfigAction(final SharedPreferences prefs, final String booleKey, final boolean defaultValue) {
        return getBoolSharedPreferencesConfigAction(prefs, booleKey, booleKey, defaultValue);
    }

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
