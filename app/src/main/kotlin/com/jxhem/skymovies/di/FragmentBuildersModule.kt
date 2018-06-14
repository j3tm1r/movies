package com.jxhem.skymovies.di


import com.jxhem.skymovies.ui.views.mainview.MainView
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMainView(): MainView
}
