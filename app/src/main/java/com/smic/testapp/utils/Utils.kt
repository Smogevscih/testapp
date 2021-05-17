package com.smic.testapp.utils

import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 *@autor Smogevscih Yuri
16.05.2021
 **/
//returns the active fragment
val FragmentManager.getCurrentFragment: Fragment?
    get() = this.primaryNavigationFragment?.childFragmentManager?.fragments?.first()

val ProgressBar.hide: ProgressBar
    get() = this.apply {
        visibility = View.INVISIBLE
    }

val ProgressBar.show: ProgressBar
    get() = this.apply {
        visibility = View.VISIBLE
    }