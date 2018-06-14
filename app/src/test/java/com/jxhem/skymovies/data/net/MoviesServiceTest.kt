package com.jxhem.skymovies.data.net

import io.reactivex.subscribers.TestSubscriber
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class MoviesServiceTest {


    private lateinit var mockWebServer: MockWebServer

    private lateinit var service: MoviesService

    private lateinit var mSubScriber: TestSubscriber<Model.ApiResponse>

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(MoviesService::class.java)

        mSubScriber = TestSubscriber()
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun get_movies_list() {
        enqueueResponse("movies.json")
        val moviesList = service.getMovies().blockingFirst().data

        val request = mockWebServer.takeRequest()
        assertThat(request.path, `is`("/movies"))

        assertThat<List<Model.Movie>>(moviesList, notNullValue())
        assertThat(moviesList[0].poster, `is`("https://goo.gl/1zUyyq"))
        assertThat(moviesList[2].genre, `is`("Action"))
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader
            .getResourceAsStream("api-response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(Charsets.UTF_8))
        )
    }
}