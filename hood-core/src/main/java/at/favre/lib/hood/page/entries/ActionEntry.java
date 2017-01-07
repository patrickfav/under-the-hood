package at.favre.lib.hood.page.entries;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import at.favre.lib.hood.R;
import at.favre.lib.hood.interfaces.PageEntry;
import at.favre.lib.hood.interfaces.ViewTemplate;
import at.favre.lib.hood.interfaces.ViewTypes;
import at.favre.lib.hood.interfaces.actions.ButtonDefinition;

/**
 * An entry that is one or two buttons which have defined click action
 */
public class ActionEntry implements PageEntry<List<ButtonDefinition>> {

    private final List<ButtonDefinition> actionList;
    private final Template template;

    /**
     * Single column action
     *
     * @param action
     */
    public ActionEntry(ButtonDefinition action) {
        this.actionList = Collections.singletonList(action);
        template = new Template(actionList.size() == 1);
    }

    /**
     * Two columns with 2 different actions in a row
     *
     * @param actionLeft
     * @param actionRight
     */
    public ActionEntry(ButtonDefinition actionLeft, ButtonDefinition actionRight) {
        this.actionList = Collections.unmodifiableList(Arrays.asList(actionLeft, actionRight));
        template = new Template(actionList.size() == 1);
    }

    @Override
    public List<ButtonDefinition> getValue() {
        return actionList;
    }

    @Override
    public ViewTemplate<List<ButtonDefinition>> getViewTemplate() {
        return template;
    }

    @Override
    public String toLogString() {
        return null;
    }

    @Override
    public void refresh() {
        //no-op
    }

    private static class Template implements ViewTemplate<List<ButtonDefinition>> {
        private final boolean isSingleAction;

        public Template(boolean isSingleAction) {
            this.isSingleAction = isSingleAction;
        }

        @Override
        public int getViewType() {
            return isSingleAction ? ViewTypes.VIEWTYPE_ACTION : ViewTypes.VIEWTYPE_ACTION_DOUBLE;
        }

        @Override
        public View constructView(ViewGroup viewGroup, LayoutInflater inflater) {
            if (isSingleAction) {
                return inflater.inflate(R.layout.hoodlib_template_action_single, viewGroup, false);
            } else {
                return inflater.inflate(R.layout.hoodlib_template_action_double, viewGroup, false);
            }
        }

        @Override
        public void setContent(final List<ButtonDefinition> value, @NonNull View view) {
            if (isSingleAction) {
                ((TextView) view.findViewById(R.id.button)).setText(value.get(0).label);
                view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        value.get(0).onClickAction.onClick(v, new AbstractMap.SimpleEntry<CharSequence, String>(value.get(0).label, null));
                    }
                });
            } else {
                ((TextView) view.findViewById(R.id.buttonLeft)).setText(value.get(0).label);
                view.findViewById(R.id.buttonLeft).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        value.get(0).onClickAction.onClick(v, new AbstractMap.SimpleEntry<CharSequence, String>(value.get(0).label, null));
                    }
                });

                ((TextView) view.findViewById(R.id.buttonRight)).setText(value.get(1).label);
                view.findViewById(R.id.buttonRight).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        value.get(1).onClickAction.onClick(v, new AbstractMap.SimpleEntry<CharSequence, String>(value.get(1).label, null));
                    }
                });
            }
        }

        @Override
        public void decorateViewWithZebra(@NonNull View view, @ColorInt int zebraColor, boolean isOdd) {
            //no-op
        }
    }
}
