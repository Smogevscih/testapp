package com.smic.testapp.ui.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.smic.testapp.R




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

            }

        }
    }


}