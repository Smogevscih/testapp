package com.smic.testapp.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.smic.testapp.TestApp
import com.smic.testapp.adapter.UserAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlin.math.ceil
import kotlin.math.roundToInt

/**
 *@autor Smogevscih Yuri
23.05.2021
 **/
class Model {
    private val compositeDisposable = CompositeDisposable()
    var totalCount = 0
    private val PER_PAGE = 30
    private var page = 1
    private var maxPage = 1
    var possibleLoad = true
    private var currentQuery = ""
    private val adapterLiveData = MutableLiveData<UserAdapter?>().apply {
        value = null
    }

    fun getAdapter(): LiveData<UserAdapter?> =
        adapterLiveData


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

    fun isLastPage() = page == maxPage
    fun dispose() {

        compositeDisposable.dispose()
    }

    fun nextPage() {
        if (!isLastPage() && possibleLoad) {
            possibleLoad = false
            page = page.inc()
            compositeDisposable.add(
                TestApp.requestApi.searchUsers(currentQuery, PER_PAGE, page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        it.userItems?.let { githubUsers ->
                            adapterLiveData.value?.addNewUsers(githubUsers, isLastPage())
                            possibleLoad = true
                        }

                    }, {

                    })
            )
        }
    }

    fun firstRequest(query: String) {
        initValue()
        currentQuery = query
        compositeDisposable.add(
            TestApp.requestApi.searchUsers(query, PER_PAGE, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    totalCount = it.totalCount ?: 0
                    maxPages()
                    it.userItems?.let { githubUsers ->
                        adapterLiveData.value = UserAdapter(githubUsers, isLastPage())
                    }

                }, {

                })
        )

    }
}