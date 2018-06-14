package com.jxhem.skymovies.data.repository

import com.jxhem.skymovies.data.net.Model
import com.jxhem.skymovies.data.net.MoviesService
import com.jxhem.skymovies.data.source.DataSource
import com.jxhem.skymovies.testing.OpenForTesting
import io.reactivex.Observable
import javax.inject.Inject

@OpenForTesting
class DataRepository
@Inject constructor(
    private val moviesService: MoviesService
) : DataSource() {

    override fun getMovies(): Observable<Model.ApiResponse> {
        return moviesService.getMovies()
    }
}