package com.jxhem.skymovies.ui

import android.os.Bundle
import com.jxhem.skymovies.R
import com.jxhem.skymovies.ui.navigation.NavigationProvider
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    internal lateinit var mNavigationController: NavigationProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        if (savedInstanceState == null)
            mNavigationController.navigateToMainView()
    }
}