package at.favre.lib.hood.defaults;


import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.util.Log;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.favre.lib.hood.page.values.DynamicValue;
import at.favre.lib.hood.util.HoodUtil;
import at.favre.lib.hood.page.entries.HeaderEntry;
import at.favre.lib.hood.page.entries.KeyValueEntry;
import at.favre.lib.hood.page.PageEntry;

public class DefaultProperties {

    private static final String TAG = DefaultProperties.class.getName();

    public static List<PageEntry<?>> createDeviceInfo(boolean includeHeader) {
        List<PageEntry<?>> entries = new ArrayList<>();
        if (includeHeader) {
            entries.add(new HeaderEntry("Device"));
        }
        entries.add(new KeyValueEntry("model", Build.MODEL));
        entries.add(new KeyValueEntry("name", Build.DEVICE));
        entries.add(new KeyValueEntry("brand", Build.MANUFACTURER));
        entries.add(new KeyValueEntry("version", Build.VERSION.RELEASE));
        entries.add(new KeyValueEntry("version_minor", Build.VERSION.INCREMENTAL));
        entries.add(new KeyValueEntry("build_id", Build.ID));
        entries.add(new KeyValueEntry("sdk_int", String.valueOf(Build.VERSION.SDK_INT)));
        entries.add(new KeyValueEntry("serial", Build.SERIAL));

        return entries;
    }

    public static List<PageEntry<?>> createSignatureHashInfo(Context context) {
        List<PageEntry<?>> entries = new ArrayList<>();

        try {
            final PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(signature.toByteArray());
                entries.add(new KeyValueEntry("signer_sha256", HoodUtil.byteToHex(md.digest()),true));
            }
        } catch (Exception e) {
            Log.e(TAG, "could not read apk signature", e);
        }

        return entries;
    }

    public static List<PageEntry<?>> createAppVersionInfo(Class<?> buildConfig, boolean includeHeader) {
        List<PageEntry<?>> entries = new ArrayList<>();
        if (includeHeader) {
            entries.add(new HeaderEntry("App Version"));
        }

        Field[] declaredFields = buildConfig.getDeclaredFields();
        for (Field field : declaredFields) {
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                try {
                    String key = null;
                    String value = String.valueOf(field.get(null));
                    switch (field.getName().toLowerCase()) {
                        case "version_name":
                            key = "version";
                            break;
                        case "version_code":
                            key = "version_code";
                            break;
                        case "application_id":
                            key = "app_id";
                            break;
                        case "build_type":
                            key = "build_type";
                            break;
                        case "flavor":
                            key = "flavor";
                            break;
                        case "debug":
                            key = "debug";
                            break;
                    }

                    if (key != null && value != null && !value.trim().isEmpty()) {
                        entries.add(new KeyValueEntry(key, String.valueOf(field.get(null))));
                    }

                } catch (Exception e) {
                    Log.w(TAG, "could not get field from BuildConfig (" + field + ")");
                }

            }
        }

        return entries;
    }

    public static List<PageEntry<?>> createRuntimePermissionInfo(final Activity activity, boolean includeHeader, String androidPermission, String... morePermissions) {
        final List<String> permissions = new ArrayList<>();
        List<PageEntry<?>> entries = new ArrayList<>();
        permissions.add(androidPermission);
        Collections.addAll(permissions, morePermissions);

        if (includeHeader) {
            entries.add(new HeaderEntry("Permissions"));
        }

        for (final String perm : permissions) {
            entries.add(new KeyValueEntry(perm.replace("android.permission.", ""), new DynamicValue<String>() {
                @Override
                public String getValue() {
                    switch (HoodUtil.getPermissionStatus(activity, perm)) {
                        case HoodUtil.GRANTED:
                            return "GRANTED";
                        case HoodUtil.DENIED:
                            return "DENIED";
                        case HoodUtil.BLOCKED:
                            return "BLOCKED/NOT ASKED";
                        default:
                            return "UNKNOWN";
                    }
                }
            },false));
        }

        return entries;
    }
}
