package com.smic.testapp.auth

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