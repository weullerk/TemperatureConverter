package com.alienonwork.temperatureconverter;

import android.app.Activity;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.PositionAssertions.isLeftAlignedWith;
import static android.support.test.espresso.assertion.PositionAssertions.isRightAlignedWith;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class TemperatureConverterActivityTests {


    MainActivity activity;
    EditText celsiusInput;
    EditText fahrenheitInput;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Before
    public void setUp() {
        activity = mActivityRule.getActivity();
        celsiusInput = (EditText)activity.findViewById(R.id.converter_celsius_input);
        fahrenheitInput = (EditText)activity.findViewById(R.id.converter_fahrenheit_input);
    }

    @Test
    public void hasInputFields() {
        assertNotNull(celsiusInput);
        assertNotNull(fahrenheitInput);
    }

    @Test
    public void areFieldsOnScreen() {
        onView(withId(R.id.converter_celsius_input)).check(matches(isDisplayed()));
        onView(withId(R.id.converter_fahrenheit_input)).check(matches(isDisplayed()));
    }

    @Test
    public void areFieldsAligned() {
        onView(withId(R.id.converter_celsius_label)).check(isLeftAlignedWith(withId(R.id.converter_celsius_input)));
        onView(withId(R.id.converter_fahrenheit_label)).check(isLeftAlignedWith(withId(R.id.converter_fahrenheit_input)));
        onView(withId(R.id.converter_celsius_input)).check(isLeftAlignedWith(withId(R.id.converter_fahrenheit_input)));
        onView(withId(R.id.converter_celsius_input)).check(isRightAlignedWith(withId(R.id.converter_fahrenheit_input)));
    }

    @Test
    public void celciusInputFieldCoversEntireScreen() {
        onView(withId(R.id.converter_celsius_input)).check(matches(withWidth(ViewGroup.LayoutParams.MATCH_PARENT)));
    }

    private Matcher<View> withWidth(final int width) {
        return new BoundedMatcher<View, View>(View.class) {
            @Override
            public void describeTo(final Description description) {
                description.appendText("Width expected: " + String.valueOf(width));
            }

            @Override
            protected boolean matchesSafely(final View item) {
                return item.getLayoutParams().width == width;
            }
        };
    }
}
