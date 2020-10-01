package com.example.lifestyle;

import android.app.Activity;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.assertion.ViewAssertions.*;

@RunWith(AndroidJUnit4.class)
public class BMIActivityTest {

    @Rule
    public ActivityScenarioRule<BMIActivity> activityTestRule = new ActivityScenarioRule<BMIActivity>(BMIActivity.class);

    @Test
    public void onCreate() {
        onView(withId(R.layout.activity_bmi)).perform();
        onView(withId(R.id.tv_BMI_value)).perform();
    }

    @Test
    public void getUserInfo() {
    }
}