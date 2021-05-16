package com.smic.testapp.auth

import android.app.Activity
import android.content.Intent

/**
 *@autor Smogevscih Yuri
16.05.2021
 **/
class VKMedia(private val activity: Activity):SocialMedia() {
    override fun signIn() {
        TODO("Not yet implemented")
    }

    override fun getResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun requestUser(data: Intent?) {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        TODO("Not yet implemented")
    }
}