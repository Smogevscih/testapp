package com.smic.testapp.auth

import android.app.Activity
import com.smic.testapp.R

object FactoryAuthorization {

    fun getAuthorization(id: Int, activity: Activity): Authorization? {
        return when (id) {
            R.id.btnSignInGoogle -> GoogleMedia(activity)
            R.id.btnSignInVK  -> VKMedia(activity)
            R.id.btnSignInFB  -> FBMedia(activity)
            else -> null
        }
    }
}