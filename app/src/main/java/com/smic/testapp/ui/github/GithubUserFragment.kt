package com.smic.testapp.ui.github

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
import com.smic.testapp.adapter.UserAdapter
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class GithubUserFragment : Fragment() {

    private lateinit var githubUserViewModel: GithubUserViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var recyclerGithubUsers: RecyclerView
    private lateinit var disposeSearchView: Disposable


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_github_user, container, false)
        githubUserViewModel =
            ViewModelProvider(this).get(GithubUserViewModel::class.java)
        (requireActivity() as? MainActivity)?.supportActionBar?.apply {
            title = getString(R.string.fragment_github_name)
        }?.show()
        sharedViewModel =
            ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        sharedViewModel.drawerState.value = true


        val searchView = root.findViewById<SearchView>(R.id.searchView)

        recyclerGithubUsers = root.findViewById(R.id.recyclerGithubUsers)
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerGithubUsers.layoutManager = linearLayoutManager

        githubUserViewModel.adapter.observe(viewLifecycleOwner, {
            it?.let {
                initRecycler(it)
            }
        })


        //add listener for pagination
        recyclerGithubUsers.addOnScrollListener(object :
            PaginationScrollListener(
                linearLayoutManager
            ) {
            override fun loadMoreItems() {
                githubUserViewModel.nextPage()
            }

            override val totalPageCount: Int
                get() = githubUserViewModel.totalCount
            override val isLastPage: Boolean
                get() = githubUserViewModel.isLastPage()
            override val possibleLoad: Boolean
                get() = githubUserViewModel.possibleLoad

        })


        disposeSearchView = RxSearchView().fromView(searchView)
            .debounce(600, TimeUnit.MILLISECONDS)
            .filter { it.isNotEmpty() }
            .distinctUntilChanged()
            .switchMap { quest -> Observable.just(quest) }
            .subscribe { quest -> githubUserViewModel.firstRequest(quest) }


        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        githubUserViewModel.adapter.removeObservers(viewLifecycleOwner)
        disposeSearchView.dispose()
    }

    private fun initRecycler(userAdapter: UserAdapter) {
        recyclerGithubUsers.adapter = userAdapter

    }
}

