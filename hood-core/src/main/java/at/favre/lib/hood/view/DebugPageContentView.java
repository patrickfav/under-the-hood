package at.favre.lib.hood.view;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import at.favre.lib.hood.R;
import at.favre.lib.hood.interfaces.Page;

/**
 * Implements the {@link NestedScrollingChild} to be able to be
 * used in a CoordinatorLayout.
 */
public class DebugPageContentView extends FrameLayout {
    private RecyclerView mRecyclerView;
    private NestedScrollingChildHelper mScrollingChildHelper;
    private final Page page;

    public DebugPageContentView(@NonNull Context context) {
        this(context, null, ResourcesCompat.getColor(context.getResources(), R.color.hoodlib_zebra_color, context.getTheme()));
    }

    public DebugPageContentView(@NonNull Context context, Page page, @ColorInt int zebraColor) {
        super(context);
        this.page = page;
        setup(zebraColor);
    }

    private void setup(@ColorInt int zebraColor) {
        FrameLayout layout = (FrameLayout) LayoutInflater.from(getContext()).inflate(R.layout.hoodlib_view_page, this, true);
        setNestedScrollingEnabled(true);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerView.setAdapter(new DebugEntriesAdapter(page, zebraColor));
    }

    public Page getPage() {
        return page;
    }

    public void refresh(boolean refreshAlsoExpensiveValues) {
        page.refreshData(refreshAlsoExpensiveValues);
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        if (mRecyclerView != null) {
            return mRecyclerView.getLayoutManager().onSaveInstanceState();
        }
        return super.onSaveInstanceState();
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (mRecyclerView != null && state != null) {
            mRecyclerView.getLayoutManager().onRestoreInstanceState(state);
        } else {
            super.onRestoreInstanceState(state);
        }
    }

    /* ****************************************************** NestedScrollingChild*/

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        getScrollingChildHelper().setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return getScrollingChildHelper().isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return getScrollingChildHelper().startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        getScrollingChildHelper().stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return getScrollingChildHelper().hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed,
                                        int dyUnconsumed, int[] offsetInWindow) {
        return getScrollingChildHelper().dispatchNestedScroll(dxConsumed, dyConsumed,
                dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return getScrollingChildHelper().dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return getScrollingChildHelper().dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return getScrollingChildHelper().dispatchNestedPreFling(velocityX, velocityY);
    }

    private NestedScrollingChildHelper getScrollingChildHelper() {
        if (mScrollingChildHelper == null) {
            mScrollingChildHelper = new NestedScrollingChildHelper(mRecyclerView);
        }
        return mScrollingChildHelper;
    }
}
