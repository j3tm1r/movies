package com.jxhem.skymovies.di

import com.jxhem.skymovies.ui.MainActivity
import com.jxhem.skymovies.ui.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(
    includes = arrayOf(
        MainActivityModule::class
    )
)
abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector(
        modules = arrayOf(
            FragmentBuildersModule::class,
            MainActivityModule::class
        )
    )
    abstract fun contributeMainActivity(): MainActivity

}