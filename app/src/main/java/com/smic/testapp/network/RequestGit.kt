package com.smic.testapp.network

import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RequestGit {

    @GET("search/users")
    fun searchUsers(
        @Query("q", encoded = true) query: String,
        @Query("per_page", encoded = true) per_page: Int,
        @Query("page", encoded = true) page: Int
    ):
            Single<SearchUserResponse>

}

data class SearchUserResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean?,
    @SerializedName("items")
    val userItems: ArrayList<GithubUser>?,
    @SerializedName("total_count")
    val totalCount: Int?
)

data class GithubUser(

    @SerializedName("login")
    var login: String,
    @SerializedName("avatar_url")
    var avatarUrl: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("bio")
    var bio: String,
    @SerializedName("public_repos")
    var publicRepos: Int,
    @SerializedName("followers")
    var followers: Int,
    @SerializedName("following")
    var following: Int
)




