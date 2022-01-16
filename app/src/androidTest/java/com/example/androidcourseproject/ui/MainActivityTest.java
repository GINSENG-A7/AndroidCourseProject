package com.example.androidcourseproject.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

import android.widget.CalendarView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.example.androidcourseproject.R;

import org.junit.Rule;
import org.junit.Test;

public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void invalidEditClient() {
        RecyclerView rvClients = mActivityTestRule.getActivity().findViewById(R.id.clientsList);
        onView(withId(R.id.clientsList)).check(matches(isDisplayed()));
        onView(withId(R.id.editClientButton)).perform(click());
        onView(withId(R.id.clientsList)).check(matches(isDisplayed()));
    }
    @Test
    public void invalidDeleteClient() {
        RecyclerView rvClients = mActivityTestRule.getActivity().findViewById(R.id.clientsList);
        onView(withId(R.id.clientsList)).check(matches(isDisplayed()));
        onView(withId(R.id.deleteClientButton)).perform(click());
        onView(withId(R.id.clientsList)).check(matches(isDisplayed()));
    }
    @Test
    public void RelationValues() {
        RecyclerView rvClients = mActivityTestRule.getActivity().findViewById(R.id.clientsList);

        onView(withId(R.id.clientsList)).check(matches(isDisplayed()));
        onView(withId(R.id.clientsList)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
//        rvClients.getAdapter().getSelected();
        onView(withId(R.id.registerNewClientButton)).perform(click());
        onView(withId(R.id.etPassportSeries)).check(ViewAssertions.matches(not(ViewMatchers.withText(""))));
        onView(withId(R.id.etPassportNumber)).check(ViewAssertions.matches(not(ViewMatchers.withText(""))));
        onView(withId(R.id.etName)).check(ViewAssertions.matches(not(ViewMatchers.withText(""))));
        onView(withId(R.id.etSurname)).check(ViewAssertions.matches(not(ViewMatchers.withText(""))));
        onView(withId(R.id.etPatronymic)).check(ViewAssertions.matches(not(ViewMatchers.withText(""))));
        onView(withId(R.id.dateTextView)).check(ViewAssertions.matches(not(ViewMatchers.withText(R.string.newClient_noDate_dateTextView))));
//        onView(withId(R.id.clientsList)).check(matches(isNotClickable()));
    }
}