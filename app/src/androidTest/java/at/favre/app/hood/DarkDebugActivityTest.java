package at.favre.app.hood;


import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.runner.RunWith;

import at.favre.app.hood.demo.MainActivity;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DarkDebugActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

}
