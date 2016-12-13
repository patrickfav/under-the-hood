package at.favre.lib.hood.views;


import android.support.annotation.NonNull;

import at.favre.lib.hood.page.Config;
import at.favre.lib.hood.page.DebugPage;
import at.favre.lib.hood.page.Page;

public interface IHoodDebugController {
    /**
     * @return the current page
     */
    @NonNull
    Page getPage();
}
