package com.smic.testapp.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 *@autor Smogevscih Yuri
16.05.2021
 **/
//returns the active fragment
val FragmentManager.getCurrentFragment: Fragment?
    get() = this.primaryNavigationFragment?.childFragmentManager?.fragments?.first()