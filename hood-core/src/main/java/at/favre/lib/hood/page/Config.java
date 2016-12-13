package at.favre.lib.hood.page;

public class Config {
    private static final String TAG = Config.class.getName();

    public final boolean showZebra;
    public final boolean showHighlightContent;
    public final boolean autoLog;
    public final String logTag;

    private Config(boolean showZebra, boolean showHighlightContent, boolean autoLog, String logTag) {
        this.showZebra = showZebra;
        this.showHighlightContent = showHighlightContent;
        this.autoLog = autoLog;
        this.logTag = logTag;
    }

    public static class Builder {
        private boolean showZebra = true;
        private boolean showHighlightContent = false;
        private boolean autoLog = true;
        private String logTag = TAG;

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
         * @param autoLog
         */
        public Builder setAutoLog(boolean autoLog) {
            this.autoLog = autoLog;
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

        public Config build() {
            return new Config(showZebra, showHighlightContent, autoLog, logTag);
        }
    }
}
