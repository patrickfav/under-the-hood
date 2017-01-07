package at.favre.lib.hood;

import at.favre.lib.hood.interfaces.Config;
import at.favre.lib.hood.interfaces.PageEntry;
import at.favre.lib.hood.interfaces.Pages;
import at.favre.lib.hood.interfaces.actions.BoolConfigAction;
import at.favre.lib.hood.interfaces.actions.ButtonDefinition;
import at.favre.lib.hood.interfaces.actions.OnClickAction;
import at.favre.lib.hood.interfaces.actions.SingleSelectListConfigAction;
import at.favre.lib.hood.interfaces.values.DynamicValue;
import at.favre.lib.hood.page.DebugPages;
import at.favre.lib.hood.page.entries.ActionEntry;
import at.favre.lib.hood.page.entries.ConfigBoolEntry;
import at.favre.lib.hood.page.entries.ConfigSpinnerEntry;
import at.favre.lib.hood.page.entries.HeaderEntry;
import at.favre.lib.hood.page.entries.KeyValueEntry;
import at.favre.lib.hood.page.entries.TextMessageEntry;

public class Hood {
    private static final HoodInternal HOOD_INTERNAL = new HoodInternal();

    private Hood() {
    }

    public static Pages createPages(Config config) {
        return DebugPages.Factory.create(config);
    }

    /**
     * Single column action
     *
     * @param action
     */
    public static PageEntry<?> createActionEntry(ButtonDefinition action) {
        return new ActionEntry(action);
    }

    /**
     * Two columns with 2 different actions in a row
     *
     * @param actionLeft
     * @param actionRight
     */
    public static PageEntry<?> createActionEntry(ButtonDefinition actionLeft, ButtonDefinition actionRight) {
        return new ActionEntry(actionLeft, actionRight);
    }

    /**
     * Creates a header entry
     *
     * @param header as shown in ui
     */
    public static PageEntry<?> createHeaderEntry(CharSequence header) {
        return new HeaderEntry(header);
    }

    /**
     * Creates a header entry
     *
     * @param header    as shown in ui
     * @param hideInLog will omit it in log
     */
    public static PageEntry<?> createHeaderEntry(CharSequence header, boolean hideInLog) {
        return new HeaderEntry(header, hideInLog);
    }

    /**
     * Creates a simple, non-interactive text message
     *
     * @param message as shown in
     */
    public static PageEntry<?> createMessageEntry(CharSequence message) {
        return new TextMessageEntry(message);
    }

    /**
     * Creates an interactive switch like entry
     *
     * @param action
     */
    public static PageEntry<?> createSwitchEntry(BoolConfigAction action) {
        return new ConfigBoolEntry(action);
    }

    /**
     * Creates a single-select from list entry (ie. spinner or drop-down list)
     *
     * @param action
     */
    public static PageEntry<?> createSpinnerEnry(SingleSelectListConfigAction action) {
        return new ConfigSpinnerEntry(action);
    }

    /**
     * Creates Key-Value style page entry.
     *
     * @param key       as shown in ui
     * @param value     dynamic value (e.g. from {@link android.content.SharedPreferences}
     * @param action    used when clicked on
     * @param multiLine if a different layout should be used for long values
     */
    public static PageEntry<?> createPropertyEntry(CharSequence key, DynamicValue<String> value, OnClickAction action, boolean multiLine) {
        return new KeyValueEntry(key, value, action, multiLine);
    }

    /**
     * Creates Key-Value style page entry. Uses dialog as default click action.
     *
     * @param key       as shown in ui
     * @param value     dynamic value (e.g. from {@link android.content.SharedPreferences}
     * @param multiLine if a different layout should be used for long values
     */
    public static PageEntry<?> createPropertyEntry(CharSequence key, DynamicValue<String> value, boolean multiLine) {
        return new KeyValueEntry(key, value, multiLine);
    }

    /**
     * Creates Key-Value style page entry. Uses dialog as default click action and is not
     * multiline enabled.
     *
     * @param key   as shown in ui
     * @param value dynamic value (e.g. from {@link android.content.SharedPreferences}
     */
    public static PageEntry<?> createPropertyEntry(CharSequence key, DynamicValue<String> value) {
        return new KeyValueEntry(key, value);
    }

    /**
     * Creates Key-Value style page entry with a static value.
     *
     * @param key       as shown in ui
     * @param value     static value
     * @param action    used when clicked on
     * @param multiLine if a different layout should be used for long values
     */
    public static PageEntry<?> createPropertyEntry(CharSequence key, String value, OnClickAction action, boolean multiLine) {
        return new KeyValueEntry(key, value, action, multiLine);
    }

    /**
     * Creates Key-Value style page entry with a static value. Uses dialog as default click action.
     *
     * @param key       as shown in ui
     * @param value     static value
     * @param multiLine if a different layout should be used for long values
     */
    public static PageEntry<?> createPropertyEntry(CharSequence key, String value, boolean multiLine) {
        return new KeyValueEntry(key, value, multiLine);
    }

    /**
     * Creates Key-Value style page entry with a static value. Uses dialog as default click action and
     * ist not multi-line enabled.
     *
     * @param key   as shown in ui
     * @param value static value
     */
    public static PageEntry<?> createPropertyEntry(CharSequence key, String value) {
        return new KeyValueEntry(key, value);
    }

    public static HoodInternal internal() {
        return HOOD_INTERNAL;
    }
}
