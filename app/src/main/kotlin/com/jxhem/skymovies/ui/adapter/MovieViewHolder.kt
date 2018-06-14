package com.jxhem.skymovies.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jxhem.skymovies.data.net.Model
import com.jxhem.skymovies.databinding.MovieLayoutBinding


class MovieViewHolder(private val viewBinding: MovieLayoutBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(movie: Model.Movie) {
        viewBinding.movie = movie
    }

    companion object {
        fun create(parent: ViewGroup): MovieViewHolder {
            val view = MovieLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return MovieViewHolder(view)
        }
    }

}