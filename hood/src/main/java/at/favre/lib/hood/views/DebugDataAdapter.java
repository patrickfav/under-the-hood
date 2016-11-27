package at.favre.lib.hood.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DebugDataAdapter extends RecyclerView.Adapter<DebugDataAdapter.DebugViewHolder> {

    private Page page;

    public DebugDataAdapter(Page page) {
        this.page = page;
    }

    @Override
    public DebugViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewTemplate<?> template = page.getTemplateMap().get(viewType);

        if (null == template) {
            throw new IllegalArgumentException("could not find view template with type " + viewType);
        }

        return new DebugViewHolder(template.constructView(parent, LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(DebugViewHolder holder, int position) {
        ViewTemplate<Object> template = page.getEntries().get(position).getViewTemplate();
        template.setContent(page.getEntries().get(position).getValue(), holder.holderView);
        template.decorateViewWithZebra(holder.holderView,position % 2 == 1);
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
