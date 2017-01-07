package at.favre.lib.hood.interfaces.actions;

/**
 * An action containing a name (button text) and a click-listener used for the button
 */
public class ButtonDefinition {
    public final String label;
    public final OnClickAction onClickAction;

    public ButtonDefinition(String label, OnClickAction onClickAction) {
        this.label = label;
        this.onClickAction = onClickAction;
    }
}
