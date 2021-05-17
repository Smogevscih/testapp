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
import com.smic.testapp.network.emptyGithubUser
import com.smic.testapp.utils.hide
import com.smic.testapp.utils.show
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class UserAdapter(private val gitHubUsers: ArrayList<GithubUser>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val USER_HOLD = 100
    private val PROGRESS_HOLD = 200

    init {
        gitHubUsers.add(emptyGithubUser)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        return when (viewType) {
            USER_HOLD -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.user_block, parent, false)
                UserHolder(view)
            }
            else -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.progress_block, parent, false)
                ProgressHolder(view)
            }
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserHolder -> holder.bind(gitHubUsers[position])
            is ProgressHolder -> holder.bind()
        }

        holder.itemView.tag = gitHubUsers[position]
    }

    override fun getItemCount(): Int = gitHubUsers.size

    override fun getItemViewType(position: Int) =
        if (gitHubUsers[position] == emptyGithubUser) PROGRESS_HOLD
        else USER_HOLD

    fun addNewUsers(newGitHubUsers: ArrayList<GithubUser>) {
        gitHubUsers.removeLast()
        gitHubUsers.addAll(newGitHubUsers)
        gitHubUsers.add(emptyGithubUser)
        notifyItemRangeInserted(gitHubUsers.size - 31, 30)
    }

}

class UserHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    private val userName: TextView = itemView.findViewById(R.id.txtGitUserName)
    private val avatar: ImageView = itemView.findViewById(R.id.imgGitUserAvatar)
    private val progressAvatar: ProgressBar = itemView.findViewById(R.id.progressAvatar)

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

class ProgressHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    private val progressRequest: ProgressBar = itemView.findViewById(R.id.progressRequest)

    fun bind() {
        progressRequest.show
    }

}
