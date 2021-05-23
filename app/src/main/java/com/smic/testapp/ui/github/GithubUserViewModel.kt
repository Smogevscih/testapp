package com.smic.testapp.ui.github

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.smic.testapp.adapter.UserAdapter
import com.smic.testapp.model.Model

class GithubUserViewModel : ViewModel() {

    private val model = Model()

    val adapter: LiveData<UserAdapter?> = model.getAdapter()


    fun firstRequest(query: String) {
        model.firstRequest(query)
    }

    fun nextPage() {
        model.nextPage()
    }

    fun isLastPage() = model.isLastPage()

    fun possibleLoad(): Boolean = model.possibleLoad

    fun getTotalCount(): Int = model.totalCount

    override fun onCleared() {
        super.onCleared()
        model.dispose()
    }
}