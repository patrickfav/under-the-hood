package at.favre.lib.hood.util;


import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class HoodUtil {
    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String byteToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars).toLowerCase();
    }

    public static <T> T getConditionally(T object, boolean shouldReturnEntry) {
        return shouldReturnEntry ? object : null;
    }

    public static Locale getCurrentLocale(Context ctx) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return ctx.getResources().getConfiguration().getLocales().get(0);
        } else {
            //noinspection deprecation
            return ctx.getResources().getConfiguration().locale;
        }
    }

    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format(Locale.US, "%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    /**
     * Convert a millisecond duration to a string format
     *
     * @param millis A duration to convert to a string form
     * @return A string of the form "X Days Y Hours Z Minutes A Seconds".
     */
    public static String millisToDaysHoursMinString(long millis) {
        if (millis < 0) {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }

        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        StringBuilder sb = new StringBuilder(64);
        if (days > 0) {
            sb.append(days);
            sb.append(" Days ");
        }
        if (hours > 0) {
            sb.append(hours);
            sb.append(" Hours ");
        }
        if (minutes > 0) {
            sb.append(minutes);
            sb.append(" Min ");
        }
        sb.append(seconds);
        sb.append(" Sec");

        return sb.toString();
    }

    public static String toSimpleDateTimeFormat(long millisEpochUtc) {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US).format(new Date(millisEpochUtc));
    }

    /**
     * Shortens the string to 2x maxCharLengthLeftOrRight and obfuscates the middle with 3x obfuscationChar
     *
     * @param original                 text to obfuscate
     * @param maxCharLengthLeftOrRight how many chars to keep on each side
     * @param obfuscationChar          the char used to obfuscate (usally e.g. '*')
     * @return the obfuscated string
     */
    public static String obfuscateAndShorten(@Nullable String original, int maxCharLengthLeftOrRight,
                                             char obfuscationChar) {
        if (original == null || original.trim().length() <= 1) {
            return original;
        }

        if (maxCharLengthLeftOrRight <= 0) {
            throw new IllegalArgumentException("max char length must be gt 0");
        }

        String star = String.valueOf(obfuscationChar);

        if (original.length() <= maxCharLengthLeftOrRight * 2) {
            return original.substring(0, original.length() / 2) + star + star + star + original.substring((original.length() / 2) + 1, original.length());
        } else {
            return original.substring(0, maxCharLengthLeftOrRight) + star + star + star + original.substring(original.length() - maxCharLengthLeftOrRight, original.length());
        }
    }
}
