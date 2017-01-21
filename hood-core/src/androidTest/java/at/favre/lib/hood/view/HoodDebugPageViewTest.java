package at.favre.lib.hood.view;

import android.support.test.runner.AndroidJUnit4;

import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class HoodDebugPageViewTest {

//    @Test
//    public void testCreateDebugViewEmpty() throws Exception {
//        HoodDebugPageView view = new HoodDebugPageView(InstrumentationRegistry.getTargetContext());
//        assertNull(view.getPages());
//        try {
//            view.refresh();
//            fail();
//        } catch (IllegalStateException e) {
//        }
//    }
//
//    @Test
//    public void testCreateDebugViewSingleEntry() throws Exception {
//        HoodDebugPageView view = new HoodDebugPageView(InstrumentationRegistry.getTargetContext());
//        Pages pages = Hood.get().createPages(Config.newBuilder().build());
//        Page page = pages.addNewPage("one");
//        page.add(Hood.get().createPropertyEntry("key", "value"));
//        view.setPageData(pages);
//
//        view.refresh();
//        assertEquals(pages.getAll().size(), view.getPages().getAll().size());
//    }
}