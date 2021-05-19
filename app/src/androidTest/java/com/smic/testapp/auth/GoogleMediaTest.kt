package com.smic.testapp.auth

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.toPackage
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.google.common.truth.Truth
import com.smic.testapp.MainActivity
import com.smic.testapp.R
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * @autor Smogevscih Yuri
 * 19.05.2021
 */
class GoogleMediaTest {
    @get:Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)

    lateinit var googleMedia: GoogleMedia

    @Before
    fun setUp() {
        rule.scenario.onActivity { activity ->
            googleMedia = GoogleMedia(activity)
        }
    }

    @Test
    fun signIn() {
        var result = Instrumentation.ActivityResult(
            Activity.RESULT_OK, Intent()
        )

        intending(hasAction(Intent.ACTION_PICK)).respondWith(result)

        onView(withId(R.id.btnSignInGoogle)).perform(click())
        intended(allOf(
            toPackage("com.google.android.gms.auth"),
            hasAction(Intent.ACTION_PICK)))
    }

    @Test
    fun getResult() {
    }

    @Test
    fun requestUser() {
    }

    @Test
    fun signOut() {
        rule.scenario.onActivity {
            googleMedia.signOut()
            val user = googleMedia.getUserLiveData().value
            Truth.assertThat(user).isInstanceOf(User::class.java)
            Truth.assertThat(user).isEqualTo(emptyUser)
        }
    }
}