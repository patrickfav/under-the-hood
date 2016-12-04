package at.favre.lib.hood.page.entries;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import at.favre.lib.hood.R;
import at.favre.lib.hood.page.PageEntry;
import at.favre.lib.hood.page.ViewTemplate;
import at.favre.lib.hood.page.values.SpinnerElement;
import at.favre.lib.hood.page.values.SpinnerValue;
import at.favre.lib.hood.util.HoodUtil;

import static at.favre.lib.hood.page.entries.ViewTypes.VIEWTYPE_CONFIG_SPINNER;

public class ConfigSpinnerEntry implements PageEntry<ConfigSpinnerEntry.SingleSelectListConfigAction> {

    private final SingleSelectListConfigAction action;
    private final Template template;

    public ConfigSpinnerEntry(SingleSelectListConfigAction action) {
        this.action = action;
        template = new Template();
    }

    @Override
    public SingleSelectListConfigAction getValue() {
        return action;
    }

    @Override
    public ViewTemplate<SingleSelectListConfigAction> getViewTemplate() {
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

    private static class Template implements ViewTemplate<SingleSelectListConfigAction> {
        @Override
        public int getViewType() {
            return VIEWTYPE_CONFIG_SPINNER;
        }

        @Override
        public View constructView(ViewGroup viewGroup, LayoutInflater inflater) {
            return inflater.inflate(R.layout.hoodlib_template_config_spinner, viewGroup, false);
        }

        @Override
        public void setContent(final SingleSelectListConfigAction value, View view) {
            final Spinner spinnerView = ((Spinner) view.findViewById(R.id.config_spinner));
            final TextView textView = ((TextView) view.findViewById(R.id.label));

            if (value.label == null) {
                textView.setVisibility(View.GONE);
            } else {
                textView.setText(value.label);
                textView.setVisibility(View.VISIBLE);
            }

            spinnerView.setAdapter(new ConfigSpinnerAdapter(view.getContext(), value.changeableValue.getAlPossibleValues()));
            spinnerView.setOnItemSelectedListener(null);
            spinnerView.setSelection(((ConfigSpinnerAdapter) spinnerView.getAdapter()).getPosition(value.changeableValue.getValue()));
            spinnerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    value.changeableValue.onChange((SpinnerElement) parent.getAdapter().getItem(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        @Override
        public void decorateViewWithZebra(View view, @ColorInt int zebraColor, boolean hasZebra) {
            HoodUtil.setZebraToView(view, zebraColor, hasZebra);
        }
    }

    private static class ConfigSpinnerAdapter extends ArrayAdapter<SpinnerElement> {

        private
        @LayoutRes
        int layout;

        public ConfigSpinnerAdapter(Context context, List<SpinnerElement> objects) {
            super(context, R.layout.hoodlib_item_spinner, objects);
            layout = R.layout.hoodlib_item_spinner;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView,
                            @NonNull ViewGroup parent) {
            return getFromResources(position, convertView, parent);
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return getFromResources(position, convertView, parent);
        }

        private View getFromResources(int position, @Nullable View convertView,
                                      @NonNull ViewGroup parent) {
            final LayoutInflater inflater = LayoutInflater.from(getContext());
            final View view;

            if (convertView == null) {
                view = inflater.inflate(layout, parent, false);
            } else {
                view = convertView;
            }

            final TextView text = (TextView) view;
            text.setText(getItem(position).getName());
            return view;
        }

        public int getPosForItem(SpinnerElement element) {
            for (int i = 0; i < getCount(); i++) {
                if (element.equals(getItem(i))) {
                    return i;
                }
            }
            return 0;
        }
    }

    public static class SingleSelectListConfigAction {
        public final String label;
        public final SpinnerValue<List<SpinnerElement>, SpinnerElement> changeableValue;

        public SingleSelectListConfigAction(@Nullable String label, SpinnerValue<List<SpinnerElement>, SpinnerElement> value) {
            this.label = label;
            this.changeableValue = value;
        }
    }
}
