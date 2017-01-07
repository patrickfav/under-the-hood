package at.favre.lib.hood.interfaces;

/**
 * All pre-defined view-types used for {@link ViewTemplate#getViewType()} in {@link at.favre.lib.hood.page}
 */
public final class ViewTypes {

    public static final int VIEWTYPE_HEADER = 1 << 16;
    public static final int VIEWTYPE_KEYVALUE = 1 << 16 + 1;
    public static final int VIEWTYPE_ACTION = 1 << 16 + 2;
    public static final int VIEWTYPE_ACTION_DOUBLE = 1 << 16 + 3;
    public static final int VIEWTYPE_CONFIG_BOOL = 1 << 16 + 4;
    public static final int VIEWTYPE_CONFIG_SPINNER = 1 << 16 + 5;
    public static final int VIEWTYPE_KEYVALUE_MULTILINE = 1 << 16 + 6;
    public static final int VIEWTYPE_MESSAGE = 1 << 16 + 7;
}
