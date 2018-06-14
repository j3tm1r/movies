package com.jxhem.skymovies.data.net

import io.reactivex.Observable
import retrofit2.http.GET

interface MoviesService {

    @GET("movies")
    fun getMovies(): Observable<Model.ApiResponse>


    companion object {

        val BASE_URL = "https://movies-sample.herokuapp.com/api/"
    }
}