package at.favre.lib.hood.page;

public class Config {
    private static final String TAG = Config.class.getName();

    public final boolean showZebra;
    public final boolean autoLog;
    public final String logTag;

    private Config(boolean showZebra, boolean autoLog, String logTag) {
        this.showZebra = showZebra;
        this.autoLog = autoLog;
        this.logTag = logTag;
    }

    public static class Builder {
        private boolean showZebra = true;
        private boolean autoLog = true;
        private String logTag = TAG;

        /**
         * If the ui should have a zebra (highlighting every other row)
         *
         * @param showZebra
         */
        public Builder setShowZebra(boolean showZebra) {
            this.showZebra = showZebra;
            return this;
        }

        /**
         * If true will automatically log the whole page content to console when the view
         * is instantiated.
         * @param autoLog
         */
        public Builder setAutoLog(boolean autoLog) {
            this.autoLog = autoLog;
            return this;
        }

        /**
         * Sets the tag which is used to log debug data to console (see {@link android.util.Log#w(String, String)}
         * @param logTag
         */
        public Builder setLogTag(String logTag) {
            this.logTag = logTag;
            return this;
        }

        public Config build() {
            return new Config(showZebra, autoLog, logTag);
        }
    }
}
