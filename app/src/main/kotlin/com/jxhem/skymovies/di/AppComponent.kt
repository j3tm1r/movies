package com.jxhem.skymovies.di

import android.app.Application
import com.jxhem.skymovies.app.AppModule
import com.jxhem.skymovies.app.MoviesApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = arrayOf(
        AndroidInjectionModule::class,
        AppModule::class
    )
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }

    fun inject(app: MoviesApp)
}