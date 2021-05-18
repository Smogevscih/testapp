package com.smic.testapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smic.testapp.MainActivity
import com.smic.testapp.R
import com.smic.testapp.SharedViewModel
import com.smic.testapp.adapter.PaginationScrollListener
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var recyclerGithubUsers: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        (requireActivity() as MainActivity).supportActionBar?.apply {
            title = getString(R.string.fragment_github_name)
        }?.show()
        sharedViewModel =
            ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        sharedViewModel.drawerState.value = true


        val searchView = root.findViewById<SearchView>(R.id.searchView)

        recyclerGithubUsers = root.findViewById(R.id.recyclerGithubUsers)
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerGithubUsers.layoutManager = linearLayoutManager


        //add listener for pagination
        recyclerGithubUsers.addOnScrollListener(object :
            PaginationScrollListener(
                linearLayoutManager
            ) {
            override fun loadMoreItems() {
                homeViewModel.nextPage(recyclerGithubUsers)
            }

            override val totalPageCount: Int
                get() = homeViewModel.totalCount
            override val isLastPage: Boolean
                get() = homeViewModel.isLastPage()
            override val possibleLoad: Boolean
                get() = homeViewModel.possibleLoad

        })



        RxSearchView().fromView(searchView)
            .debounce(600, TimeUnit.MILLISECONDS)
            .filter { it.isNotEmpty() }
            .distinctUntilChanged()
            .switchMap { quest -> Observable.just(quest) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { quest -> homeViewModel.method(quest, recyclerGithubUsers) }


        return root
    }


}

