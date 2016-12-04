package at.favre.lib.hood.page.entries;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import at.favre.lib.hood.R;
import at.favre.lib.hood.page.PageEntry;
import at.favre.lib.hood.page.ViewTemplate;

import static at.favre.lib.hood.page.entries.ViewTypes.VIEWTYPE_ACTION;
import static at.favre.lib.hood.page.entries.ViewTypes.VIEWTYPE_ACTION_DOUBLE;

/**
 * An entry that is one or two buttons which have defined click action
 */
public class ActionEntry implements PageEntry<List<ActionEntry.Action>> {

    private final List<Action> actionList;
    private final Template template;

    /**
     * Single column action
     * @param action
     */
    public ActionEntry(Action action) {
        this.actionList = Collections.singletonList(action);
        template = new Template(actionList.size() == 1);
    }

    /**
     * Two columns with 2 different actions in a row
     * @param actionLeft
     * @param actionRight
     */
    public ActionEntry(Action actionLeft, Action actionRight) {
        this.actionList = Collections.unmodifiableList(Arrays.asList(actionLeft, actionRight));
        template = new Template(actionList.size() == 1);
    }

    @Override
    public List<Action> getValue() {
        return actionList;
    }

    @Override
    public ViewTemplate<List<Action>> getViewTemplate() {
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

    private static class Template implements ViewTemplate<List<Action>> {
        private boolean isSingleAction;

        public Template(boolean isSingleAction) {
            this.isSingleAction = isSingleAction;
        }

        @Override
        public int getViewType() {
            return isSingleAction ? VIEWTYPE_ACTION : VIEWTYPE_ACTION_DOUBLE;
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
        public void setContent(List<Action> value, @NonNull View view) {
            if (isSingleAction) {
                ((TextView) view.findViewById(R.id.button)).setText(value.get(0).label);
                view.findViewById(R.id.button).setOnClickListener(value.get(0).onClickListener);
            } else {
                ((TextView) view.findViewById(R.id.buttonLeft)).setText(value.get(0).label);
                view.findViewById(R.id.buttonLeft).setOnClickListener(value.get(0).onClickListener);

                ((TextView) view.findViewById(R.id.buttonRight)).setText(value.get(1).label);
                view.findViewById(R.id.buttonRight).setOnClickListener(value.get(1).onClickListener);
            }
        }

        @Override
        public void decorateViewWithZebra(@NonNull View view, @ColorInt int zebraColor, boolean isOdd) {
            //no-op
        }
    }

    /**
     * An action containing a name (button text) and a click-listener used for the button
     */
    public static class Action {
        public final String label;
        public final View.OnClickListener onClickListener;

        public Action(String label, View.OnClickListener onClickListener) {
            this.label = label;
            this.onClickListener = onClickListener;
        }
    }
}
