package com.smic.testapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smic.testapp.R
import com.smic.testapp.network.GithubUser
import com.smic.testapp.utils.hide
import com.smic.testapp.utils.show
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class UserAdapter(private val gitHubUsers: List<GithubUser>) :
    RecyclerView.Adapter<UserHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.user_block, parent, false)
        return UserHolder(view)
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bind(gitHubUsers[position])
        holder.itemView.tag = gitHubUsers[position]
    }

    override fun getItemCount(): Int = gitHubUsers.size

}

class UserHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    val userName: TextView = itemView.findViewById(R.id.txtGitUserName)
    val avatar: ImageView = itemView.findViewById(R.id.imgGitUserAvatar)
    val progressAvatar: ProgressBar = itemView.findViewById(R.id.progressAvatar)

    fun bind(githubUser: GithubUser) {

        userName.text = githubUser.login
        progressAvatar.show
        Picasso.get()
            .load(githubUser.avatarUrl)
            .fit()
            .error(R.drawable.ic_not_auth)
            .into(avatar, object : Callback {
                override fun onSuccess() {
                    progressAvatar.hide
                }

                override fun onError(e: Exception?) {
                    progressAvatar.hide
                }

            })
    }


}
