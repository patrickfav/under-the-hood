package at.favre.lib.hood.views;


import android.support.annotation.NonNull;

import at.favre.lib.hood.page.Config;
import at.favre.lib.hood.page.DebugPage;
import at.favre.lib.hood.page.Page;

public interface IHoodDebugController {
    /**
     * Implement this method to pass a {@link Page} filled with entries.
     *
     * @param emptyPage use this to add entries (or create new one)
     * @return non-null set up page
     */
    @NonNull
    Page getPageData(@NonNull DebugPage emptyPage);

    /**
     * Create a config with this method. See {@link at.favre.lib.hood.page.Config.Builder}
     *
     * @return the config
     */
    @NonNull
    Config getConfig();

    /**
     * @return the current page
     */
    @NonNull
    Page getPage();
}
