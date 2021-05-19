package com.smic.testapp

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smic.testapp.auth.Authorization
import com.smic.testapp.auth.User
import com.smic.testapp.auth.emptyUser

/**
 *@autor Smogevscih Yuri
15.05.2021
 **/
class SharedViewModel : ViewModel() {
    private val userLiveData = MutableLiveData<User>().apply {
        value = emptyUser
    }
    val user: LiveData<User> = userLiveData

    val authorizationLiveData = MutableLiveData<Authorization?>().apply {
        apply {
            this.observeForever {
                if (it != null) checkAuth(it)
            }
        }
    }

    private var currentAuthorization: Authorization? = null

    //describes the state of the drawer
    val drawerState = MutableLiveData<Boolean>().apply {
        value = false
    }


    //User must have only one auth
    private fun checkAuth(authorization: Authorization?) {
        if (currentAuthorization == null) {
            currentAuthorization = authorization
            currentAuthorization!!.signIn()
        } else if (authorization != null && currentAuthorization != authorization) {
            currentAuthorization!!.signOut()
            currentAuthorization = authorization
            currentAuthorization!!.signIn()
        } else if (currentAuthorization == authorization) {
            currentAuthorization = authorization
            currentAuthorization!!.signIn()
        }
    }

    fun requestUser(data: Intent?) {
        currentAuthorization?.requestUser(data)

        currentAuthorization?.getUserLiveData()?.observeForever {
            userLiveData.value = it
        }
    }

    fun signOut() {
        currentAuthorization?.signOut()
    }

    fun changeAccount() {
        TODO("Not yet implemented")
    }

}