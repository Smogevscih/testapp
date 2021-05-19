package com.smic.testapp

import android.content.Intent
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.smic.testapp.auth.SocialMedia
import com.smic.testapp.auth.emptyUser
import com.smic.testapp.auth.testUser
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SharedViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: SharedViewModel
    private lateinit var testAuth: SocialMedia

    @Before
    fun setUp() {
        viewModel = SharedViewModel()

        testAuth = object : SocialMedia() {
            override fun signIn() {
                user.value = testUser
            }

            override fun getResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
                return true
            }

            override fun requestUser(data: Intent?) {
                user.value = testUser
            }

            override fun signOut() {
                user.value = emptyUser
            }

        }
    }


    @Test
    fun after_init_viewModel_LiveData_user_is_emptyUser() {
        val result = viewModel.user.value
        Truth.assertThat(result).isEqualTo(emptyUser)
    }

    @Test
    fun after_init_drawerStat_is_false() {
        val result = viewModel.drawerState.value
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun after_init_authorizationLiveData_is_null() {
        val result = viewModel.authorizationLiveData.value
        Truth.assertThat(result).isNull()
    }

    @Test
    fun test_authorization() {
        viewModel.authorizationLiveData.value = testAuth
        viewModel.requestUser(Intent())
        val result = viewModel.user.value
        Truth.assertThat(result).isEqualTo(testUser)
    }

    @Test
    fun test_signOut() {
        //first step auth
        viewModel.authorizationLiveData.value = testAuth
        viewModel.requestUser(Intent())
        var result = viewModel.user.value
        Truth.assertThat(result).isEqualTo(testUser)
        //second step sign out
        viewModel.signOut()
        result = viewModel.user.value
        Truth.assertThat(result).isEqualTo(emptyUser)
    }
}