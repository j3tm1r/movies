package com.jxhem.skymovies.data.net

object Model {

    data class ApiResponse(
        val data: List<Movie>
    )

    data class Movie(
        val id: Int,
        val title: String,
        val year: String,
        val genre: String,
        val poster: String
    )
}