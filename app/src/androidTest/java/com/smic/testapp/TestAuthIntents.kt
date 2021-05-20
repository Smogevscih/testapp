package com.smic.testapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.toPackage
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Rule
import org.junit.Test


class TestAuthIntents {
    @get:Rule
    val rule = IntentsTestRule(MainActivity::class.java)


    @Test
    fun startGoogleSignIn() {

        onView(withId(R.id.btnSignInGoogle))
            .perform(click())

        intended(toPackage("com.google.android.gms"))

    }

    @Test
    fun startVKSignIn() {

        onView(withId(R.id.btnSignInVK))
            .perform(click())

        intended(hasComponent("com.vk.api.sdk.ui.VKWebViewAuthActivity"))

    }

    @Test//Sometimes it can hung. Please launch alone
    fun startFBSignIn() {

        onView(withId(R.id.btnSignInFB))
            .perform(click())

        intended(hasComponent("com.facebook.FacebookActivity"))

    }



}