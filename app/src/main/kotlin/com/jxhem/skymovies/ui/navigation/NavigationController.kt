package com.jxhem.skymovies.ui.navigation

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.jxhem.skymovies.R
import com.jxhem.skymovies.ui.BaseActivity
import com.jxhem.skymovies.ui.views.mainview.MainView


class NavigationController(baseActivity: BaseActivity) : NavigationProvider {
    private val containerId: Int = R.id.container
    private val fragmentManager: FragmentManager = baseActivity.supportFragmentManager


    override fun navigateToMainView() {
        var mainView = findFragment(MainView::class.java.simpleName) ?: MainView.create()

        fragmentManager
            .beginTransaction()
            .replace(containerId, mainView, MainView::class.java.simpleName)
            .setReorderingAllowed(true)
            .commitAllowingStateLoss()
    }

    private fun removeFragment(id: String) {
        var fragment = fragmentManager.findFragmentByTag(id)
        if (fragment != null) fragmentManager.beginTransaction().remove(fragment).commit()
    }

    private fun findFragment(id: String): Fragment? {
        return fragmentManager.findFragmentByTag(id)
    }

}