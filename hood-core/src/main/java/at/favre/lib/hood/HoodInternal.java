package at.favre.lib.hood;

import android.app.Activity;
import android.content.Intent;

import java.util.List;

import at.favre.lib.hood.interfaces.PageEntry;
import at.favre.lib.hood.interfaces.Section;
import at.favre.lib.hood.interfaces.actions.OnClickAction;
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

    public OnClickAction createOnClickActionAskPermission(String perm, Activity activity) {
        return new KeyValueEntry.AskPermissionClickAction(perm, activity);
    }

    public OnClickAction createOnClickActionStartIntent(Intent intent) {
        return new KeyValueEntry.StartIntentAction(intent);
    }

    public OnClickAction createOnClickActionToast() {
        return new KeyValueEntry.ToastClickAction();
    }

    public CharSequence createFullLabel(CharSequence shortLabel, CharSequence fullLabel) {
        return new KeyValueEntry.Label(shortLabel, fullLabel);
    }
}
