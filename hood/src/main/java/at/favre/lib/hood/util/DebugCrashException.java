package at.favre.lib.hood.util;


public class DebugCrashException extends RuntimeException {
    public DebugCrashException() {
        super("This is a exception used for debugging purposes - this is not an error in the code.");
    }
}
