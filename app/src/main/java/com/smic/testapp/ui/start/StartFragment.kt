package com.smic.testapp.ui.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.smic.testapp.MainActivity
import com.smic.testapp.R
import com.smic.testapp.SharedViewModel
import com.smic.testapp.auth.FactoryAuthorization
import com.smic.testapp.ui.IOnBackPressed


class StartFragment : Fragment(), View.OnClickListener, IOnBackPressed {
    private lateinit var btnSignInGoogle: Button
    private lateinit var btnSignInVK: Button
    private lateinit var btnSignInFB: Button
    private lateinit var sharedViewModel: SharedViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_start, container, false)
        (requireActivity() as? MainActivity)?.supportActionBar?.hide()
        btnSignInGoogle = root.findViewById(R.id.btnSignInGoogle)
        btnSignInVK = root.findViewById(R.id.btnSignInVK)
        btnSignInFB = root.findViewById(R.id.btnSignInFB)
        sharedViewModel =
            ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        sharedViewModel.drawerState.value = false


        btnSignInGoogle.setOnClickListener(this)
        btnSignInVK.setOnClickListener(this)
        btnSignInFB.setOnClickListener(this)

        return root
    }

    override fun onClick(v: View?) {
        if (v is Button) {
            FactoryAuthorization.getAuthorization(v.id, requireActivity())
                ?.let { sharedViewModel.authorizationLiveData.value = it }

        }
    }


}