package com.jxhem.skymovies.ui

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.jxhem.skymovies.data.net.Model
import com.jxhem.skymovies.data.source.DataSource
import com.jxhem.skymovies.data.source.Resource
import com.jxhem.skymovies.helpers.RxJavaImmediateTestRule
import com.jxhem.skymovies.ui.views.mainview.MainViewModel
import com.jxhem.skymovies.util.mock
import io.reactivex.Observable
import io.reactivex.plugins.RxJavaPlugins
import junit.framework.Assert.assertEquals
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*


@RunWith(JUnit4::class)
class MainViewModelTest {


    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val schedulers = RxJavaImmediateTestRule()

    private var repository: DataSource = mock(DataSource::class.java)
    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        RxJavaPlugins.setErrorHandler { e -> }
        mainViewModel = MainViewModel(repository)
        addTestCases()
    }

    @After
    fun cleanUp() {
        reset(repository)
    }

    @Test
    fun testNotNull() {
        assertThat(mainViewModel.movies, notNullValue())
        verify(repository, never()).getMovies()
    }

    private fun addTestCases() {
        doReturn(
            Observable.just(
                Model.ApiResponse(
                    listOf(
                        Model.Movie(1, "ti", "2000", "horror", "url"),
                        Model.Movie(2, "to", "2010", "action", "url2")
                    )
                )
            )
        ).`when`(repository).getMovies()
    }

    @Test
    fun errorIsReported() {
        val errorMesage = "Error loading data"
        doReturn(
            Observable.error<Model.ApiResponse>(Throwable(errorMesage))
        ).`when`(repository).getMovies()

        // When
        mainViewModel.getMovies()

        // Than
        verify(repository).getMovies()
        assertEquals(
            Resource.error<List<Model.Movie>>(errorMesage),
            mainViewModel.getMovies().value
        )
    }

    @Test
    fun testMoviesDoLoad() {
        mainViewModel.getMovies().observeForever(mock())
        verify(repository).getMovies()
        assertEquals(
            Resource.success(
                listOf(
                    Model.Movie(1, "ti", "2000", "horror", "url"),
                    Model.Movie(2, "to", "2010", "action", "url2")
                )
            ),
            mainViewModel.getMovies().value
        )
    }

    @Test
    fun testMoviesAreFiltered() {
        val observer: Observer<Resource<List<Model.Movie>>> = mock()

        mainViewModel.movies.observeForever { observer }

        mainViewModel.filterMovies("1")
        verify(repository).getMovies()
        assertEquals(
            Resource.success(emptyList<Model.Movie>()),
            mainViewModel.movies.value
        )

        mainViewModel.filterMovies("to")
        assertEquals(
            Resource.success(
                listOf(
                    Model.Movie(2, "to", "2010", "action", "url2")
                )
            ),
            mainViewModel.movies.value
        )

    }

}