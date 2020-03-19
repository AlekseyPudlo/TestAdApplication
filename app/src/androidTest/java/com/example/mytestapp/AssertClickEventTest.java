package com.example.mytestapp;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.IdlingPolicies;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

@RunWith(AndroidJUnit4.class)
public class AssertClickEventTest {
    private static final String AD_CLICKED = "Ad clicked";
    //private IdlingResource mIdlingResource;

    // Preferred JUnit 4 mechanism of specifying the activity to be launched before each test
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void shouldInvokeOnTapEvent() throws Exception {
        // Press the 'Show The Ad' button.
        onView(withId(R.id.button_showAd))
                .perform(click());

        new Wait(new Wait.Condition() {
            @Override
            public boolean check() {
                return activityTestRule.getActivity().getAdLoaded();
            }
        }).waitForIt();

        // Click on banner
        onView(withId(R.id.bannerView)).perform(click());

        // Confirm that event happened. Toast message appears only after onAdClicked event invoked
        onView(withText(AD_CLICKED))
                .inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }
}
