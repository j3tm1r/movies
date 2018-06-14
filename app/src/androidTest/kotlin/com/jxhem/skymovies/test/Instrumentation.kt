package com.jxhem.skymovies.test

import android.os.Bundle
import android.support.test.runner.MonitoringInstrumentation
import cucumber.api.CucumberOptions

import cucumber.api.android.CucumberInstrumentationCore


/***
 * Instrumentation class responsible for running the cucumber features tests
 */
@CucumberOptions(
    features = arrayOf("features"),
    glue = arrayOf("com.jxhem.skymovies.test")
)
class Instrumentation : MonitoringInstrumentation() {
    private val instrumentationCore = CucumberInstrumentationCore(this)

    override fun onCreate(arguments: Bundle) {
        super.onCreate(arguments)

        instrumentationCore.create(arguments)
        start()
    }

    override fun onStart() {
        super.onStart()
        waitForIdleSync()
        instrumentationCore.start()
    }
}