package com.madison.client.movies.extention.helper.navigation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.madison.client.movies.feature.base.BaseActivity
import javax.inject.Inject

class Navigator @Inject constructor() {
    fun addFragment(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        frameId: Int,
        addToBackStack: Boolean = false,
        tag: String? = null
    ) {
        fragmentManager.beginTransaction().also {
            it.add(frameId, fragment, tag)
            if (addToBackStack) it.addToBackStack(null)
            it.commit()
        }
    }

    fun popFragment(activity: Activity) {
        if (activity is BaseActivity) activity.supportFragmentManager.popBackStack()
    }

    fun startActivity(
        activity: Activity, clazz: Class<out Activity>, bundle: Bundle? = null
    ) {
        val intent = Intent(activity, clazz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }

        activity.startActivity(intent)
    }
}