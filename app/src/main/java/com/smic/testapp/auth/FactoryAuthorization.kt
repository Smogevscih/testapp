package com.smic.testapp.auth

import android.app.Activity

object FactoryAuthorization {

    fun getAuthorization(name: String, activity: Activity): Authorization? {
        return when (name) {
            "Connect with Google" -> GoogleMedia(activity)
            "Connect with VK" -> VKMedia(activity)
            "Connect with Facebook" -> FBMedia(activity)
            else -> null
        }
    }
}