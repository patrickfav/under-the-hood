package at.favre.lib.hood.util;

import org.junit.Test;

import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.fail;

public class HoodUtilTest {
    @Test
    public void testByteToHex() throws Exception {
        try {
            HoodUtil.byteToHex(null);
            fail();
        } catch (NullPointerException e) {
        }

        assertEquals("", HoodUtil.byteToHex(new byte[0]).toLowerCase());
        assertEquals("e5", HoodUtil.byteToHex(new byte[]{-27}).toLowerCase());
        assertEquals("cfac", HoodUtil.byteToHex(new byte[]{-49, -84}).toLowerCase());
        assertEquals("88fd8c", HoodUtil.byteToHex(new byte[]{-120, -3, -116}).toLowerCase());
        assertEquals("2516c585", HoodUtil.byteToHex(new byte[]{37, 22, -59, -123}).toLowerCase());
        assertEquals("01a1bc1e65", HoodUtil.byteToHex(new byte[]{1, -95, -68, 30, 101}).toLowerCase());
        assertEquals("b8cc92ee1711", HoodUtil.byteToHex(new byte[]{-72, -52, -110, -18, 23, 17}).toLowerCase());
        assertEquals("5fc1e2958c699d", HoodUtil.byteToHex(new byte[]{95, -63, -30, -107, -116, 105, -99}).toLowerCase());
        assertEquals("f9373f5de142dfec", HoodUtil.byteToHex(new byte[]{-7, 55, 63, 93, -31, 66, -33, -20}).toLowerCase());
        assertEquals("f3fd69c164245a7ff0", HoodUtil.byteToHex(new byte[]{-13, -3, 105, -63, 100, 36, 90, 127, -16}).toLowerCase());
        assertNotSame("eefd19c164245a72f1", HoodUtil.byteToHex(new byte[]{-13, -3, 105, -63, 100, 36, 90, 127, -16}).toLowerCase());
    }

    @Test
    public void testSimpleDate() throws Exception {
        assertNotNull(HoodUtil.toSimpleDateTimeFormat(-1));
        assertNotNull(HoodUtil.toSimpleDateTimeFormat(0));
        assertNotNull(HoodUtil.toSimpleDateTimeFormat(1));
        assertNotNull(HoodUtil.toSimpleDateTimeFormat(new Date().getTime()));
    }

    @Test
    public void testGetConditionally() throws Exception {
        assertNull(HoodUtil.getConditionally("hello", false));
        assertEquals("hello", HoodUtil.getConditionally("hello", true));
    }

    @Test
    public void testBytesHumanReadable() throws Exception {
        assertEquals("1 B", HoodUtil.humanReadableByteCount(1, false));
        assertEquals("2 B", HoodUtil.humanReadableByteCount(2, false));
        assertEquals("1023 B", HoodUtil.humanReadableByteCount(1023, false));
        assertEquals("1.0 KiB", HoodUtil.humanReadableByteCount(1024, false));
        assertEquals("1.0 MiB", HoodUtil.humanReadableByteCount(1024 * 1024, false));
        assertEquals("1.0 GiB", HoodUtil.humanReadableByteCount(1024 * 1024 * 1024, false));

        assertEquals("1 B", HoodUtil.humanReadableByteCount(1, true));
        assertEquals("2 B", HoodUtil.humanReadableByteCount(2, true));
        assertEquals("999 B", HoodUtil.humanReadableByteCount(999, true));
        assertEquals("1.0 kB", HoodUtil.humanReadableByteCount(1000, true));
        assertEquals("1.0 MB", HoodUtil.humanReadableByteCount(1000 * 1000, true));
        assertEquals("1.0 GB", HoodUtil.humanReadableByteCount(1000 * 1000 * 1000, true));
    }
}
