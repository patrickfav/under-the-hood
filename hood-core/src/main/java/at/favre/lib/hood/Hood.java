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

package at.favre.lib.hood;

import at.favre.lib.hood.interfaces.HoodAPI;

/**
 * This is the main API. Use the factory methods instead of directly
 * instantiating the internal classes.
 */
public final class Hood {
    private static HoodAPI.Extension extensionInstance;
    private static HoodAPI instance;

    private Hood() {
    }

    /**
     * Gets the main API. See {@link HoodAPI}
     * Dependent on the build type may get a null-safe no-op version (see {@link #isLibEnabled()} for release builds
     *
     * @return singleton instance
     */
    public static HoodAPI get() {
        if (instance == null) {
            instance = new HoodFactory().createHoodApi();
        }
        return instance;
    }

    /**
     * Gets the extension API. See {@link at.favre.lib.hood.interfaces.HoodAPI.Extension}
     * <p>
     * Dependent on the build type may get a null-safe no-op version (see {@link #isLibEnabled()} for release builds
     *
     * @return singleton instance
     */
    public static HoodAPI.Extension ext() {
        if (extensionInstance == null) {
            extensionInstance = new HoodFactory().createHoodApiExtension();
        }
        return extensionInstance;
    }

    /**
     * This will return false if the no-op flavour is used. Use this in your app to be able to
     * omit initializing your debugging code in release builds.
     *
     * @return true if the lib is enabled and all of its features fully functional
     */
    public static boolean isLibEnabled() {
        return !BuildConfig.NO_OP;
    }
}
