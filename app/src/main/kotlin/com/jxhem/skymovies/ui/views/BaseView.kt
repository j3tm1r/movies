package com.jxhem.skymovies.ui.views

import android.arch.lifecycle.ViewModelProvider
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.View
import com.jxhem.skymovies.R
import com.jxhem.skymovies.di.Injectable
import javax.inject.Inject

/**
 *  Base class for our view system. It implements [Injectable]
 *  in order for [Dagger] to correctly inject these fragments.
 *  It extends [Fragment] as our views are based on fragments.
 *
 *  It provides two helper methods to deal with the [Snackbar] Android widget.
 */
abstract class BaseView : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    fun showMessage(view: View, message: String) {
        val snackbar = Snackbar
            .make(view, message, Snackbar.LENGTH_SHORT)
        snackbar.show()
    }

    fun showMessageWithAction(view: View, message: String, onClickListener: View.OnClickListener) {
        val snackbar = Snackbar
            .make(view, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.retry, onClickListener)
        snackbar.show()
    }
}