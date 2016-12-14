package at.favre.lib.hood.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import at.favre.lib.hood.page.Page;
import at.favre.lib.hood.page.entries.ActionEntry;
import at.favre.lib.hood.page.entries.HeaderEntry;
import at.favre.lib.hood.page.entries.KeyValueEntry;
import at.favre.lib.hood.page.values.DynamicValue;

/**
 * Convenience methods for modifying pages
 */
public class PageUtil {

    public void addProperty(@NonNull Page page, CharSequence key, DynamicValue<String> value) {
        page.add(new KeyValueEntry(key, value, false));
    }

    public static void addProperty(@NonNull Page page, CharSequence key, String value) {
        page.add(new KeyValueEntry(key, value));
    }

    public static void addTitle(@NonNull Page page, CharSequence title) {
        page.add(new HeaderEntry(title));
    }

    public static void addAction(@NonNull Page page, @Nullable ActionEntry.Action action) {
        if (action != null) {
            page.add(new ActionEntry(action));
        }
    }

    public static void addAction(@NonNull Page page, @Nullable ActionEntry.Action action1, @Nullable ActionEntry.Action action2) {
        if (action1 == null && action2 != null) {
            addAction(page, action2);
        } else if (action1 != null && action2 == null) {
            addAction(page, action1);
        } else if (action1 != null && action2 != null) {
            page.add(new ActionEntry(action1, action2));
        }
    }
}
