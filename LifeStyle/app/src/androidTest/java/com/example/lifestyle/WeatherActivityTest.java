package com.example.lifestyle;

import androidx.annotation.ContentView;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class WeatherActivityTest {

    @Rule
    public ActivityScenarioRule<WeatherActivity> activityTestRule = new ActivityScenarioRule<>(WeatherActivity.class);

    @Test
    public void onCreate() {
        onView(withId(R.layout.activity_weather)).perform();

        onView(withId(R.id.tv_current_city)).perform();
//        onView(withId(R.id.tv_current_country)).perform().check(matches(isDisplayed()));
//        onView(withId(R.id.tv_week_day)).perform().check(matches(isDisplayed()));
//        onView(withId(R.id.tv_current_temp)).perform().check(matches(isDisplayed()));
//        onView(withId(R.id.tv_current_weather)).perform().check(matches(isDisplayed()));
//        onView(withId(R.id.humidity_value)).perform().check(matches(isDisplayed()));
//        onView(withId(R.id.wind_value)).perform().check(matches(isDisplayed()));
//        onView(withId(R.id.iv_current_weather)).perform().check(matches(isDisplayed()));
    }

    @Test
    public void getUserInfo() {
    }
}