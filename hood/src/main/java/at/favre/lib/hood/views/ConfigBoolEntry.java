package at.favre.lib.hood.views;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import at.favre.lib.hood.R;

public class ConfigBoolEntry extends AbstractPageEntry<ConfigBoolEntry.BoolConfigAction> {
    public static final int VIEWTYPE_CONFIG_BOOL = 1 << 16 + 4;

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
    public boolean isStaticContent() {
        return true;
    }

    @Override
    public String toLogString() {
        return "\t" + action.label + ": " + action.changeableValue.getValue();
    }

    private static class Template implements ViewTemplate<BoolConfigAction> {
        @Override
        public int getViewType() {
            return VIEWTYPE_CONFIG_BOOL;
        }

        @Override
        public View constructView(ViewGroup viewGroup, LayoutInflater inflater) {
            return inflater.inflate(R.layout.template_config_bool, viewGroup, false);
        }

        @Override
        public void setContent(final BoolConfigAction value, View view) {
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
        public void decorateViewWithZebra(View view, boolean hasZebra) {
            Drawable zebra = null;

            if (hasZebra) {
                zebra = new ColorDrawable(ContextCompat.getColor(view.getContext(), R.color.zebra_color));
            }

            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                view.findViewById(R.id.inner_wrapper).setBackgroundDrawable(zebra);
            } else {
                view.findViewById(R.id.inner_wrapper).setBackground(zebra);
            }
        }
    }

    public static class BoolConfigAction {
        public final String label;
        public final ChangeableValue<Boolean> changeableValue;

        public BoolConfigAction(String label, ChangeableValue<Boolean> boolValue) {
            this.label = label;
            this.changeableValue = boolValue;
        }
    }
}
