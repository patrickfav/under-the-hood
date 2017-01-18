package at.favre.lib.hood.view;


import android.support.annotation.NonNull;

import at.favre.lib.hood.interfaces.Pages;

/**
 * Used by some classes to call methods on the view's current {@link Pages} instanced.
 * This is necessary for components that are not tied to the debug view to communicate with the {@link HoodDebugPageView}
 * The implementation is not mandatory, but some warnings might be popping up in log.
 */
public interface HoodController {
    /**
     * The default implementation would be to return {@link HoodDebugPageView#getPages()}
     *
     * @return get the {@link Pages} instanced used by this Activity's or Fragment's {@link HoodDebugPageView}
     */
    @NonNull
    Pages getCurrentPagesFromThisView();
}
