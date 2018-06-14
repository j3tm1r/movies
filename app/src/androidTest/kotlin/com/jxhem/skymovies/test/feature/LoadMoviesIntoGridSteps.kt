package com.jxhem.skymovies.test.feature

import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.support.design.widget.Snackbar
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.closeSoftKeyboard
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.v4.app.Fragment
import com.jxhem.skymovies.R
import com.jxhem.skymovies.data.net.Model
import com.jxhem.skymovies.data.repository.MockRepository
import com.jxhem.skymovies.data.source.DataSource
import com.jxhem.skymovies.test.util.RecyclerViewAssertions.adapterItemCountEquals
import com.jxhem.skymovies.test.util.ViewModelUtil
import com.jxhem.skymovies.testing.SingleFragmentActivity
import com.jxhem.skymovies.ui.navigation.NavigationProvider
import com.jxhem.skymovies.ui.views.mainview.MainView
import com.jxhem.skymovies.ui.views.mainview.MainViewModel
import cucumber.api.CucumberOptions
import cucumber.api.java.After
import cucumber.api.java.Before
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import io.reactivex.Observable
import io.reactivex.plugins.RxJavaPlugins
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertNotNull
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock


@CucumberOptions(
    features = ["features"],
    glue = ["com.jxhem.skymovies.test"]
)
class LoadMoviesIntoGridSteps {

    @Rule
    var singleFragmentActivityTestRule =
        ActivityTestRule(
            SingleFragmentActivity::class.java,
            true,
            true
        )

    private lateinit var singleFragmentActivity: SingleFragmentActivity
    private val mockRepository: MockRepository = MockRepository()
    private val viewModelFactory: ViewModelProvider.Factory =
        ViewModelUtil.createFor(MainViewModel(mockRepository))

    private lateinit var mainView: MainView

    @Before
    fun setup() {
        RxJavaPlugins.setErrorHandler { e -> }
        singleFragmentActivity = singleFragmentActivityTestRule.launchActivity(Intent())
    }

    @After
    fun tearDown() {
        singleFragmentActivityTestRule.activity?.finish()
    }

    @Given("^I have a SingleFragmentActivity\$")
    fun I_have_a_SingleFragmentActivity() {
        assertNotNull(singleFragmentActivity)
    }

    @Given("^I create a MainView\$")
    fun I_create_a_MainView() {
        mainView = MainView.create()
        mainView.navigationController = mock(NavigationProvider::class.java)
        mainView.viewModelFactory = viewModelFactory
        singleFragmentActivity.setFragment(mainView)
    }


    @Given("^I feed to the MainView a bad repository\$")
    fun I_feed_to_the_MainView_a_bad_repository() {
        val errorMesage = "Error loading data"
        val dataRepository: DataSource = mock(DataSource::class.java)
        doReturn(
            Observable.error<Model.ApiResponse>(Throwable(errorMesage))
        ).`when`(dataRepository).getMovies()

        mainView = MainView.create()
        mainView.navigationController = mock(NavigationProvider::class.java)
        mainView.viewModelFactory = ViewModelUtil.createFor(MainViewModel(dataRepository))
        singleFragmentActivity.setFragment(mainView)
    }

    @Given("^I create a MainView with a loading repository\$")
    fun I_create_a_mainView_with_a_loading_repo() {
        val dataSource: DataSource = mock(DataSource::class.java)
        val viewmodel = MainViewModel(dataSource)
        doReturn(Observable.empty<Model.ApiResponse>()).`when`(dataSource).getMovies()

        mainView = MainView.create()
        mainView.navigationController = mock(NavigationProvider::class.java)
        mainView.viewModelFactory = ViewModelUtil.createFor(viewmodel)
    }

    @When("^I have a MainView")
    fun I_have_a_MainView() {
        assertNotNull(findFragmentByTag(MainView::class.java.simpleName))
    }

    @When("^The MainView is (true|false)\$")
    fun The_MainView_is_visible(visible: Boolean) {
        mainView = findFragmentByTag(MainView::class.java.simpleName) as MainView
        if (visible)
            assert(mainView.isVisible)
        else
            assertFalse(mainView.isVisible)
    }

    @When("^There are no movies loaded")
    fun no_movies_loded() {
        onView(
            ViewMatchers.withId(R.id.movies_grid)
        ).check(
            adapterItemCountEquals(0)
        )
    }


    @When("^The movies have been loaded")
    fun The_movies_have_been_loaded() {
        onView(
            ViewMatchers.withId(R.id.movies_grid)
        ).check(
            adapterItemCountEquals(mockRepository.getMovies().blockingFirst().data.size)
        )
    }

    @When("^I type into the searchbar (\\S+)\$")
    fun I_type_into_the_searchbar(filterString: String) {
        onView(
            ViewMatchers.withId(R.id.search_box)
        ).perform(
            typeText(filterString), closeSoftKeyboard()
        )
    }

    @Then("^I should see (\\d+) movies in the grid\$")
    fun I_should_see_movies_in_the_grid(count: Int) {
        onView(
            ViewMatchers.withId(R.id.movies_grid)
        ).check(
            adapterItemCountEquals(count)
        )
    }

    @Then("^The error is (true|false)\$")
    fun The_error_is_visible(visible: Boolean) {
        val visibility = when (visible) {
            true -> isCompletelyDisplayed()
            false -> not(isCompletelyDisplayed())
        }

        withText(MainViewModel.ERROR_MESSAGE).matches(
            allOf(
                isDescendantOfA(
                    isAssignableFrom(Snackbar.SnackbarLayout::class.java)
                ),
                visibility
            )
        )
    }


    @Then("^The loading message is (true|false)\$")
    fun The_loading_message_is_visible(visible: Boolean) {
        val visibility = when (visible) {
            true -> isCompletelyDisplayed()
            false -> not(isCompletelyDisplayed())
        }

        withText(MainViewModel.LOADING_MESSAGE).matches(
            allOf(
                isDescendantOfA(
                    isAssignableFrom(Snackbar.SnackbarLayout::class.java)
                ),
                visibility
            )
        )
    }

    private fun findFragmentByTag(tag: String): Fragment? {
        return singleFragmentActivity.supportFragmentManager?.findFragmentByTag(tag)
    }
}
