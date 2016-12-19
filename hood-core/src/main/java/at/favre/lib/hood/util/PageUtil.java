package at.favre.lib.hood.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import at.favre.lib.hood.Hood;
import at.favre.lib.hood.interfaces.Page;
import at.favre.lib.hood.interfaces.actions.ButtonAction;
import at.favre.lib.hood.interfaces.values.DynamicValue;
import at.favre.lib.hood.page.entries.ActionEntry;

/**
 * Convenience methods for modifying pages
 */
public class PageUtil {

    public void addProperty(@NonNull Page page, CharSequence key, DynamicValue<String> value) {
        page.add(Hood.createPropertyEntry(key, value, false));
    }

    public static void addProperty(@NonNull Page page, CharSequence key, String value) {
        page.add(Hood.createPropertyEntry(key, value));
    }

    public static void addHeader(@NonNull Page page, CharSequence title) {
        page.add(Hood.createHeaderEntry(title));
    }

    public static void addAction(@NonNull Page page, @Nullable ButtonAction action) {
        if (action != null) {
            page.add(Hood.createActionEntry(action));
        }
    }

    public static void addAction(@NonNull Page page, @Nullable ButtonAction action1, @Nullable ButtonAction action2) {
        if (action1 == null && action2 != null) {
            addAction(page, action2);
        } else if (action1 != null && action2 == null) {
            addAction(page, action1);
        } else if (action1 != null && action2 != null) {
            page.add(new ActionEntry(action1, action2));
        }
    }
}
