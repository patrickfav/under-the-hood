package at.favre.lib.hood.util;


/**
 * Simple synthetic exception used to crash the app for debugging proposes
 */
public class DebugCrashException extends RuntimeException {
    public DebugCrashException() {
        super("This is a exception used for debugging purposes - this is not an error in the code.");
    }
}
