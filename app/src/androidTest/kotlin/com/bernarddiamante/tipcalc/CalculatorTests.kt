package com.bernarddiamante.tipcalc

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.containsString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CalculatorTests {
    // Test rules get executed before every test in this class
    @get:Rule()
    // Launch MainActivity
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun calculate_20_percent_tip() {
        // Input cost of service
        onView(withId(R.id.etCostOfService))
            .perform(typeText("100.00"))
            .perform(ViewActions.closeSoftKeyboard())

        // Click on calculate
        onView(withId(R.id.btCalculate))
            .perform(click())

        // Check result
        onView(withId(R.id.tvTipResult))
            .check(matches(withText(containsString("$18.00"))))
    }
}