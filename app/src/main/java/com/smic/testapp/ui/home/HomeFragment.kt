package com.smic.testapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smic.testapp.MainActivity
import com.smic.testapp.R
import com.smic.testapp.SharedViewModel

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var button3: Button
    private lateinit var recyclerGithubUsers: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        (requireActivity() as MainActivity).supportActionBar?.show()
        sharedViewModel =
            ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        sharedViewModel.drawerState.value = true

        button3 = root.findViewById(R.id.button3)
        val button4: Button = root.findViewById(R.id.button4)
        recyclerGithubUsers = root.findViewById(R.id.recyclerGithubUsers)
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerGithubUsers.layoutManager = linearLayoutManager


        button3.setOnClickListener {

            homeViewModel.method(recyclerGithubUsers)

        }
        button4.setOnClickListener {

            homeViewModel.nextPage(recyclerGithubUsers)

        }

        return root
    }


}