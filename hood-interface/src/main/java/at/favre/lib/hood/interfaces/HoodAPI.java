package at.favre.lib.hood.interfaces;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import java.util.List;

import at.favre.lib.hood.interfaces.actions.BoolConfigAction;
import at.favre.lib.hood.interfaces.actions.ButtonDefinition;
import at.favre.lib.hood.interfaces.actions.OnClickAction;
import at.favre.lib.hood.interfaces.actions.SingleSelectListConfigAction;
import at.favre.lib.hood.interfaces.values.DynamicValue;

/**
 * The main API for the lib
 */
public interface HoodAPI {
    /**
     * Creates a new {@link Pages} object that can be used to fill with {@link Page}
     *
     * @param config the config used to create the pages
     * @return pages
     */
    @NonNull
    Pages createPages(@NonNull Config config);

    /**
     * Single column action
     *
     * @param action
     */
    PageEntry<?> createActionEntry(ButtonDefinition action);

    /**
     * Two columns with 2 different actions in a row
     *
     * @param actionLeft
     * @param actionRight
     */
    PageEntry<?> createActionEntry(ButtonDefinition actionLeft, ButtonDefinition actionRight);

    /**
     * Creates a header entry
     *
     * @param header as shown in ui
     */
    PageEntry<?> createHeaderEntry(CharSequence header);

    /**
     * Creates a header entry
     *
     * @param header    as shown in ui
     * @param hideInLog will omit it in log
     */
    PageEntry<?> createHeaderEntry(CharSequence header, boolean hideInLog);

    /**
     * Creates a simple, non-interactive text message
     *
     * @param message as shown in
     */
    PageEntry<?> createMessageEntry(CharSequence message);

    /**
     * Creates an interactive switch like entry
     *
     * @param action
     */
    PageEntry<?> createSwitchEntry(BoolConfigAction action);

    /**
     * Creates a single-select from list entry (ie. spinner or drop-down list)
     *
     * @param action
     */
    PageEntry<?> createSpinnerEntry(SingleSelectListConfigAction action);

    /**
     * Creates Key-Value style page entry.
     *
     * @param key       as shown in ui
     * @param value     dynamic value (e.g. from {@link android.content.SharedPreferences}
     * @param action    used when clicked on
     * @param multiLine if a different layout should be used for long values
     */
    PageEntry<?> createPropertyEntry(CharSequence key, DynamicValue<String> value, OnClickAction action, boolean multiLine);

    /**
     * Creates Key-Value style page entry. Uses dialog as default click action.
     *
     * @param key       as shown in ui
     * @param value     dynamic value (e.g. from {@link android.content.SharedPreferences}
     * @param multiLine if a different layout should be used for long values
     */
    PageEntry<?> createPropertyEntry(CharSequence key, DynamicValue<String> value, boolean multiLine);

    /**
     * Creates Key-Value style page entry. Uses dialog as default click action and is not
     * multiline enabled.
     *
     * @param key   as shown in ui
     * @param value dynamic value (e.g. from {@link android.content.SharedPreferences}
     */
    PageEntry<?> createPropertyEntry(CharSequence key, DynamicValue<String> value);

    /**
     * Creates Key-Value style page entry with a static value.
     *
     * @param key       as shown in ui
     * @param value     static value
     * @param action    used when clicked on
     * @param multiLine if a different layout should be used for long values
     */
    PageEntry<?> createPropertyEntry(CharSequence key, String value, OnClickAction action, boolean multiLine);

    /**
     * Creates Key-Value style page entry with a static value. Uses dialog as default click action.
     *
     * @param key       as shown in ui
     * @param value     static value
     * @param multiLine if a different layout should be used for long values
     */
    PageEntry<?> createPropertyEntry(CharSequence key, String value, boolean multiLine);

    /**
     * Creates Key-Value style page entry with a static value. Uses dialog as default click action and
     * ist not multi-line enabled.
     *
     * @param key   as shown in ui
     * @param value static value
     */
    PageEntry<?> createPropertyEntry(CharSequence key, String value);

    interface Internal {
        Section.ModifiableHeaderSection createSection(String header);

        Section.ModifiableHeaderSection createSection(String header, List<PageEntry<?>> entries);

        OnClickAction createOnClickActionAskPermission(String perm, Activity activity);

        OnClickAction createOnClickActionStartIntent(Intent intent);

        OnClickAction createOnClickActionToast();

        CharSequence createFullLabel(CharSequence shortLabel, CharSequence fullLabel);
    }
}
