package com.jxhem.skymovies.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.jxhem.skymovies.di.Injectable
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.plugins.RxJavaPlugins
import javax.inject.Inject


/**
 * [BaseApp] handles the Activity and Fragment Injection duties.
 * It is also responsible for ignoring Activities not tied to the DI cycle such as the
 * SingleFragmentActivity.
 */
open class BaseApp : Application(), HasActivityInjector {


    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        // because of https://github.com/ReactiveX/RxJava/wiki/What's-different-in-2.0#error-handling
        RxJavaPlugins.setErrorHandler { e -> }

        // Needed for injectin Fragments that extend Injectable
        registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                handleActivity(activity)
            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityDestroyed(activity: Activity) {

            }
        })
    }

    private fun handleActivity(activity: Activity) {

        if (activity is HasSupportFragmentInjector) {
            AndroidInjection.inject(activity)
        }
        // This else statement returns from function to make
        // it possible to use the SingleFragmentActivity
        else return

        (activity as? FragmentActivity)?.supportFragmentManager?.registerFragmentLifecycleCallbacks(
            object : FragmentManager.FragmentLifecycleCallbacks() {
                override fun onFragmentCreated(
                    fm: FragmentManager?, f: Fragment?,
                    savedInstanceState: Bundle?
                ) {
                    if (f is Injectable) {
                        AndroidSupportInjection.inject(f)
                    }
                }
            }, true
        )
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityDispatchingAndroidInjector
    }
}