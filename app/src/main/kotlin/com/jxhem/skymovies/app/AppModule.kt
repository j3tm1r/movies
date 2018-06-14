package com.jxhem.skymovies.app

import android.app.Application
import android.content.Context
import android.support.annotation.NonNull
import com.jxhem.skymovies.data.DataModule
import com.jxhem.skymovies.di.ActivityBuilder
import com.jxhem.skymovies.viewmodel.ViewModelModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(
    includes = arrayOf(
        ActivityBuilder::class,
        ViewModelModule::class,
        DataModule::class
    )
)
class AppModule {


    @Singleton
    @Provides
    @NonNull
    fun provideContext(application: Application): Context {
        return application
    }
}

