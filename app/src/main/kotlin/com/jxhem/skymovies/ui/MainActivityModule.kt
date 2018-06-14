package com.jxhem.skymovies.ui

import com.jxhem.skymovies.ui.navigation.NavigationController
import com.jxhem.skymovies.ui.navigation.NavigationProvider
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun provideNavigationProvider(activity: MainActivity): NavigationProvider =
        NavigationController(activity)
}
