package at.favre.lib.hood.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import at.favre.lib.hood.R;
import at.favre.lib.hood.page.Page;

/**
 * The view encapsulating the rendering logic of a {@link Page}. Internally has an recyclerview
 * with a {@link DebugDataAdapter}. Implements the {@link NestedScrollingChild} to be able to be
 * used in a CoordinatorLayout.
 */
public class HoodDebugPageView extends FrameLayout implements NestedScrollingChild {
    private static final String TAG = HoodDebugPageView.class.getName();

    private RecyclerView mRecyclerView;
    private DebugDataAdapter mAdapter;
    private Page page;
    @ColorInt
    private int zebraColor;
    private NestedScrollingChildHelper mScrollingChildHelper;

    public HoodDebugPageView(Context context) {
        super(context);
        setup(null);
    }

    public HoodDebugPageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(attrs);
    }

    public HoodDebugPageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(attrs);
    }

    private void setup(@Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.HoodDebugPageView, 0, 0);
            try {
                zebraColor = a.getColor(R.styleable.HoodDebugPageView_zebraBackgroundColor, getResources().getColor(R.color.hoodlib_zebra_color));
            } finally {
                a.recycle();
            }
        } else {
            zebraColor = ContextCompat.getColor(getContext(), R.color.hoodlib_zebra_color);
        }

        FrameLayout layout = (FrameLayout) LayoutInflater.from(getContext()).inflate(R.layout.hoodlib_view_debugview, this, true);
        setNestedScrollingEnabled(true);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setNestedScrollingEnabled(true);
    }

    /**
     * Sets the page data (required to for the ui to show anything)
     * @param page
     */
    public void setPageData(@NonNull Page page) {
        setPageData(page, new Config(true));
    }

    /**
     * Sets the page data (required to for the ui to show anything)
     * @param page
     * @param config
     */
    public void setPageData(@NonNull Page page, @NonNull Config config) {
        this.page = page;
        this.mAdapter = new DebugDataAdapter(page, config, zebraColor);
        this.mRecyclerView.setAdapter(mAdapter);

        if (config.autoLog) {
            page.log(TAG);
        }
    }

    public Page getPage() {
        return page;
    }

    /**
     * Refreshes all dynamic values
     */
    public void refresh() {
        checkPreconditions();
        page.refreshData();
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Logs a entries to console
     * @param tag
     */
    public void log(String tag) {
        checkPreconditions();
        page.log(tag);
    }

    private void checkPreconditions() {
        if (page == null) {
            throw new IllegalStateException("you have to call setPageData first");
        }
    }

    // NestedScrollingChild
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

    public static class Config {
        public final boolean showZebra;
        public final boolean autoLog = true;

        public Config(boolean showZebra) {
            this.showZebra = showZebra;
        }
    }
}
