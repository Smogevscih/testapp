package com.smic.testapp.auth

import android.app.Activity
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

/**
 *@autor Smogevscih Yuri
16.05.2021
 **/

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