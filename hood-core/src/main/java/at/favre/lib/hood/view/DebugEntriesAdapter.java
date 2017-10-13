/*
 *  Copyright 2016 Patrick Favre-Bulle
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package at.favre.lib.hood.view;

import android.support.annotation.ColorInt;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import at.favre.lib.hood.interfaces.Page;
import at.favre.lib.hood.interfaces.ViewTemplate;

/**
 * The adapter managing the entries from given {@link Page}. Instead of a {@link android.support.v7.widget.RecyclerView.ViewHolder}
 * the {@link ViewTemplate} from the entry is used.
 */
class DebugEntriesAdapter extends RecyclerView.Adapter<DebugEntriesAdapter.DebugViewHolder> {

    private final Page page;
    @ColorInt
    private final int zebraColor;

    public DebugEntriesAdapter(Page page, int zebraColor) {
        this.page = page;
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
        ViewTemplate<Object> template = (ViewTemplate<Object>) page.getTemplateForViewType(page.getEntries().get(position).getViewType());
        template.setContent(page.getEntries().get(position).getValue(), holder.holderView);
        if (page.getConfig().showZebra || page.getConfig().showHighlightContent) {
            template.decorateViewWithZebra(holder.holderView, zebraColor, page.getConfig().showHighlightContent || position % 2 == 1);
        }
    }

    @Override
    public int getItemCount() {
        return page.getEntries().size();
    }

    @Override
    public int getItemViewType(int position) {
        return page.getEntries().get(position).getViewType();
    }

    static class DebugViewHolder extends RecyclerView.ViewHolder {
        final View holderView;

        public DebugViewHolder(View itemView) {
            super(itemView);
            holderView = itemView;
        }
    }

}
