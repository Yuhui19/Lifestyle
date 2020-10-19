package com.example.lifestyle;

import android.app.Activity;
import android.view.Display;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.junit.Assert.*;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.assertion.ViewAssertions.*;

@RunWith(AndroidJUnit4.class)
public class EditActivityTest {

    @Rule
    public ActivityScenarioRule<EditActivity> rule = new ActivityScenarioRule<>(EditActivity.class);

//    private Activity launch;
//    ActivityScenario<EditActivity> test;
//
//    @Before
//    public void setUp() {
//        rule.getScenario();
//        test = rule.getScenario();
//    }

    @Test
    public void onCreate() {
        onView(withId(R.id.et_edit_name)).perform(typeText("Hello world"));
        onView(withId(R.id.et_edit_age)).perform(typeText("25"));
        onView(withId(R.id.et_edit_height)).perform(typeText("6"));
        onView(withId(R.id.et_edit_weight)).perform(typeText("170"));
        onView(withId(R.id.et_edit_country)).perform(typeText("us"));
        onView(withId(R.id.et_edit_city)).perform(typeText("Prove"));
        onView(withId(R.id.iv_user_profile)).check(matches(isDisplayed()));
        onView(withId(R.id.spin_edit_gender)).perform(click());
        onView(withText(endsWith("Others"))).check(matches(isDisplayed()));
    }

    @Test
    public void getUserInfo() {
    }
}