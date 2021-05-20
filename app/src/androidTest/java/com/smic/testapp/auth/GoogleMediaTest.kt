package com.smic.testapp.auth

import android.content.Intent
import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.google.common.truth.Truth
import com.smic.testapp.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * @autor Smogevscih Yuri
 * 19.05.2021
 */
class GoogleMediaTest {
    private val RC_SIGN_IN = 777
    @get:Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var googleMedia: GoogleMedia


    @Before
    fun setUp() {
        rule.scenario.onActivity { activity ->
            googleMedia = GoogleMedia(activity)

        }
    }

    @Test
    fun signIn() {

    }

    @Test
    fun getResult() {
        var result = googleMedia.getResult(RC_SIGN_IN, 0, null)
        Truth.assertThat(result).isTrue()
        result = googleMedia.getResult(RC_SIGN_IN - 1, 0, null)
        Truth.assertThat(result).isFalse()
    }

    @Test
    @UiThreadTest
    fun requestUser() {
        val intent = Intent()
        googleMedia.requestUser(intent)
        val user = googleMedia.getUserLiveData().value
        Truth.assertThat(user).isInstanceOf(User::class.java)
        Truth.assertThat(user).isEqualTo(emptyUser)
    }

    @Test
    @UiThreadTest
    fun signOut() {
        googleMedia.signOut()
        val user = googleMedia.getUserLiveData().value
        Truth.assertThat(user).isInstanceOf(User::class.java)
        Truth.assertThat(user).isEqualTo(emptyUser)

    }
}