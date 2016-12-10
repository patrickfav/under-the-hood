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

        public Builder setShowZebra(boolean showZebra) {
            this.showZebra = showZebra;
            return this;
        }

        public Builder setAutoLog(boolean autoLog) {
            this.autoLog = autoLog;
            return this;
        }

        public Builder setLogTag(String logTag) {
            this.logTag = logTag;
            return this;
        }

        public Config build() {
            return new Config(showZebra, autoLog, logTag);
        }
    }
}
