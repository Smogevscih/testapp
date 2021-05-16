package com.smic.testapp.auth

import android.app.Activity
import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import com.vk.api.sdk.exceptions.VKApiExecutionException
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

/**
 *@autor Smogevscih Yuri
16.05.2021
 **/
class VKMedia(private val activity: Activity):SocialMedia() {
    init {
        nameSocialMedia = "VK"
    }
    private val callback = object : VKAuthCallback {
        override fun onLogin(token: VKAccessToken) {

            VK.execute(VKUsersRequest(), object : VKApiCallback<List<VKUser>> {
                override fun success(result: List<VKUser>) {

                    val vkUser = result[0]
                    user.value = User(
                        "${vkUser.firstName} ${vkUser.lastName}",
                        "ID: ${vkUser.id}",
                        vkUser.photo
                    )
                }

                override fun fail(error: VKApiExecutionException) {
                    user.value = emptyUser
                }
            })
        }
        override fun onLoginFailed(errorCode: Int) {
            user.value = emptyUser
        }
    }

    override fun signIn() {
        VK.login(activity, arrayListOf(VKScope.WALL, VKScope.PHOTOS))
    }

    override fun signOut() {
        VK.logout()
        user.value = emptyUser
    }

    override fun requestUser(data: Intent?) {

    }


    override fun getResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean =
        VK.onActivityResult(requestCode, resultCode, data, callback)


}


class VKUsersRequest : VKRequest<List<VKUser>> {
    constructor(uids: IntArray = intArrayOf()) : super("users.get") {
        if (uids.isNotEmpty()) {
            addParam("user_ids", uids.joinToString(","))
        }
        addParam("fields", "photo_200")
    }

    override fun parse(r: JSONObject): List<VKUser> {
        val users = r.getJSONArray("response")
        val result = ArrayList<VKUser>()
        for (i in 0 until users.length()) {
            result.add(VKUser.parse(users.getJSONObject(i)))
        }
        return result
    }
}

data class VKUser(
    val id: Int = 0,
    val firstName: String = "",
    val lastName: String = "",
    val photo: String = "",
    val deactivated: Boolean = false
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(photo)
        parcel.writeByte(if (deactivated) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VKUser> {
        override fun createFromParcel(parcel: Parcel): VKUser {
            return VKUser(parcel)
        }

        override fun newArray(size: Int): Array<VKUser?> {
            return arrayOfNulls(size)
        }

        fun parse(json: JSONObject) = VKUser(
            id = json.optInt("id", 0),
            firstName = json.optString("first_name", ""),
            lastName = json.optString("last_name", ""),
            photo = json.optString("photo_200", ""),
            deactivated = json.optBoolean("deactivated", false)
        )
    }
}