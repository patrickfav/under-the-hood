package at.favre.lib.hood.internal.entries;

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
import at.favre.lib.hood.interfaces.PageEntry;
import at.favre.lib.hood.interfaces.ViewTemplate;
import at.favre.lib.hood.interfaces.ViewTypes;
import at.favre.lib.hood.interfaces.actions.SingleSelectListConfigAction;
import at.favre.lib.hood.interfaces.values.SpinnerElement;
import at.favre.lib.hood.view.HoodDebugPageView;

/**
 * A dropdown-type ui page entry element (select one in a list). Uses defined interface to get/set the
 * values of the list.
 */
public class ConfigSpinnerEntry implements PageEntry<SingleSelectListConfigAction> {

    private final SingleSelectListConfigAction action;

    /**
     * Creates a single-select from list entry (ie. spinner or drop-down list)
     *
     * @param action
     */
    public ConfigSpinnerEntry(SingleSelectListConfigAction action) {
        this.action = action;
    }

    @Override
    public SingleSelectListConfigAction getValue() {
        return action;
    }

    @Override
    public ViewTemplate<SingleSelectListConfigAction> createViewTemplate() {
        return new Template(getViewType());
    }

    @Override
    public int getViewType() {
        return ViewTypes.VIEWTYPE_CONFIG_SPINNER;
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
        private final int viewType;

        Template(int viewType) {
            this.viewType = viewType;
        }

        @Override
        public int getViewType() {
            return viewType;
        }

        @Override
        public View constructView(ViewGroup viewGroup, LayoutInflater inflater) {
            return inflater.inflate(R.layout.hoodlib_template_config_spinner, viewGroup, false);
        }

        @Override
        public void setContent(final SingleSelectListConfigAction value, @NonNull View view) {
            final Spinner spinnerView = ((Spinner) view.findViewById(R.id.config_spinner));
            final TextView textView = ((TextView) view.findViewById(R.id.label));

            if (value.label == null) {
                textView.setVisibility(View.GONE);
            } else {
                textView.setText(value.label);
                textView.setVisibility(View.VISIBLE);
            }

            spinnerView.setAdapter(new ConfigSpinnerAdapter(view.getContext(), value.changeableValue.getAllPossibleValues()));
            spinnerView.setOnItemSelectedListener(null);
            spinnerView.setSelection(((ConfigSpinnerAdapter) spinnerView.getAdapter()).getPosForItem(value.changeableValue.getValue()));
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
        public void decorateViewWithZebra(@NonNull View view, @ColorInt int zebraColor, boolean isOdd) {
            HoodDebugPageView.setZebraToView(view, zebraColor, isOdd);
        }
    }

    private static class ConfigSpinnerAdapter extends ArrayAdapter<SpinnerElement> {
        ConfigSpinnerAdapter(Context context, List<SpinnerElement> objects) {
            super(context, 0, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return getFromResources(position, convertView, parent, R.layout.hoodlib_item_spinner, "normal");
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return getFromResources(position, convertView, parent, R.layout.hoodlib_item_spinner_dropdown, "dropdown");
        }

        private View getFromResources(int position, @Nullable View convertView,
                                      @NonNull ViewGroup parent, @LayoutRes int layout, String tag) {
            final LayoutInflater inflater = LayoutInflater.from(getContext());
            final View view;

            if (convertView == null || convertView.getTag() == null || !convertView.getTag().equals(tag)) {
                view = inflater.inflate(layout, parent, false);
                view.setTag(tag);
            } else {
                view = convertView;
            }

            final TextView text = (TextView) view;
            text.setText(getItem(position).getName());
            return view;
        }

        public int getPosForItem(SpinnerElement element) {
            if (element != null) {
                for (int i = 0; i < getCount(); i++) {
                    if (element.equals(getItem(i))) {
                        return i;
                    }
                }
            }
            return 0;
        }
    }

}
