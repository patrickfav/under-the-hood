package at.favre.lib.hood.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import at.favre.lib.hood.Hood;
import at.favre.lib.hood.interfaces.Page;
import at.favre.lib.hood.interfaces.actions.ButtonDefinition;
import at.favre.lib.hood.interfaces.values.DynamicValue;

/**
 * Convenience methods for modifying pages. Most of them are null-safe.
 */
public class PageUtil {

    public void addProperty(@NonNull Page page, CharSequence key, DynamicValue<String> value) {
        page.add(Hood.get().createPropertyEntry(key, value, false));
    }

    public static void addProperty(@NonNull Page page, CharSequence key, String value) {
        page.add(Hood.get().createPropertyEntry(key, value));
    }

    public static void addHeader(@NonNull Page page, CharSequence title) {
        page.add(Hood.get().createHeaderEntry(title));
    }

    public static void addAction(@NonNull Page page, @Nullable ButtonDefinition action) {
        if (action != null) {
            page.add(Hood.get().createActionEntry(action));
        }
    }

    public static void addAction(@NonNull Page page, @Nullable ButtonDefinition action1, @Nullable ButtonDefinition action2) {
        if (action1 == null && action2 != null) {
            addAction(page, action2);
        } else if (action1 != null && action2 == null) {
            addAction(page, action1);
        } else if (action1 != null && action2 != null) {
            page.add(Hood.get().createActionEntry(action1, action2));
        }
    }
}
