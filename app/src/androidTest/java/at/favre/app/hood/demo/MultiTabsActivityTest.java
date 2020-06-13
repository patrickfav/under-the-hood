package at.favre.app.hood.demo;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
public class MultiTabsActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void multiTabsActivityTest() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_start_dark_multi_page), withText("Start Multi Page")));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction switchableViewpager = onView(
                allOf(withId(R.id.view_pager), isDisplayed()));
        switchableViewpager.perform(swipeLeft());

        ViewInteraction switchableViewpager2 = onView(
                allOf(withId(R.id.view_pager), isDisplayed()));
        switchableViewpager2.perform(swipeLeft());

        ViewInteraction textView = onView(
                allOf(withText("Details"),
                        withParent(allOf(withId(R.id.tabs),
                                withParent(withId(R.id.view_pager)))),
                        isDisplayed()));
        textView.perform(click());

        ViewInteraction switchableViewpager3 = onView(
                allOf(withId(R.id.view_pager), isDisplayed()));
        switchableViewpager3.perform(swipeRight());

        ViewInteraction textView2 = onView(
                allOf(withText("General"),
                        withParent(allOf(withId(R.id.tabs),
                                withParent(withId(R.id.view_pager)))),
                        isDisplayed()));
        textView2.perform(click());

        ViewInteraction switchableViewpager4 = onView(
                allOf(withId(R.id.view_pager), isDisplayed()));
        switchableViewpager4.perform(swipeRight());

    }

}
