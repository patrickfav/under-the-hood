package at.favre.lib.hood.view;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import at.favre.lib.hood.interfaces.Page;
import at.favre.lib.hood.interfaces.Pages;
import timber.log.Timber;

/**
 * The pager adapter for the main viewpager containing {@link DebugPageContentView} views.
 * Supports saving and restoring of the view's states.
 */
public class DebugViewPageAdapter extends PagerAdapter {
    private static final String TAG_VIEWS = "tagViews";

    private final Pages pages;
    @ColorInt
    private final int zebraColor;
    private SparseArray<Parcelable> mViewStates = new SparseArray<>();
    private final ViewPager viewPager;

    public DebugViewPageAdapter(ViewPager viewPager, Pages pages, int zebraColor) {
        this.pages = pages;
        this.zebraColor = zebraColor;
        this.viewPager = viewPager;
        Timber.d("should not be visible");
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Page page = pages.getPage(position);
        DebugPageContentView view = new DebugPageContentView(container.getContext(), pages.getPage(position), zebraColor);
        view.onRestoreInstanceState(mViewStates.get(position));
        container.addView(view);
        return page;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        for (int i = 0; i < container.getChildCount(); i++) {
            if (container.getChildAt(i) instanceof DebugPageContentView && ((DebugPageContentView) container.getChildAt(i)).getPage().equals(object)) {
                DebugPageContentView contentView = ((DebugPageContentView) container.getChildAt(i));
                mViewStates.append(getIndexForPage(contentView.getPage()), contentView.onSaveInstanceState());
                container.removeViewAt(i);
                break;
            }
        }
    }

    public void notifyDataSetChanged(boolean refreshAlsoExpensiveValues) {
        if (viewPager != null) {
            for (int i = 0; i < viewPager.getChildCount(); i++) {
                if (viewPager.getChildAt(i) instanceof DebugPageContentView) {
                    ((DebugPageContentView) viewPager.getChildAt(i)).refresh(refreshAlsoExpensiveValues);
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

    @Override
    public Parcelable saveState() {
        Bundle state = new Bundle();
        if (viewPager != null) {
            for (int i = 0; i < viewPager.getChildCount(); i++) {
                if (viewPager.getChildAt(i) instanceof DebugPageContentView) {
                    DebugPageContentView contentView = ((DebugPageContentView) viewPager.getChildAt(i));
                    mViewStates.append(getIndexForPage(contentView.getPage()), contentView.onSaveInstanceState());
                }
            }
        }
        state.putSparseParcelableArray(TAG_VIEWS, mViewStates);
        return state;
    }

    private int getIndexForPage(Page page) {
        for (int i = 0; i < pages.getAll().size(); i++) {
            if (pages.getAll().get(i).equals(page)) {
                return i;
            }
        }
        throw new IllegalStateException("unknown page " + page);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        Bundle bundle = (Bundle) state;
        bundle.setClassLoader(loader);
        mViewStates = bundle.getSparseParcelableArray(TAG_VIEWS);
    }
}
