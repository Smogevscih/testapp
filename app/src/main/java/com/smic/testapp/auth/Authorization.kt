package com.smic.testapp.auth

import android.content.Intent
import androidx.lifecycle.MutableLiveData

/**
 *@autor Smogevscih Yuri
15.05.2021
 **/
interface Authorization {
    fun signIn()
    fun getUserLiveData(): MutableLiveData<User>
    fun getResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean
    fun requestUser(data: Intent?)
    fun signOut()
    fun changeAccount()
}

