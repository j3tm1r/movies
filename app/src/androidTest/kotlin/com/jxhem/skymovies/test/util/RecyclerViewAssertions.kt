package com.jxhem.skymovies.test.util

import android.support.test.espresso.NoMatchingViewException
import android.support.test.espresso.ViewAssertion
import android.support.v7.widget.RecyclerView
import android.view.View
import org.hamcrest.Matchers.`is`
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue


/***
 * Helper class for dealing with the recyclerview's adapter in Espresso tests
 */
object RecyclerViewAssertions {
    fun adapterItemCountEquals(count: Int): ViewAssertion {
        return ItemCountAssertion(
            ItemCountAssertion.MODE_EQUALS,
            count
        )
    }

    fun adapterItemCountLowerThan(count: Int): ViewAssertion {
        return ItemCountAssertion(
            ItemCountAssertion.MODE_LESS_THAN,
            count
        )
    }

    private class ItemCountAssertion internal constructor(
        private val mode: Int,
        private val expectedChildCount: Int
    ) : ViewAssertion {

        override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
            if (noViewFoundException != null) {
                throw noViewFoundException
            }

            val recyclerView = view as RecyclerView
            val adapter = recyclerView.adapter

            if (mode == MODE_EQUALS) {
                assertThat(expectedChildCount, `is`(adapter.itemCount))
            } else if (mode == MODE_LESS_THAN) {
                assertTrue(expectedChildCount > adapter.itemCount)
            }
        }

        companion object {
            val MODE_EQUALS = 0
            val MODE_LESS_THAN = 1
        }
    }
}