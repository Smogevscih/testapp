package com.smic.testapp

import androidx.test.rule.ActivityTestRule
import com.google.common.truth.Truth
import com.smic.testapp.network.RequestGit
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TestInitRetrofit {

    @get:Rule
    val rule = ActivityTestRule(MainActivity::class.java)

    private lateinit var activity: MainActivity


    @Before
    fun setUp() {
        activity = rule.activity

    }

    @Test
    fun get_initialized_requestApi() {
        val result = TestApp.requestApi
        Truth.assertThat(result).isInstanceOf(RequestGit::class.java)

    }

}