package at.favre.app.hood.demo;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
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
