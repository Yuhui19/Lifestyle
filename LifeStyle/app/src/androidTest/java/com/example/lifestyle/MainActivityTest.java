package com.example.lifestyle;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> rule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void onCreate() {
        onView(withId(R.id.tv_username)).perform().check(matches(isDisplayed()));
        onView(withId(R.id.tv_gender)).perform().check(matches(isDisplayed()));
        onView(withId(R.id.tv_age)).perform().check(matches(isDisplayed()));
        onView(withId(R.id.tv_height)).perform().check(matches(isDisplayed()));
        onView(withId(R.id.tv_weight)).perform().check(matches(isDisplayed()));
        onView(withId(R.id.tv_country)).perform().check(matches(isDisplayed()));
        onView(withId(R.id.tv_city)).perform().check(matches(isDisplayed()));

        onView(withId(R.id.ib_weather)).perform(click());
        Espresso.pressBack();
        onView(withId(R.id.ib_bmi)).perform(click());
        Espresso.pressBack();
        onView(withId(R.id.ib_goal)).perform(click());
        Espresso.pressBack();
//        onView(withId(R.id.ib_map)).perform(click());
//        Espresso.pressBack();
    }

    @Test
    public void getUserInfo() {
    }
}