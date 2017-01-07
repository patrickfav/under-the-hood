package at.favre.lib.hood;

import android.app.Activity;
import android.content.Intent;

import java.util.List;

import at.favre.lib.hood.interfaces.Config;
import at.favre.lib.hood.interfaces.HoodAPI;
import at.favre.lib.hood.interfaces.PageEntry;
import at.favre.lib.hood.interfaces.Pages;
import at.favre.lib.hood.interfaces.Section;
import at.favre.lib.hood.interfaces.actions.BoolConfigAction;
import at.favre.lib.hood.interfaces.actions.ButtonDefinition;
import at.favre.lib.hood.interfaces.actions.OnClickAction;
import at.favre.lib.hood.interfaces.actions.SingleSelectListConfigAction;
import at.favre.lib.hood.interfaces.values.DynamicValue;
import at.favre.lib.hood.page.DebugPages;
import at.favre.lib.hood.page.DefaultSection;
import at.favre.lib.hood.page.entries.ActionEntry;
import at.favre.lib.hood.page.entries.ConfigBoolEntry;
import at.favre.lib.hood.page.entries.ConfigSpinnerEntry;
import at.favre.lib.hood.page.entries.HeaderEntry;
import at.favre.lib.hood.page.entries.KeyValueEntry;
import at.favre.lib.hood.page.entries.TextMessageEntry;

/**
 * This is the main API. Use the factory methods instead of directly
 * instantiating the internal classes.
 */
public class Hood implements HoodAPI {
    private static HoodAPI.Internal internalInstance;
    private static HoodAPI instance;

    private Hood() {
    }

    public static HoodAPI get() {
        if (instance == null) {
            instance = new Hood();
        }
        return instance;
    }

    public static HoodAPI.Internal internal() {
        if (internalInstance == null) {
            internalInstance = new HoodInternalImpl();
        }
        return internalInstance;
    }

    @Override
    public Pages createPages(Config config) {
        return DebugPages.Factory.create(config);
    }

    @Override
    public PageEntry<?> createActionEntry(ButtonDefinition action) {
        return new ActionEntry(action);
    }

    @Override
    public PageEntry<?> createActionEntry(ButtonDefinition actionLeft, ButtonDefinition actionRight) {
        return new ActionEntry(actionLeft, actionRight);
    }

    @Override
    public PageEntry<?> createHeaderEntry(CharSequence header) {
        return new HeaderEntry(header);
    }

    @Override
    public PageEntry<?> createHeaderEntry(CharSequence header, boolean hideInLog) {
        return new HeaderEntry(header, hideInLog);
    }

    @Override
    public PageEntry<?> createMessageEntry(CharSequence message) {
        return new TextMessageEntry(message);
    }

    @Override
    public PageEntry<?> createSwitchEntry(BoolConfigAction action) {
        return new ConfigBoolEntry(action);
    }

    @Override
    public PageEntry<?> createSpinnerEntry(SingleSelectListConfigAction action) {
        return new ConfigSpinnerEntry(action);
    }

    @Override
    public PageEntry<?> createPropertyEntry(CharSequence key, DynamicValue<String> value, OnClickAction action, boolean multiLine) {
        return new KeyValueEntry(key, value, action, multiLine);
    }

    @Override
    public PageEntry<?> createPropertyEntry(CharSequence key, DynamicValue<String> value, boolean multiLine) {
        return new KeyValueEntry(key, value, multiLine);
    }

    @Override
    public PageEntry<?> createPropertyEntry(CharSequence key, DynamicValue<String> value) {
        return new KeyValueEntry(key, value);
    }

    @Override
    public PageEntry<?> createPropertyEntry(CharSequence key, String value, OnClickAction action, boolean multiLine) {
        return new KeyValueEntry(key, value, action, multiLine);
    }

    @Override
    public PageEntry<?> createPropertyEntry(CharSequence key, String value, boolean multiLine) {
        return new KeyValueEntry(key, value, multiLine);
    }

    @Override
    public PageEntry<?> createPropertyEntry(CharSequence key, String value) {
        return new KeyValueEntry(key, value);
    }

    private static class HoodInternalImpl implements HoodAPI.Internal {
        HoodInternalImpl() {
        }

        @Override
        public Section.ModifiableHeaderSection createSection(String header) {
            return new DefaultSection(header);
        }

        @Override
        public Section.ModifiableHeaderSection createSection(String header, List<PageEntry<?>> entries) {
            return new DefaultSection(header, entries);
        }

        @Override
        public OnClickAction createOnClickActionAskPermission(String perm, Activity activity) {
            return new KeyValueEntry.AskPermissionClickAction(perm, activity);
        }

        @Override
        public OnClickAction createOnClickActionStartIntent(Intent intent) {
            return new KeyValueEntry.StartIntentAction(intent);
        }

        @Override
        public OnClickAction createOnClickActionToast() {
            return new KeyValueEntry.ToastClickAction();
        }

        @Override
        public CharSequence createFullLabel(CharSequence shortLabel, CharSequence fullLabel) {
            return new KeyValueEntry.Label(shortLabel, fullLabel);
        }
    }
}
