package com.smic.testapp.ui.github

import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.smic.testapp.TestApp.Companion.requestApi
import com.smic.testapp.adapter.UserAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlin.math.ceil
import kotlin.math.roundToInt

class GithubUserViewModel : ViewModel() {


    private val compositeDisposable = CompositeDisposable()
    var totalCount = 0
    private val PER_PAGE = 30
    private var page = 1
    private var maxPage = 1
    var possibleLoad = true

    private var currentQuery = ""


    fun firstRequest(
        query: String,
        recyclerGithubUsers: RecyclerView
    ) {
        initValue()
        currentQuery = query
        compositeDisposable.add(
            requestApi.searchUsers(query, PER_PAGE, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    totalCount = it.totalCount ?: 0
                    maxPages()
                    it.userItems?.let { githubUsers ->
                        recyclerGithubUsers.adapter = UserAdapter(githubUsers, isLastPage())
                    }

                }, {

                })
        )

    }

    private fun initValue() {
        page = 1
        maxPage = 1
        possibleLoad = true
    }

    private fun maxPages() {
        maxPage = ceil((totalCount.toDouble() / PER_PAGE.toDouble())).roundToInt()
        if (maxPage == 0) maxPage = 1
        if (maxPage > 10) maxPage = 10 //for test access
    }

    fun nextPage(recyclerGithubUsers: RecyclerView) {
        if (!isLastPage() && possibleLoad) {
            possibleLoad = false
            page = page.inc()
            compositeDisposable.add(
                requestApi.searchUsers(currentQuery, PER_PAGE, page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        it.userItems?.let { githubUsers ->
                            (recyclerGithubUsers.adapter as UserAdapter)
                                .addNewUsers(
                                    githubUsers, isLastPage()
                                )
                            possibleLoad = true
                        }

                    }, {

                    })
            )
        }
    }

    fun isLastPage() = page == maxPage

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}