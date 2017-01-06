package at.favre.lib.hood;

import android.app.Activity;
import android.content.Intent;

import java.util.List;

import at.favre.lib.hood.interfaces.PageEntry;
import at.favre.lib.hood.interfaces.Section;
import at.favre.lib.hood.page.DefaultSection;
import at.favre.lib.hood.page.entries.KeyValueEntry;

public class HoodInternal {
    HoodInternal() {
    }

    public Section.ModifiableHeaderSection createSection(String header) {
        return new DefaultSection(header);
    }

    public Section.ModifiableHeaderSection createSection(String header, List<PageEntry<?>> entries) {
        return new DefaultSection(header, entries);
    }

    public KeyValueEntry.OnClickAction createOnClickActionAskPermission(String perm, Activity activity) {
        return new KeyValueEntry.AskPermissionClickAction(perm, activity);
    }

    public KeyValueEntry.OnClickAction createOnClickActionStartIntent(Intent intent) {
        return new KeyValueEntry.StartIntentAction(intent);
    }

    public KeyValueEntry.OnClickAction createOnClickActionToast() {
        return new KeyValueEntry.ToastClickAction();
    }
}
