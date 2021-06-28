package com.vkochenkov.lifesimulation

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vkochenkov.lifesimulation.presentation.activity.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AndroidTests {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun isStoppedAfterClearBtnClick() {
        //when
        onView(withId(R.id.btn_clear)).perform(click())
        //then
        onView(withId(R.id.switch_start)).check(matches(isNotChecked()))
        onView(withId(R.id.tv_dinamic_generation)).check(matches(withText("0")))
    }
}
