package com.jxhem.skymovies.ui.views.mainview

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.jxhem.skymovies.R
import com.jxhem.skymovies.data.source.ResourceStatus
import com.jxhem.skymovies.ui.adapter.MoviesAdapter
import com.jxhem.skymovies.ui.navigation.NavigationProvider
import com.jxhem.skymovies.ui.views.BaseView
import kotlinx.android.synthetic.main.content_movies.*
import javax.inject.Inject


class MainView : BaseView() {

    @Inject
    lateinit var navigationController: NavigationProvider

    private lateinit var viewModel: MainViewModel
    private lateinit var rootView: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.content_movies, container, false) as LinearLayout
        return rootView
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MainViewModel::class.java)

        var columnCount =
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 5
            else 3

        movies_grid.layoutManager = GridLayoutManager(this.context, columnCount)
        movies_grid.adapter = MoviesAdapter(emptyList())

        search_box.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                viewModel.filterMovies(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })
        setupMovies()
    }

    private fun setupMovies() {
        viewModel.getMovies()
            .observe(this, Observer {
                when (it?.status) {
                    ResourceStatus.SUCCESS -> {
                        (movies_grid.adapter as MoviesAdapter).updateMovies(it.data!!)
                    }
                    ResourceStatus.LOADING -> {
                        showMessage(rootView, it.message!!)
                    }
                    else -> {
                        showMessageWithAction(
                            rootView,
                            it?.message!!,
                            View.OnClickListener {
                                viewModel.getMovies()
                            }
                        )
                    }
                }
            })
    }

    companion object {
        fun create(): MainView {
            return MainView()
        }
    }
}