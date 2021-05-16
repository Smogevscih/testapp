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

class GoogleMedia(private val activity: Activity) : SocialMedia() {
    private val mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 777


    init {
        nameSocialMedia = "Google"
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso)
    }

    override fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        activity.startActivityForResult(signInIntent, RC_SIGN_IN)

    }

    override fun getResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean =
        requestCode == RC_SIGN_IN

    override fun requestUser(data: Intent?) {
        val completedTask = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account: GoogleSignInAccount =
                completedTask?.getResult(ApiException::class.java)!!
            val userName = account.displayName ?: ""
            val userEmail = account.email ?: ""
            val userPhotoUrl =
                if (account.photoUrl != null) account.photoUrl.toString() else emptyUser.userPhoto

            user.value = User(userName, userEmail, userPhotoUrl)
        } catch (e: ApiException) {
            user.value = emptyUser
        }
    }

    override fun signOut() {
        mGoogleSignInClient.signOut()
        user.value = emptyUser
    }


}


class FBMedia(private val activity: Activity) : SocialMedia(), Authorization {

    private var callbackManager = CallbackManager.Factory.create()


    override fun signIn() {

        LoginManager.getInstance().logInWithReadPermissions(activity, listOf("email"))

        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    Log.d("MyTag", "onSuccess")
                    loginResult?.let { requestAfterSuccess(it) }
                }

                override fun onCancel() {
                    user.value = emptyUser
                }

                override fun onError(exception: FacebookException) {
                    user.value = emptyUser
                }
            })


    }

    override fun signOut() {
        LoginManager.getInstance().logOut()
        user.value = emptyUser
    }

    override fun requestUser(data: Intent?) {

    }

    override fun getResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        val accessToken = AccessToken.getCurrentAccessToken()
        return accessToken != null && !accessToken.isExpired
    }


    private fun requestAfterSuccess(loginResult: LoginResult) {

        val request = GraphRequest.newMeRequest(
            loginResult.accessToken
        ) { jsonObj, response ->
            try {
                val userName = jsonObj.getString("name")
                val userEmailOrId = jsonObj.getString("email")
                val userPhoto =
                    jsonObj.getJSONObject("picture").getJSONObject("data").getString("url")
                user.value = User(userName, userEmailOrId, userPhoto)
            } catch (e: Exception) {
                user.value = emptyUser
                signOut()
            }

        }

        //Request Graph API
        val parameters = Bundle()
        parameters.putString("fields", "id, name, email, picture.type(large)")
        request.parameters = parameters
        request.executeAsync()
    }

}


