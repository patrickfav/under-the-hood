package at.favre.lib.hood.views;

import android.content.Context;
import android.support.v4.view.NestedScrollingChild;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import at.favre.lib.hood.R;
import at.favre.lib.hood.page.Page;

public class HoodDebugPageView extends FrameLayout implements NestedScrollingChild {
    private RecyclerView mRecyclerView;
    private DebugDataAdapter mAdapter;
    private Page page;
    private Config config;

    public HoodDebugPageView(Context context) {
        super(context);
        setup();
    }

    public HoodDebugPageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    private void setup() {
        FrameLayout layout = (FrameLayout) LayoutInflater.from(getContext()).inflate(R.layout.view_debugview, this, true);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setNestedScrollingEnabled(true);
    }

    public void setPageData(Page page) {
        setPageData(page, new Config(true));
    }

    public void setPageData(Page page, Config config) {
        this.page = page;
        this.config = config;
        this.mAdapter = new DebugDataAdapter(page, config);
        this.mRecyclerView.setAdapter(mAdapter);
    }

    public Page getPage() {
        return page;
    }

    public void refresh() {
        checkPreconditions();
        page.refreshData();
        mAdapter.notifyDataSetChanged();
    }

    public void log(String tag) {
        checkPreconditions();
        page.log(tag);
    }

    private void checkPreconditions() {
        if (page == null) {
            throw new IllegalStateException("you have to call setPageData first");
        }
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        mRecyclerView.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return mRecyclerView.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return mRecyclerView.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        mRecyclerView.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return mRecyclerView.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return mRecyclerView.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return mRecyclerView.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return mRecyclerView.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return mRecyclerView.dispatchNestedPreFling(velocityX, velocityY);
    }

    public static class Config {
        public final boolean showZebra;

        public Config(boolean showZebra) {
            this.showZebra = showZebra;
        }
    }
}
