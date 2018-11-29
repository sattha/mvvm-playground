package com.viinsoft.playground.mvvmmytipcalculator;

import com.viinsoft.playground.mvvmmytipcalculator.view.MainActivity;

import org.junit.Rule;
import org.junit.Test;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class TipCalculatorActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule  = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testTipCalculator() {

        // Calculate Tip
        enter(15.99, 15);
        calculateTip();
        assertOutput("", "$15.99", "$2.40", "$18.39");

        // Save Tip
        saveTip("BBQ Max");
        assertOutput("BBQ Max", "$15.99", "$2.40", "$18.39");

        // Clear Output
        clearOutput();
        assertOutput("", "$0.00", "$0.00", "$0.00");

        // Load Tip
        loadTip("BBQ Max");
        assertOutput("BBQ Max", "$15.99", "$2.40", "$18.39");
    }

    private void enter(Double checkAmount, int tipPercent) {
        onView(withId(R.id.inputCheckAmount)).perform(replaceText(checkAmount.toString()));
        onView(withId(R.id.inputTipPercentage)).perform(replaceText(tipPercent + ""));
    }

    private void calculateTip() {
        onView(withId(R.id.fab)).perform(click());
    }

    private void assertOutput(String name, String checkAmount, String tipAmount, String total) {
        onView(withId(R.id.textCheckAmount)).check(matches(withText(checkAmount)));
        onView(withId(R.id.textTipAmount)).check(matches(withText(tipAmount)));
        onView(withId(R.id.textTotalAmount)).check(matches(withText(total)));
        onView(withId(R.id.textLocationName)).check(matches(withText(name)));
    }

    private void clearOutput() {
        enter(0.0, 0);
        calculateTip();
    }

    private void saveTip(String name) {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getContext());
        onView(withText(R.string.action_save)).perform(click());
        onView(withHint(R.string.save_hint)).perform(replaceText(name));
        onView(withText(R.string.action_save)).perform(click());
    }

    private void loadTip(String name) {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getContext());
        onView(withText(R.string.action_load)).perform(click());
        onView(withText(name)).perform(click());
    }
}
