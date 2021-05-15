package com.smic.testapp.ui.start

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.smic.testapp.R
import com.smic.testapp.auth.GoogleMedia


class StartFragment : Fragment(), View.OnClickListener {
    private lateinit var btnSignInGoogle: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_start, container, false)
        btnSignInGoogle = root.findViewById(R.id.btnSignInGoogle)


        btnSignInGoogle.setOnClickListener(this)
        return root
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnSignInGoogle -> {
                auth()
            }

        }
    }




    fun auth() {

        val auth = GoogleMedia(requireActivity())
        auth.signIn()

    }


}