package com.smic.testapp.ui.github

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.google.common.truth.Truth
import com.smic.testapp.model.Model
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    lateinit var model: Model

    @Before
    fun setUp() {
        model = Model()
    }

    @Test
    fun test_first_launch_adapter_value_is_null() {
        val result = model.getAdapter().value
        Truth.assertThat(result).isNull()
    }

    @Test
    fun test_adapter_is_immutable() {
        val result = model.getAdapter()
        Truth.assertThat(result).isInstanceOf(LiveData::class.java)
    }

    @Test
    fun test_first_launch_totalcount_possibleLoad() {
        val result1 = model.totalCount
        Truth.assertThat(result1).isEqualTo(0)
        val result2 = model.possibleLoad
        Truth.assertThat(result2).isTrue()
    }


}