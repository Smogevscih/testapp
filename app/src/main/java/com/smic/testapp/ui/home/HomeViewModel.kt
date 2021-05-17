package com.smic.testapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.smic.testapp.TestApp.Companion.requestApi
import com.smic.testapp.adapter.UserAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlin.math.ceil
import kotlin.math.roundToInt

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val compositeDisposable = CompositeDisposable()
    var totalPageCount = 0
    private val PER_PAGE = 30
    private var page = 1
    private var maxPage = 1

    fun method(
        recyclerGithubUsers: RecyclerView
    ) {
        compositeDisposable.add(
            requestApi.searchUsers("smog", PER_PAGE, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    totalPageCount = it.totalCount ?: 0
                    it.userItems?.let { it1 ->
                        recyclerGithubUsers.adapter = UserAdapter(it1,isLastPage())

                    }
                    maxPages()
                }, {

                })
        )

    }

    private fun maxPages() {
        maxPage = ceil((totalPageCount.toDouble() / PER_PAGE.toDouble())).roundToInt()
        if (maxPage == 0) maxPage = 1
    }

    fun nextPage(recyclerGithubUsers: RecyclerView) {
        page = page.inc()
        compositeDisposable.add(
            requestApi.searchUsers("smog", PER_PAGE, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.userItems?.let { it1 ->
                        (recyclerGithubUsers.adapter as UserAdapter).addNewUsers(it1,isLastPage())
                    }

                }, {

                })
        )
    }

    fun isLastPage() = page == maxPage
}