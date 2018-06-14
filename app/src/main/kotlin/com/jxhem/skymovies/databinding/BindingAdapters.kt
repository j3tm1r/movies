package com.jxhem.skymovies.databinding

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.squareup.picasso.Picasso

object BindingAdapters {


    /***
     * [BindigAdapter] responsible for loading the poster at the [posterUrl] location
     * into the bound ImageView
     */
    @JvmStatic
    @BindingAdapter("moviePoster")
    fun moviewPoster(view: ImageView, posterUrl: String) {
        Picasso
            .with(view.context)
            .load(posterUrl)
            .into(view)
    }
}
