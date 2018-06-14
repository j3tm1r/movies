package com.jxhem.skymovies.data.source

import com.jxhem.skymovies.data.net.Model
import com.jxhem.skymovies.data.net.MoviesService
import com.jxhem.skymovies.data.repository.DataRepository
import com.jxhem.skymovies.util.mock
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.verify

@RunWith(JUnit4::class)
class DataRepositoryTest {

    val moviesService: MoviesService = mock()
    val dataRepository: DataRepository = DataRepository(moviesService)

    @Before
    fun setup() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun moviesServiceIsCalled() {
        dataRepository.getMovies()
        verify(moviesService).getMovies()
    }


    @Test
    fun moviesAreFilterd() {
        val moviesList = listOf(
            Model.Movie(1, "tu", "2000", "horror", "url"),
            Model.Movie(2, "to", "2010", "action", "url2")
        )
        assertEquals(
            dataRepository.getFilterMovies(moviesList, "it"),
            emptyList<Model.Movie>()
        )

        assertEquals(
            listOf(Model.Movie(1, "tu", "2000", "horror", "url")),
            dataRepository.getFilterMovies(moviesList, "tu")
        )

        assertEquals(
            listOf(Model.Movie(2, "to", "2010", "action", "url2")),
            dataRepository.getFilterMovies(moviesList, "action")
        )

    }
}