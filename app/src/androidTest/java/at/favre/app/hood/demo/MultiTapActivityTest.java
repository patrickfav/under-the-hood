package at.favre.app.hood.demo;

import android.os.SystemClock;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class MultiTapActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void multiTapActivityTEst() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_more), withText("More")));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.tv_doubletap), withText("Double Tap Me"), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(R.id.tv_doubletap), withText("Double Tap Me"), isDisplayed()));
        appCompatTextView2.perform(click());

        SystemClock.sleep(300);

        pressBack();

        SystemClock.sleep(300);

        ViewInteraction appCompatTextView5 = onView(
                allOf(withId(R.id.tv_tripletap), withText("Triple Tap Me"), isDisplayed()));
        appCompatTextView5.perform(click());

        ViewInteraction appCompatTextView6 = onView(
                allOf(withId(R.id.tv_tripletap), withText("Triple Tap Me"), isDisplayed()));
        appCompatTextView6.perform(click());

        ViewInteraction appCompatTextView7 = onView(
                allOf(withId(R.id.tv_tripletap), withText("Triple Tap Me"), isDisplayed()));
        appCompatTextView7.perform(click());

        SystemClock.sleep(300);

        pressBack();
    }

}
