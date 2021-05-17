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

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val compositeDisposable = CompositeDisposable()
    private var totalCount = 0
    private val PER_PAGE = 30
    private var page = 1


    fun method(
        recyclerGithubUsers: RecyclerView
    ) {
        compositeDisposable.add(
            requestApi.searchUsers("smog", PER_PAGE, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    totalCount = it.totalCount ?: 0
                    it.userItems?.let { it1 ->
                        recyclerGithubUsers.adapter = UserAdapter(it1)
                    }

                }, {

                })
        )

    }

    fun nextPage(recyclerGithubUsers: RecyclerView) {

        compositeDisposable.add(
            requestApi.searchUsers("smog", PER_PAGE, page.inc())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    totalCount = it.totalCount ?: 0
                    it.userItems?.let { it1 ->
                        (recyclerGithubUsers.adapter as UserAdapter).addNewUsers(it1)
                    }

                }, {

                })
        )
    }
}