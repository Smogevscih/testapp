package com.smic.testapp

import androidx.fragment.app.testing.FragmentScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.smic.testapp.ui.github.GithubUserFragment
import com.smic.testapp.ui.start.StartFragment
import org.junit.Test


class IsolationTestFragment {
    @Test//We have to see 3 enabled buttons and the app name from StartFragment
    fun startScreen() {

        FragmentScenario.launchInContainer(StartFragment::class.java)

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

    @Test
    fun githubScreen() {
        FragmentScenario.launchInContainer(GithubUserFragment::class.java)

        onView(withId(R.id.searchView))
            .check(matches(isDisplayed()))
        onView(withId(R.id.recyclerGithubUsers))
            .check(matches(isDisplayed()))

    }
}