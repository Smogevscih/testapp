package com.smic.testapp.auth

import android.app.Activity
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

/**
 *@autor Smogevscih Yuri
15.05.2021
 **/

open class SocialMedia() : Authorization {
    protected var nameSocialMedia = "SocialMedia"
    override fun signIn() {

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
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        activity.startActivityForResult(signInIntent, RC_SIGN_IN)

    }
}

