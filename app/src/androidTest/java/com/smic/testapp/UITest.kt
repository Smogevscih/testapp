package com.smic.testapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.google.common.truth.Truth
import com.smic.testapp.ui.start.StartFragment
import com.smic.testapp.utils.getCurrentFragment
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class UITest {
    @get:Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)

    lateinit var activity: MainActivity


    @Before
    fun setUp() {
        rule.scenario.onActivity { _activity ->
            activity = _activity
        }
    }

    @Test//We have to see 3 enabled buttons and the app name from StartFragment
    fun startScreen() {
        val fragment = activity.supportFragmentManager.getCurrentFragment
        Truth.assertThat(fragment).isInstanceOf(StartFragment::class.java)

        onView(withId(R.id.btnSignInGoogle))
            .check(matches(isDisplayed()))
            .check(matches(isEnabled()))

        onView(withId(R.id.btnSignInFB))
            .check(matches(isDisplayed()))
            .check(matches(isEnabled()))

        onView(withId(R.id.btnSignInVK))
            .check(matches(isDisplayed()))
            .check(matches(isEnabled()))

        onView(withId(R.id.txtNameApp))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.app_Name)))

    }

    @Test//We have to see 3 enabled buttons and the app name from StartFragment
    fun githubScreen() {
        val fragment = activity.supportFragmentManager.getCurrentFragment
        Truth.assertThat(fragment).isInstanceOf(StartFragment::class.java)

        onView(withId(R.id.btnSignInGoogle))
            .check(matches(isDisplayed()))
            .check(matches(isEnabled()))

        onView(withId(R.id.btnSignInFB))
            .check(matches(isDisplayed()))
            .check(matches(isEnabled()))

        onView(withId(R.id.btnSignInVK))
            .check(matches(isDisplayed()))
            .check(matches(isEnabled()))

        onView(withId(R.id.txtNameApp))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.app_Name)))

    }


}