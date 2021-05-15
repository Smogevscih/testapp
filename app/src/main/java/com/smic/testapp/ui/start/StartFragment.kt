package com.smic.testapp.ui.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.smic.testapp.R
import com.smic.testapp.SharedViewModel
import com.smic.testapp.auth.GoogleMedia


class StartFragment : Fragment(), View.OnClickListener {
    private lateinit var btnSignInGoogle: Button
    private lateinit var btnSignOutGoogle: Button
    private lateinit var sharedViewModel: SharedViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_start, container, false)
        btnSignInGoogle = root.findViewById(R.id.btnSignInGoogle)
        btnSignOutGoogle = root.findViewById(R.id.btnSignOutGoogle)
        sharedViewModel =
            ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        btnSignInGoogle.setOnClickListener(this)
        btnSignOutGoogle.setOnClickListener(this)
        return root
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnSignInGoogle -> {
                sharedViewModel.authorizationLiveData.value = GoogleMedia(requireActivity())
            }
            R.id.btnSignOutGoogle -> {
                sharedViewModel.signOut()
            }

        }
    }


}