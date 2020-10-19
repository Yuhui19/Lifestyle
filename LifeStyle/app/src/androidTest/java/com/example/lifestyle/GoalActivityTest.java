package com.example.lifestyle;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class GoalActivityTest {

    @Rule
    public ActivityScenarioRule<GoalActivity> rule = new ActivityScenarioRule<>(GoalActivity.class);

//    GoalActivity test = new GoalActivity();

    @Test
    public void onCreate() {
        onView(withId(R.id.goal_gain_weight)).perform();
        onView(withId(R.id.goal_lose_weight)).perform(click());
        onView(withId(R.id.goal_maintain_weight)).perform(click());
        onView(withId(R.id.status_active)).perform(click());
        onView(withId(R.id.status_sedentary)).perform(click());
        onView(withId(R.id.et_input_goal_weight)).perform(typeText("2.0"), closeSoftKeyboard());
        onView(withId(R.id.goal_lose_weight)).perform(click());
        onView(withId(R.id.et_input_current_height)).perform(typeText("170"), closeSoftKeyboard());
        onView(withId(R.id.et_input_current_weight)).perform(typeText("6"));
        ViewActions.closeSoftKeyboard();
    }

    @Test
    public void getUserInfo() {
    }
}