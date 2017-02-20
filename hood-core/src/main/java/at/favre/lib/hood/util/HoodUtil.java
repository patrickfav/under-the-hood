package at.favre.lib.hood.util;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
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
     * Use with {@link PackageInfoAssembler#createPmSignatureHashInfo(PackageInfo)}. Used to be able to
     * name certificates, if you use multiple in e.g. testing. Uses {@link String#startsWith(String)}
     * to check if they are equal.
     *
     * @param certificatesFingerprint            the raw (title,sha256-fingerprint)
     * @param knownCertificateFingerPrintNameMap map of known certificates with (name,sha256-fingerprint)
     * @return map with names
     */
    public static Map<String, String> appendCertificateNameToSha256Fingerprint(Map<String, String> certificatesFingerprint, Map<String, String> knownCertificateFingerPrintNameMap) {
        Map<String, String> out = new HashMap<>();
        for (Map.Entry<String, String> entry : certificatesFingerprint.entrySet()) {
            for (Map.Entry<String, String> knownCertEntry : knownCertificateFingerPrintNameMap.entrySet()) {
                if (entry.getValue().toLowerCase().startsWith(knownCertEntry.getValue().toLowerCase())) {
                    out.put(entry.getKey() + " (" + knownCertEntry.getKey() + ")", entry.getValue());
                    break;
                }
            }
            out.put(entry.getKey(), entry.getValue());
        }
        return out;
    }
}
