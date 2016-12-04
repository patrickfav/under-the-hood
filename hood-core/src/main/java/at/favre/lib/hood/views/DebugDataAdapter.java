package at.favre.lib.hood.views;

import android.support.annotation.ColorInt;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import at.favre.lib.hood.page.Page;
import at.favre.lib.hood.page.ViewTemplate;

/**
 * The adapter managing the entries from given {@link Page}. Instead of a {@link android.support.v7.widget.RecyclerView.ViewHolder}
 * the {@link ViewTemplate} from the entry is used.
 */
public class DebugDataAdapter extends RecyclerView.Adapter<DebugDataAdapter.DebugViewHolder> {

    private final Page page;
    private final HoodDebugPageView.Config config;
    @ColorInt
    private final int zebraColor;

    public DebugDataAdapter(Page page, HoodDebugPageView.Config config, int zebraColor) {
        this.page = page;
        this.config = config;
        this.zebraColor = zebraColor;
    }

    @Override
    public DebugViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewTemplate<?> template = page.getTemplateForViewType(viewType);

        if (null == template) {
            throw new IllegalArgumentException("could not find view template with type " + viewType);
        }

        return new DebugViewHolder(template.constructView(parent, LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(DebugViewHolder holder, int position) {
        ViewTemplate<Object> template = page.getEntries().get(position).getViewTemplate();
        template.setContent(page.getEntries().get(position).getValue(), holder.holderView);
        if (config.showZebra) {
            template.decorateViewWithZebra(holder.holderView, zebraColor, position % 2 == 1);
        }
    }

    @Override
    public int getItemCount() {
        return page.getEntries().size();
    }

    @Override
    public int getItemViewType(int position) {
        return page.getEntries().get(position).getViewTemplate().getViewType();
    }

    static class DebugViewHolder extends RecyclerView.ViewHolder {
        final View holderView;

        public DebugViewHolder(View itemView) {
            super(itemView);
            holderView = itemView;
        }
    }

}
