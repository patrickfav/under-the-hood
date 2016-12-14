package at.favre.lib.hood.view;

import android.support.annotation.ColorInt;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import at.favre.lib.hood.page.Page;
import at.favre.lib.hood.page.Pages;


public class DebugViewPageAdapter extends PagerAdapter {
    private Pages pages;
    @ColorInt
    private int zebraColor;
    private ViewGroup viewGroup;
    private boolean enabled = true;

    public DebugViewPageAdapter(Pages pages, int zebraColor) {
        this.pages = pages;
        this.zebraColor = zebraColor;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        viewGroup = container;
        Page page = pages.getPage(position);
        container.addView(new DebugPageContentView(container.getContext(), pages.getPage(position), zebraColor));
        return page;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        viewGroup = container;
        for (int i = 0; i < container.getChildCount(); i++) {
            if (container.getChildAt(i) instanceof DebugPageContentView && ((DebugPageContentView) container.getChildAt(i)).getPage().equals(object)) {
                container.removeViewAt(i);
                break;
            }
        }
    }

    @Override
    public void notifyDataSetChanged() {
        if (viewGroup != null) {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                if (viewGroup.getChildAt(i) instanceof DebugPageContentView) {
                    ((DebugPageContentView) viewGroup.getChildAt(i)).refresh();
                }
            }
        }
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return pages.getAll().size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view instanceof DebugPageContentView && ((DebugPageContentView) view).getPage().equals(object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pages.getPage(position).getTitle();
    }
}
