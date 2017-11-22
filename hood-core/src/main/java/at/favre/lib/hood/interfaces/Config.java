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

package at.favre.lib.hood.interfaces;

public final class Config {
    private static final String TAG = Config.class.getName();

    public final boolean showZebra;
    public final boolean showHighlightContent;
    public final boolean autoLog;
    public final boolean autoRefresh;
    public final long autoRefreshIntervalMs;
    public final String logTag;
    public final boolean showPagesIndicator;

    private Config(boolean showZebra, boolean showHighlightContent, boolean autoLog, boolean autoRefresh, long autoRefreshIntervalMs, String logTag, boolean showPagesIndicator) {
        this.showZebra = showZebra;
        this.showHighlightContent = showHighlightContent;
        this.autoLog = autoLog;
        this.autoRefresh = autoRefresh;
        this.autoRefreshIntervalMs = autoRefreshIntervalMs;
        this.logTag = logTag;
        this.showPagesIndicator = showPagesIndicator;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private boolean showZebra = true;
        private boolean showHighlightContent = false;
        private boolean autoLog = true;
        private boolean autoRefresh = false;
        private long autoRefreshIntervalMs = 10_000;
        private String logTag = TAG;
        private boolean showPagesIndicator = true;

        private Builder() {
        }

        /**
         * UI showing zebra pattern (highlighting every other row)
         *
         * @param showZebra
         */
        public Builder setShowZebra(boolean showZebra) {
            this.showZebra = showZebra;
            return this;
        }

        /**
         * UI highlighting content area with zebra color
         *
         * @param showHighlightContent
         */
        public Builder setShowHighlightContent(boolean showHighlightContent) {
            this.showHighlightContent = showHighlightContent;
            return this;
        }

        /**
         * If true will automatically log the whole page content to console when the view
         * is instantiated.
         *
         * @param autoLogEnabled
         */
        public Builder setAutoLog(boolean autoLogEnabled) {
            this.autoLog = autoLogEnabled;
            return this;
        }

        /**
         * If true will auto refresh the view
         *
         * @param autoRefreshEnabled
         */
        public Builder setAutoRefresh(boolean autoRefreshEnabled) {
            this.autoRefresh = autoRefreshEnabled;
            return this;
        }

        /**
         * If true will auto refresh the view. This call can set the refresh interval in ms (min is 300ms)
         *
         * @param autoRefreshEnabled
         * @param intervalMs         min is 300ms - time between refreshes
         */
        public Builder setAutoRefresh(boolean autoRefreshEnabled, long intervalMs) {
            this.autoRefresh = autoRefreshEnabled;
            this.autoRefreshIntervalMs = Math.max(300, intervalMs);
            return this;
        }

        /**
         * Sets the tag which is used to log debug data to console (see {@link android.util.Log#w(String, String)}
         *
         * @param logTag
         */
        public Builder setLogTag(String logTag) {
            this.logTag = logTag;
            return this;
        }

        /**
         * Enables or disables the viewpager page indicator (only visible if more than one page)
         *
         * @param showPagesIndicator
         */
        public Builder setShowPagesIndicatorOnMultiplePages(boolean showPagesIndicator) {
            this.showPagesIndicator = showPagesIndicator;
            return this;
        }

        public Config build() {
            return new Config(showZebra, showHighlightContent, autoLog, autoRefresh, autoRefreshIntervalMs, logTag, showPagesIndicator);
        }
    }
}
