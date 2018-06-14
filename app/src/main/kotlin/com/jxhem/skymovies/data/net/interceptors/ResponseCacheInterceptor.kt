package com.jxhem.skymovies.data.net.interceptors

import com.jxhem.skymovies.data.net.interceptors.ResponseCacheInterceptor.Companion.CACHE_TIME
import okhttp3.Interceptor
import java.io.IOException

/**
 * An OkHttp response interceptor responsible for caching the results for [CACHE_TIME] minutes
 */
class ResponseCacheInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val originalResponse = chain.proceed(chain.request())
        return originalResponse.newBuilder()
            .header(HTTP_HEADER_NAME, HTTP_HEADER_VALUE)
            .build()
    }

    companion object {
        const val CACHE_TIME: Int = 60 * 10
        const val HTTP_HEADER_NAME = "Cache-Control"
        const val HTTP_HEADER_VALUE = "public, max-age=${Companion.CACHE_TIME}"
    }
}