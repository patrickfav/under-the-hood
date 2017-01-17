package at.favre.lib.hood.page.entries;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import at.favre.lib.hood.R;
import at.favre.lib.hood.interfaces.PageEntry;
import at.favre.lib.hood.interfaces.ViewTemplate;
import at.favre.lib.hood.interfaces.ViewTypes;
import at.favre.lib.hood.interfaces.actions.BoolConfigAction;
import at.favre.lib.hood.view.HoodDebugPageView;

/**
 * A boolean switch used to change a debug setting
 */
public class ConfigBoolEntry implements PageEntry<BoolConfigAction> {

    private final BoolConfigAction action;
    private final Template template;

    /**
     * Creates an interactive switch like entry
     *
     * @param action
     */
    public ConfigBoolEntry(BoolConfigAction action) {
        this.action = action;
        template = new Template();
    }

    @Override
    public BoolConfigAction getValue() {
        return action;
    }

    @Override
    public ViewTemplate<BoolConfigAction> getViewTemplate() {
        return template;
    }

    @Override
    public String toLogString() {
        return "\t" + action.label + ": " + action.changeableValue.getValue();
    }

    @Override
    public void refresh(boolean refreshAlsoExpensiveValues) {
        //no-op
    }

    private static class Template implements ViewTemplate<BoolConfigAction> {
        @Override
        public int getViewType() {
            return ViewTypes.VIEWTYPE_CONFIG_BOOL;
        }

        @Override
        public View constructView(ViewGroup viewGroup, LayoutInflater inflater) {
            return inflater.inflate(R.layout.hoodlib_template_config_bool, viewGroup, false);
        }

        @Override
        public void setContent(final BoolConfigAction value, @NonNull View view) {
            final Switch switchView = ((Switch) view.findViewById(R.id.config_switch));
            final TextView textView = ((TextView) view.findViewById(R.id.label));

            textView.setText(value.label);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchView.setChecked(!value.changeableValue.getValue());
                }
            });

            switchView.setOnCheckedChangeListener(null);
            switchView.setChecked(value.changeableValue.getValue());
            switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    value.changeableValue.onChange(isChecked);
                }
            });
        }

        @Override
        public void decorateViewWithZebra(@NonNull View view, @ColorInt int zebraColor, boolean isOdd) {
            HoodDebugPageView.setZebraToView(view, zebraColor, isOdd);
        }
    }

}
