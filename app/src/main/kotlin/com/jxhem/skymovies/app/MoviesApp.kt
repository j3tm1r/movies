package com.jxhem.skymovies.app

import com.jxhem.skymovies.di.DaggerAppComponent

class MoviesApp : BaseApp() {
    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this)
    }
}