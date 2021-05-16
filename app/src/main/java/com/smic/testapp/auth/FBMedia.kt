package com.smic.testapp.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult

/**
 *@autor Smogevscih Yuri
16.05.2021
 **/

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
