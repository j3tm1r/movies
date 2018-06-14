package com.jxhem.skymovies.data.interceptor

import com.jxhem.skymovies.data.net.interceptors.ResponseCacheInterceptor
import junit.framework.Assert.assertEquals
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class ResponseCacheInterceptorTest {

    @Before
    fun setup() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testCacheHeaderIsAdded() {
        val mockWebServer = MockWebServer()
        mockWebServer.start()
        mockWebServer.enqueue(MockResponse())
        mockWebServer.enqueue(MockResponse())

        val okHttpClient =
            OkHttpClient.Builder()
                .addNetworkInterceptor(ResponseCacheInterceptor())
                .build()
        val response =
            okHttpClient.newCall(Request.Builder().url(mockWebServer.url("/movies")).head().build())
                .execute()

        assertEquals(
            ResponseCacheInterceptor.HTTP_HEADER_VALUE,
            response.header(ResponseCacheInterceptor.HTTP_HEADER_NAME)
        )
    }
}