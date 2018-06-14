package com.jxhem.skymovies.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.jxhem.skymovies.data.net.Model

class MoviesAdapter(
    private var movies: List<Model.Movie>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieViewHolder.create(parent)
    }

    fun updateMovies(newMovies: List<Model.Movie>) {
        this.movies = newMovies
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? MovieViewHolder)?.bind(movies[position])
    }
}