package com.jxhem.skymovies.data.source

import com.jxhem.skymovies.data.net.Model
import io.reactivex.Observable

abstract class DataSource {
    abstract fun getMovies(): Observable<Model.ApiResponse>
    fun getFilterMovies(data: List<Model.Movie>, filter: String): List<Model.Movie> {
        val newList = data.filter {
            it.title.decapitalize().contains(
                filter.decapitalize(),
                true
            ) || it.genre.decapitalize().contains(filter, true)
        }
        return newList
    }
}