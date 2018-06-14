package com.jxhem.skymovies.data.net

import android.content.Context
import com.jxhem.skymovies.data.net.interceptors.ResponseCacheInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetModule {

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: HttpUrl, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideBaseUrl(): HttpUrl {
        return PRODUCTION_API_URL
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(context: Context): OkHttpClient {
        return createApiClient()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            // setup cache .cache(directory, size)
            .cache(provideCacheDirectory(context))
            //add interceptors / authenticators here
            .addNetworkInterceptor(ResponseCacheInterceptor())
            .build()
    }


    @Provides
    @Singleton
    fun provideCacheDirectory(context: Context): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        return Cache(context.cacheDir, cacheSize.toLong())
    }

    private fun createApiClient(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
    }

    companion object {
        val PRODUCTION_API_URL: HttpUrl = HttpUrl.parse(MoviesService.BASE_URL)!!
    }
}