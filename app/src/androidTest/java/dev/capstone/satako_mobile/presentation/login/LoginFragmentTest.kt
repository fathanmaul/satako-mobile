package dev.capstone.satako_mobile.presentation.login

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import dev.capstone.satako_mobile.R
import dev.capstone.satako_mobile.presentation.MainActivity
import dev.capstone.satako_mobile.utils.EspressoIdlingResource
import dev.capstone.satako_mobile.utils.IdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class LoginFragmentTest {

    @get:Rule
    val activity = ActivityScenarioRule(MainActivity::class.java)

    private val idlingResource = IdlingResource()

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun testLoginSuccess() {
        idlingResource.setIdleState(false)

        val email = "ruben@gmail.com"
        val password = "123ruben123"

        onView(withId(R.id.button_get_started))
            .perform(click())

        onView(withId(R.id.email_edit_text))
            .perform(typeText(email), closeSoftKeyboard())
        onView(withId(R.id.password_edit_text))
            .perform(typeText(password), closeSoftKeyboard())
        onView(withId(R.id.sign_in_button))
            .perform(click())

        idlingResource.setIdleState(true)

        Thread.sleep(1000)

        onView(withId(R.id.home_heading))
            .check(matches(isDisplayed()))
    }

}