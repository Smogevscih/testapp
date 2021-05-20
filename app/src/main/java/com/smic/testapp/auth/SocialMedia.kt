package com.smic.testapp.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

/**
 *@autor Smogevscih Yuri
15.05.2021
 **/

abstract class SocialMedia() : Authorization {
    protected var nameSocialMedia = "SocialMedia"
    protected var user = MutableLiveData<User>().apply {
        value = emptyUser
    }

    override fun getUserLiveData(): MutableLiveData<User> = user

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SocialMedia

        if (nameSocialMedia != other.nameSocialMedia) return false

        return true
    }

    override fun hashCode(): Int {
        return nameSocialMedia.hashCode()
    }

}




