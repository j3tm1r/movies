package com.jxhem.skymovies.ui.views.mainview

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.jxhem.skymovies.data.net.Model
import com.jxhem.skymovies.data.source.DataSource
import com.jxhem.skymovies.data.source.Resource
import com.jxhem.skymovies.testing.OpenForTesting
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@OpenForTesting
class MainViewModel
@Inject constructor(private val dataRepository: DataSource) : ViewModel() {

    val movies: MutableLiveData<Resource<List<Model.Movie>>> = MutableLiveData()

    init {
        movies.postValue(Resource.loading(LOADING_MESSAGE))
    }

    fun getMovies(): LiveData<Resource<List<Model.Movie>>> {
        loadMovies()
        return movies
    }

    private fun loadMovies() {
        dataRepository.getMovies()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .doOnError {
                movies.postValue(Resource.error(msg = ERROR_MESSAGE))
            }
            .subscribe {
                movies.postValue(Resource.success(it.data))
            }
    }

    fun filterMovies(filter: String) {
        dataRepository.getMovies()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .doOnError {
                movies.postValue(Resource.error(msg = ERROR_MESSAGE))
            }
            .subscribe {
                movies.postValue(
                    Resource.success(
                        dataRepository.getFilterMovies(it.data, filter)
                    )
                )
            }
    }

    companion object {
        const val ERROR_MESSAGE = "Error while loading the movies"
        const val LOADING_MESSAGE = "Loading movies..."
    }
}
