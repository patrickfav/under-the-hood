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
import at.favre.lib.hood.page.PageEntry;
import at.favre.lib.hood.page.ViewTemplate;
import at.favre.lib.hood.page.values.ChangeableValue;
import at.favre.lib.hood.util.HoodUtil;

import static at.favre.lib.hood.page.entries.ViewTypes.VIEWTYPE_CONFIG_BOOL;

/**
 * A boolean switch used to change a debug setting
 */
public class ConfigBoolEntry implements PageEntry<ConfigBoolEntry.BoolConfigAction> {

    private final BoolConfigAction action;
    private final Template template;

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
    public void refresh() {
        //no-op
    }

    private static class Template implements ViewTemplate<BoolConfigAction> {
        @Override
        public int getViewType() {
            return VIEWTYPE_CONFIG_BOOL;
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
            HoodUtil.setZebraToView(view, zebraColor, isOdd);
        }
    }

    /**
     * The abstraction of the switch containing a label (as shown in ui) and a changeable value
     * representing the boolean switch value. Default implementation see {@link at.favre.lib.hood.defaults.DefaultConfigActions}
     */
    public static class BoolConfigAction {
        public final String label;
        public final ChangeableValue<Boolean, Boolean> changeableValue;

        public BoolConfigAction(String label, ChangeableValue<Boolean, Boolean> boolValue) {
            this.label = label;
            this.changeableValue = boolValue;
        }
    }
}
