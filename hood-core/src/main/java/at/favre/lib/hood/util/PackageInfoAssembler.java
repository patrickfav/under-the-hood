package at.favre.lib.hood.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.Signature;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import at.favre.lib.hood.Hood;
import at.favre.lib.hood.interfaces.PageEntry;
import at.favre.lib.hood.interfaces.Section;
import at.favre.lib.hood.util.defaults.DefaultProperties;

/**
 * Helper class to assemble packageManager packageInfo data for different configurations;
 */
public class PackageInfoAssembler {
    private static final String TAG = PackageInfoAssembler.class.getSimpleName();

    public enum Type {
        /**
         * Package name and versions
         */
        APK_VERSION_INFO(null, "Versions", new PageEntryProvider() {
            @Override
            public List<PageEntry<?>> getEntries(@NonNull Context context, @NonNull PackageInfo packageInfo) {
                return createPmApkVersionInfo(packageInfo);
            }
        }),
        /**
         * Declared services
         */
        SERVICES(PackageManager.GET_SERVICES, "Services", new PageEntryProvider() {
            @Override
            public List<PageEntry<?>> getEntries(@NonNull Context context, @NonNull PackageInfo packageInfo) {
                return createPmServiceInfo(packageInfo);
            }
        }),
        /**
         * Declared broadcast receivers
         */
        RECEIVERS(PackageManager.GET_RECEIVERS, "Broadcast Receivers", new PageEntryProvider() {
            @Override
            public List<PageEntry<?>> getEntries(@NonNull Context context, @NonNull PackageInfo packageInfo) {
                return createPmBroadcastReceiversInfo(packageInfo);
            }
        }),
        /**
         * Declared providers
         */
        PROVIDER(PackageManager.GET_PROVIDERS, "Providers", new PageEntryProvider() {
            @Override
            public List<PageEntry<?>> getEntries(@NonNull Context context, @NonNull PackageInfo packageInfo) {
                return createPmProviderInfo(packageInfo);
            }
        }),
        /**
         * Declared activities
         */
        ACTIVITIES(PackageManager.GET_ACTIVITIES, "Activities", new PageEntryProvider() {
            @Override
            public List<PageEntry<?>> getEntries(@NonNull Context context, @NonNull PackageInfo packageInfo) {
                return createPmActivitiesInfo(packageInfo);
            }
        }),
        /**
         * Apk signing signature
         */
        SIGNATURE(PackageManager.GET_SIGNATURES, "Signatures", new PageEntryProvider() {
            @Override
            public List<PageEntry<?>> getEntries(@NonNull Context context, @NonNull PackageInfo packageInfo) {
                return createPmSignatureHashInfo(packageInfo);
            }
        }),
        /**
         * Declared permissions
         */
        PERMISSIONS(PackageManager.GET_PERMISSIONS, "Permissions", new PageEntryProvider() {
            @Override
            public List<PageEntry<?>> getEntries(@NonNull Context context, @NonNull PackageInfo packageInfo) {
                return createPmPermissionInfo(context, packageInfo, false);
            }
        }),
        /**
         * Declared uses-features
         */
        USES_FEATURE(PackageManager.GET_CONFIGURATIONS, "System Features", new PageEntryProvider() {
            @Override
            public List<PageEntry<?>> getEntries(@NonNull Context context, @NonNull PackageInfo packageInfo) {
                return createPmDeclaredSystemFeatureInfo(context, packageInfo);
            }
        }),
        /**
         * Install time and location
         */
        APK_INSTALL_INFO(null, "Install Info", new PageEntryProvider() {
            @Override
            public List<PageEntry<?>> getEntries(@NonNull Context context, @NonNull PackageInfo packageInfo) {
                return createApkStateInfo(packageInfo);
            }
        });

        /**
         * See PackageManager.GET_* flags
         */
        private final Integer requestFlag;

        /**
         * Header for {@link at.favre.lib.hood.interfaces.Section.HeaderSection}
         */
        private final String header;

        private final PageEntryProvider pageEntryProvider;

        Type(Integer requestFlag, String header, PageEntryProvider pageEntryProvider) {
            this.requestFlag = requestFlag;
            this.header = header;
            this.pageEntryProvider = pageEntryProvider;
        }
    }

    private final EnumSet<Type> typeSet;
    private final String packageName;

    /**
     * Same as {@link PackageInfoAssembler} constructor, but will use the current's packageName
     *
     * @param type must provide types - each type will request a certain packageManager info category (see {@link Type}
     */
    public PackageInfoAssembler(@NonNull Type type, Type... types) {
        this(null, type, types);
    }

    /**
     * Creates and configures a new assembler which uses given types as request for the info to return
     * in {@link #createSection(Context, boolean)}.
     *
     * @param packageName for which packageName (ie. app) you want the info for
     * @param type        must provide types - each type will request a certain packageManager info category (see {@link Type}
     */
    public PackageInfoAssembler(String packageName, @NonNull Type type, Type... types) {
        this.packageName = packageName;
        this.typeSet = EnumSet.of(type, types);
    }

    /**
     * Same as {@link #createSection(Context, boolean)} but will include all sub headers
     *
     * @param context must not be null, used for getting the {@link PackageManager}
     * @return section containing the info
     */
    public Section createSection(@Nullable Context context) {
        return createSection(context, true);
    }

    /**
     * Creates a section containing all requested types with or without sub headers for each type.
     * If something went wrong (e.g. unknown package) an error message will be shown in ui
     *
     * @param context           must not be null, used for getting the {@link PackageManager}
     * @param addSectionHeaders if true includes a section header before very type
     * @return section containing the info
     */
    public Section createSection(@Nullable Context context, boolean addSectionHeaders) {
        Section.ModifiableHeaderSection mainSection = Hood.internal().createSection("");
        if (context != null) {
            String targetPackageName = packageName == null ? context.getPackageName() : packageName;
            try {
                @SuppressWarnings("WrongConstant")
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), getRequiredRequestFlags(typeSet));

                if (packageInfo == null) {
                    throw new IllegalStateException("info was null");
                }

                for (Type type : typeSet) {
                    mainSection.add(Hood.internal().createSection(addSectionHeaders ? type.header : null, type.pageEntryProvider.getEntries(context, packageInfo)));
                }
            } catch (Exception e) {
                mainSection.setErrorMessage("Could not get packageInfo for " + targetPackageName + ": " + e.getClass() + " (" + e.getMessage() + ")");
                Log.w(TAG, mainSection.getErrorMessage(), e);
            }
        }
        return mainSection.removeHeader();
    }

    private int getRequiredRequestFlags(EnumSet<Type> requestedTypes) {
        int flags = 0;

        for (Type requestedType : requestedTypes) {
            if (requestedType.requestFlag != null) {
                flags |= requestedType.requestFlag;
            }
        }

        return flags;
    }

    private interface PageEntryProvider {
        List<PageEntry<?>> getEntries(@NonNull Context context, @NonNull PackageInfo packageInfo);
    }

    /* *************************************************************************** SUb-ASSEMBLERS */

    /**
     * Includes info about when and where the apk was installed
     *
     * @param packageInfo from {@link PackageManager#getPackageInfo(String, int)}
     * @return list of apk info
     */
    public static List<PageEntry<?>> createPmApkVersionInfo(@NonNull PackageInfo packageInfo) {
        List<PageEntry<?>> entries = new ArrayList<>();
        entries.add(Hood.createPropertyEntry("package-name", packageInfo.packageName));
        entries.add(Hood.createPropertyEntry("version-name", packageInfo.versionName));
        entries.add(Hood.createPropertyEntry("version-code", String.valueOf(packageInfo.versionCode)));
        return entries;
    }

    /**
     * Lists all defined services with additional meta-data
     *
     * @param packageInfo from {@link PackageManager#getPackageInfo(String, int)} requiring {@link PackageManager#GET_SERVICES} flag
     * @return list of services
     */
    public static List<PageEntry<?>> createPmServiceInfo(@NonNull PackageInfo packageInfo) {
        List<PageEntry<?>> entries = new ArrayList<>();
        if (packageInfo.services != null) {
            for (ServiceInfo service : packageInfo.services) {
                if (service != null) {
                    entries.add(Hood.createPropertyEntry(service.name,
                            "exported: " + service.exported + "\n" +
                                    "enabled: " + service.enabled + "\n" +
                                    "flags: " + service.exported + "\n" +
                                    "process: " + service.processName + "\n" +
                                    "req-permission: " + service.permission + "\n", true));
                }
            }
        }
        return entries;
    }

    /**
     * Lists all defined broadcast receivers with additional meta-data
     *
     * @param packageInfo from {@link PackageManager#getPackageInfo(String, int)} requiring {@link PackageManager#GET_RECEIVERS} flag
     * @return entries
     */
    public static List<PageEntry<?>> createPmBroadcastReceiversInfo(@NonNull PackageInfo packageInfo) {
        List<PageEntry<?>> entries = new ArrayList<>();
        if (packageInfo.receivers != null) {
            for (ActivityInfo receiver : packageInfo.receivers) {
                if (receiver != null) {
                    entries.add(Hood.createPropertyEntry(receiver.name,
                            "exported: " + receiver.exported + "\n" +
                                    "enabled: " + receiver.enabled + "\n", true));
                }
            }
        }
        return entries;
    }

    /**
     * Lists all defined providers with additional meta-data
     *
     * @param packageInfo from {@link PackageManager#getPackageInfo(String, int)} requiring {@link PackageManager#GET_PROVIDERS} flag
     * @return entries
     */
    public static List<PageEntry<?>> createPmProviderInfo(@NonNull PackageInfo packageInfo) {
        List<PageEntry<?>> entries = new ArrayList<>();
        if (packageInfo.providers != null) {
            for (ProviderInfo provider : packageInfo.providers) {
                if (provider != null) {
                    entries.add(Hood.createPropertyEntry(provider.name,
                            "exported: " + provider.exported + "\n" +
                                    "enabled: " + provider.enabled + "\n" +
                                    "authorities: " + provider.authority + "\n" +
                                    "multi-process: " + provider.multiprocess + "\n" +
                                    "read-perm: " + provider.readPermission + "\n" +
                                    "write-perm: " + provider.writePermission + "\n", true));
                }
            }
        }
        return entries;
    }

    /**
     * Lists all defined activities with additional meta-data
     *
     * @param packageInfo from {@link PackageManager#getPackageInfo(String, int)} requiring {@link PackageManager#GET_ACTIVITIES} flag
     * @return entries
     */
    public static List<PageEntry<?>> createPmActivitiesInfo(@NonNull PackageInfo packageInfo) {
        List<PageEntry<?>> entries = new ArrayList<>();
        if (packageInfo.activities != null) {
            for (ActivityInfo receiver : packageInfo.activities) {
                if (receiver != null) {
                    entries.add(Hood.createPropertyEntry(receiver.name,
                            "exported: " + receiver.exported + "\n" +
                                    "enabled: " + receiver.enabled + "\n", true));
                }
            }
        }
        return entries;
    }

    /**
     * Includes info about when and where the apk was installed
     *
     * @param packageInfo from {@link PackageManager#getPackageInfo(String, int)}
     * @return entries
     */
    public static List<PageEntry<?>> createApkStateInfo(@NonNull PackageInfo packageInfo) {
        List<PageEntry<?>> entries = new ArrayList<>();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            entries.add(Hood.createPropertyEntry("apk-location", String.valueOf(TypeTranslators.translatePMInstallLocation(packageInfo.installLocation))));
        }
        entries.add(Hood.createPropertyEntry("apk-first-install", HoodUtil.toSimpleDateTimeFormat(packageInfo.firstInstallTime)));
        entries.add(Hood.createPropertyEntry("apk-reinstall", HoodUtil.toSimpleDateTimeFormat(packageInfo.lastUpdateTime)));
        return entries;
    }

    /**
     * Creates page-entries for all the apk's signatures and shows sh256 hash of it
     *
     * @param packageInfo from {@link PackageManager#getPackageInfo(String, int)} requiring {@link PackageManager#GET_SIGNATURES} flag
     * @return entries
     */
    public static List<PageEntry<?>> createPmSignatureHashInfo(@NonNull PackageInfo packageInfo) {
        List<PageEntry<?>> entries = new ArrayList<>();
        try {
            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(signature.toByteArray());
                entries.add(Hood.createPropertyEntry("apk-signature-sha256", HoodUtil.byteToHex(md.digest()), true));
            }
        } catch (Exception e) {
            throw new IllegalStateException("could not create hash", e);
        }
        return entries;
    }

    /**
     * Returns page-entry for each defined permission in the app (the passed activity belongs to).
     * <p>
     * See {@link DefaultProperties#createSectionRuntimePermissions(Activity, List)} for more details
     *
     * @param context                  must not be null and must be instance of activity (needed for getting the state)
     * @param packageInfo              from {@link PackageManager#getPackageInfo(String, int)} requiring {@link PackageManager#GET_PERMISSIONS} flag
     * @param onlyDangerousPermissions only include permissions with flag PROTECTION_DANGEROUS (ie. have to be granted by the user)
     * @return list of page-entries
     */
    @SuppressLint("NewApi")
    public static List<PageEntry<?>> createPmPermissionInfo(final @NonNull Context context, @NonNull PackageInfo packageInfo, boolean onlyDangerousPermissions) {

        if (!(context instanceof Activity)) {
            throw new IllegalArgumentException("context must be of type activity - needed for getting current permission state");
        }

        List<PageEntry<?>> entries = new ArrayList<>();
        if (packageInfo.requestedPermissions != null && packageInfo.requestedPermissions.length > 0) {
            List<String> permissionNames = new ArrayList<>();
            for (int i = 0; i < packageInfo.requestedPermissions.length; i++) {
                if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1
                        || !onlyDangerousPermissions
                        || packageInfo.requestedPermissionsFlags[i] == PermissionInfo.PROTECTION_DANGEROUS) {
                    permissionNames.add(packageInfo.requestedPermissions[i]);
                }
            }
            Collections.sort(permissionNames);

            return DefaultProperties.createSectionRuntimePermissions(((Activity) context), permissionNames).removeHeader().asEntryList();
        }

        return entries;
    }

    /**
     * Creates entries for all declared system features (see Manifest &lt;uses-feature> tags)
     * Uses {@link DefaultProperties#createSystemFeatureInfo(Context, Map)} call.
     *
     * @param context     must not be null
     * @param packageInfo from {@link PackageManager#getPackageInfo(String, int)} requiring {@link PackageManager#GET_CONFIGURATIONS} flag
     * @return list of all declared uses-feature tags in AndroidManifest as page entries
     */
    public static List<PageEntry<?>> createPmDeclaredSystemFeatureInfo(@NonNull Context context, @NonNull PackageInfo packageInfo) {
        try {
            Map<CharSequence, String> featureMap = new TreeMap<>();
            if (packageInfo.reqFeatures != null && packageInfo.reqFeatures.length > 0) {
                for (FeatureInfo reqFeature : packageInfo.reqFeatures) {
                    boolean required = reqFeature.flags == FeatureInfo.FLAG_REQUIRED;
                    String fullLabel = reqFeature.name + (required ? " (req)" : "");
                    featureMap.put(Hood.internal().createFullLabel(fullLabel.replace("android.hardware.", ""), fullLabel), reqFeature.name);
                }
            }

            return DefaultProperties.createSystemFeatureInfo(context, featureMap);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
