package com.smic.testapp.ui.github

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GithubUserViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    lateinit var githubUserViewModel: GithubUserViewModel

    @Before
    fun setUp() {
        githubUserViewModel = GithubUserViewModel()
    }

    @Test
    fun test_first_launch_adapter_value_is_null() {
        val result = githubUserViewModel.adapter.value
        Truth.assertThat(result).isNull()
    }

    @Test
    fun test_adapter_is_immutable() {
        val result = githubUserViewModel.adapter
        Truth.assertThat(result).isInstanceOf(LiveData::class.java)
    }

    @Test
    fun test_first_launch_totalcount_possibleLoad() {
        val result1 = githubUserViewModel.totalCount
        Truth.assertThat(result1).isEqualTo(0)
        val result2 = githubUserViewModel.possibleLoad
        Truth.assertThat(result2).isTrue()
    }


}