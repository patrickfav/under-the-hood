package at.favre.lib.hood.views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import at.favre.lib.hood.R;
import at.favre.lib.hood.page.Page;

public class HoodDebugPageView extends FrameLayout {
    private RecyclerView mRecyclerView;
    private DebugDataAdapter mAdapter;
    private Page page;

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
    }

    public void setPageData(Page page) {
        this.page = page;
        mAdapter = new DebugDataAdapter(page);
        mRecyclerView.setAdapter(mAdapter);
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
}
