package at.favre.lib.hood.util.defaults;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import at.favre.lib.hood.interfaces.actions.BoolConfigAction;
import at.favre.lib.hood.interfaces.actions.SingleSelectListConfigAction;
import at.favre.lib.hood.interfaces.values.ChangeableValue;
import at.favre.lib.hood.interfaces.values.SpinnerElement;
import at.favre.lib.hood.interfaces.values.SpinnerValue;

/**
 * Default implementations for Config* type entries
 */
public class DefaultConfigActions {

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
            public List<SpinnerElement> getAlPossibleValues() {
                return elements;
            }
        });
    }
}
