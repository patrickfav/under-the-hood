/*
 *  Copyright 2016 Patrick Favre-Bulle
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
