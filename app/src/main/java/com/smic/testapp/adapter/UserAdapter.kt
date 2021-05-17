package com.smic.testapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smic.testapp.R
import com.smic.testapp.network.GithubUser
import com.smic.testapp.utils.hide
import com.smic.testapp.utils.show
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


class UserAdapter(private val gitHubUsers: ArrayList<GithubUser>, var isLastPage: Boolean) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val USER_HOLD = 100
    private val PROGRESS_HOLD = 200
    private val PER_PAGE = 30


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

    }

    override fun getItemCount() = if (isLastPage) gitHubUsers.size else gitHubUsers.size + 1


    override fun getItemViewType(position: Int) =
        if (position == gitHubUsers.size) PROGRESS_HOLD
        else USER_HOLD

    fun addNewUsers(newGitHubUsers: ArrayList<GithubUser>, _isLastPage: Boolean) {
        isLastPage = _isLastPage
        val positionStart = gitHubUsers.size
        gitHubUsers.addAll(newGitHubUsers)

        notifyItemInserted(positionStart)
        Log.e("MyTag", gitHubUsers.size.toString())
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


abstract class PaginationScrollListener(var layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        if (!isLastPage) {
            if (visibleItemCount + firstVisibleItemPosition >=
                totalItemCount && firstVisibleItemPosition >= 0
            ) {
                loadMoreItems()
            }
        }
    }

    protected abstract fun loadMoreItems()
    abstract val totalPageCount: Int
    abstract val isLastPage: Boolean


}