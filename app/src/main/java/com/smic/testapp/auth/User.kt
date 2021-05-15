package com.smic.testapp.auth

/**
 *@autor Smogevscih Yuri
15.05.2021
 **/
data class User(val userName: String, val userEmailOrId: String, val userPhoto: String)

val emptyUser = User("Not authorized","", "http://")