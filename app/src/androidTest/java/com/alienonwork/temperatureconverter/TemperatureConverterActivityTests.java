package com.alienonwork.temperatureconverter;

import android.app.Activity;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TemperatureConverterActivityTests {


    MainActivity activity;
    EditText celsiusInput;
    EditText fahrenheitInput;
    TextView celsiusLabel;
    TextView fahrenheitLabel;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Before
    public void setUp() {
        activity = mActivityRule.getActivity();
        celsiusInput = (EditText)activity.findViewById(R.id.converter_celsius_input);
        fahrenheitInput = (EditText)activity.findViewById(R.id.converter_fahrenheit_input);
        celsiusLabel = (TextView)activity.findViewById(R.id.converter_celsius_label);
        fahrenheitLabel = (TextView)activity.findViewById(R.id.converter_fahrenheit_label);

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

    @Test
    public void assertfontSizes() {
        float pixelSize = getFloatPixelSize(R.dimen.label_text_size);
        assertEquals(pixelSize, celsiusLabel.getTextSize(), 0.1);
        assertEquals(pixelSize, fahrenheitLabel.getTextSize(), 0.1);
    }

    @Test
    public void celsiusInputMargins() {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)celsiusInput.getLayoutParams();

        assertEquals(getIntPixelSize(R.dimen.margin), lp.leftMargin);
        assertEquals(getIntPixelSize(R.dimen.margin), lp.rightMargin);
    }

     public void fahrenheitInputMargins() {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)fahrenheitInput.getLayoutParams();

        assertEquals(getIntPixelSize(R.dimen.margin), lp.leftMargin);
        assertEquals(getIntPixelSize(R.dimen.margin), lp.rightMargin);
    }

    public void celsiusInputJustification() {
        int expectedGravity = Gravity.END | Gravity.CENTER_VERTICAL;
        int actual = celsiusInput.getGravity();
        String errorMessage = String.format("Expected 0x%02x", expectedGravity, actual);

        assertEquals(errorMessage, expectedGravity, actual);
    }

    public void fahrenheitInputJustification() {
        int expectedGravity = Gravity.END | Gravity.CENTER_VERTICAL;
        int actual = fahrenheitInput.getGravity();
        String errorMessage = String.format("Expected 0x%02x", expectedGravity, actual);

        assertEquals(errorMessage, expectedGravity, actual);
    }

    public void testVirtualKeyboardSpaceReserved() {
        int expected = getIntPixelSize(R.dimen.keyboard_space);
        int actual = fahrenheitInput.getBottom();
        String errorMessage = "Space not reserved, expected " + expected + " actual " + actual;
        assertTrue(errorMessage, actual <= expected);
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

    private float getFloatPixelSize(int dimensionResourceId) {
        return activity.getResources().getDimensionPixelSize(dimensionResourceId);
    }

    private int getIntPixelSize(int margin) {
        return (int) getFloatPixelSize(margin);
    }
}
