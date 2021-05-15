package com.smic.testapp.auth

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
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

}

class GoogleMedia(private val activity: Activity) : SocialMedia() {
    private val mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 777
    private lateinit var signInIntent:Intent

    init {
        nameSocialMedia = "Google"
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso)
    }

    override fun signIn() {
         signInIntent = mGoogleSignInClient.signInIntent
        activity.startActivityForResult(signInIntent, RC_SIGN_IN)

    }

    override fun getResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean =
        requestCode == RC_SIGN_IN

    override fun requestUser(data: Intent?) {
        val completedTask = GoogleSignIn.getSignedInAccountFromIntent(signInIntent)
        try {
            val account: GoogleSignInAccount =
                completedTask?.getResult(ApiException::class.java)!!

            user.value = User(account.displayName!!, account.email!!, account.photoUrl!!.toString())
        } catch (e: ApiException) {
            Log.w("MyTAG", "signInResult:failed code=" + e.statusCode)
            user.value = emptyUser
        }
    }

    override fun signOut() {
        mGoogleSignInClient.signOut()
    }


}
