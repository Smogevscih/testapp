package com.smic.testapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.smic.testapp.adapter.UserAdapter
import com.smic.testapp.network.RequestGit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val compositeDisposable = CompositeDisposable()

    fun method(
        requestApi: RequestGit,
        recyclerGithubUsers: RecyclerView
    ) {
        compositeDisposable.add(
            requestApi.searchUsers("smog", 30, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    val adapter = UserAdapter(it.userItems!!)
                    recyclerGithubUsers.adapter = adapter

                }, {

                })
        )

    }
}