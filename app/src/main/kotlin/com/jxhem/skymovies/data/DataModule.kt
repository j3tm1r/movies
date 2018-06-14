package com.jxhem.skymovies.data


import android.content.Context
import android.content.SharedPreferences
import com.jxhem.skymovies.data.net.MoviesService
import com.jxhem.skymovies.data.net.NetModule
import com.jxhem.skymovies.data.repository.DataRepository
import com.jxhem.skymovies.data.source.DataSource
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(
    includes = [(NetModule::class)]
)
class DataModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("skymovies", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideDataSource(retrofit: Retrofit): DataSource {
        return DataRepository(retrofit.create(MoviesService::class.java))
    }
}