package at.favre.lib.hood;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import at.favre.lib.hood.views.DebugDataAdapter;
import at.favre.lib.hood.views.Page;
import at.favre.lib.hood.views.PageEntry;

public class HoodDebugView extends FrameLayout {
    private RecyclerView mRecyclerView;
    private Page page;

    public HoodDebugView(Context context) {
        super(context);
        setup();
    }

    public HoodDebugView(Context context, AttributeSet attrs) {
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
        DebugDataAdapter mAdapter = new DebugDataAdapter(page);
        mRecyclerView.setAdapter(mAdapter);
    }

    public String getDebugDataAsString() {
        checkPreconditions();
        StringBuilder sb = new StringBuilder();
        for (PageEntry pageEntry : page.getEntries()) {
            String log = pageEntry.toLogString();
            if (log != null) {
                sb.append(pageEntry.toLogString()).append("\n");
            }
        }
        return sb.toString();
    }

    public void log(String tag) {
        Log.w(tag, getDebugDataAsString());
    }

    private void checkPreconditions() {
        if (page == null) {
            throw new IllegalStateException("you have to call setPageData first");
        }
    }
}
