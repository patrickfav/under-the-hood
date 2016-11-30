package at.favre.lib.hood.page.entries;


final class ViewTypes {

    static final int VIEWTYPE_HEADER = 1 << 16;
    static final int VIEWTYPE_KEYVALUE = 1 << 16 + 1;
    static final int VIEWTYPE_ACTION = 1 << 16 + 2;
    static final int VIEWTYPE_ACTION_DOUBLE = 1 << 16 + 3;
    static final int VIEWTYPE_CONFIG_BOOL = 1 << 16 + 4;
    static final int VIEWTYPE_CONFIG_SPINNER = 1 << 16 + 5;
    static final int VIEWTYPE_KEYVALUE_MULTILINE = 1 << 16 + 6;

}
