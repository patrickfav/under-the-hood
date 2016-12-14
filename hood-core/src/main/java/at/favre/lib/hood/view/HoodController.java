package at.favre.lib.hood.view;


import android.support.annotation.NonNull;

import at.favre.lib.hood.page.Pages;

public interface HoodController {
    /**
     * @return the current page
     */
    @NonNull
    Pages getPages();
}
