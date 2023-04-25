package com.iapolinarortiz.coffeeshop

import android.util.Log
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun visibilityOfControls() {
        onView(withId(R.id.tv_coffee_shop)).check(matches(isDisplayed()))
        onView(withId(R.id.et_user_name)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_toppings)).check(matches(isDisplayed()))
        onView(withId(R.id.cb_topping_whippedcream)).check(matches(isDisplayed()))
        onView(withId(R.id.cb_topping_chocolate)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_quantity)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_quantity_add)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_quantity_remove)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_price_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_price)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_order)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_cancel)).check(matches(isDisplayed()))
    }

    @Test
    fun increaseQuantity() {
        for (i in 1..3) {
            Log.d("MainActivityTest", "Clicking increase...")
            onView(withId(R.id.btn_quantity_add)).perform(click())
        }
        onView(withId(R.id.tv_quantity)).check(matches(withText("Quantity: 3")))
    }

    @Test
    fun decreaseQuantity() {
        performClick(3, R.id.btn_quantity_add)
        onView(withId(R.id.tv_quantity)).check(matches(withText("Quantity: 3")))
        performClick(2, R.id.btn_quantity_remove)
        onView(withId(R.id.tv_quantity)).check(matches(withText("Quantity: 1")))
    }

    @Test
    fun validateEnabledControls() {
        // When app is created, controls should be disabled as quantity is 0
        onView(withId(R.id.cb_topping_chocolate)).check(matches(isNotEnabled()))
        onView(withId(R.id.cb_topping_whippedcream)).check(matches(isNotEnabled()))
        onView(withId(R.id.btn_order)).check(matches(isNotEnabled()))

        // Quantity > 0
        onView(withId(R.id.btn_quantity_add)).perform(click())

        // Controls should be enabled.
        onView(withId(R.id.cb_topping_chocolate)).check(matches(isEnabled()))
        onView(withId(R.id.cb_topping_whippedcream)).check(matches(isEnabled()))
        onView(withId(R.id.btn_order)).check(matches(isEnabled()))
    }

    @Test
    fun nameInput() {
        onView(withId(R.id.et_user_name)).perform(
            typeText("Isidro"),
            ViewActions.closeSoftKeyboard()
        )
            .check(matches(withText("Isidro")))
    }

    @Test
    fun validateOrderWithoutToppings() {
        val name = "Isidro Apolinar"
        val clicks = 2
        val totalPrice = "10.0"

        onView(withId(R.id.et_user_name)).perform(typeText(name), ViewActions.closeSoftKeyboard())
            .check(matches(withText(name)))
        performClick(clicks, R.id.btn_quantity_add)
        onView(withId(R.id.tv_price)).check(matches(withText(totalPrice)))
        onView(withId(R.id.btn_order)).perform(click())
        onView(withId(R.id.tv_ticket)).check(
            matches(
                withText(
                    String.format(
                        TICKET_MESSAGE,
                        name,
                        clicks.toString(),
                        totalPrice,
                        "false",
                        "false"
                    )
                )
            )
        )
    }

    @Test
    fun validateOrderWithToppings() {
        val name = "Isidro Apolinar"
        val clicks = 2
        val totalPrice = "13.0"

        onView(withId(R.id.et_user_name)).perform(typeText(name), ViewActions.closeSoftKeyboard())
            .check(matches(withText(name)))
        performClick(clicks, R.id.btn_quantity_add)
        onView(withId(R.id.cb_topping_chocolate)).perform(click())
        onView(withId(R.id.cb_topping_whippedcream)).perform(click())
        onView(withId(R.id.tv_price)).check(matches(withText(totalPrice)))
        onView(withId(R.id.btn_order)).perform(click())
        onView(withId(R.id.tv_ticket)).check(
            matches(
                withText(
                    String.format(
                        TICKET_MESSAGE,
                        name,
                        clicks.toString(),
                        totalPrice,
                        "true",
                        "true"
                    )
                )
            )
        )
    }

    @Test
    fun cancelOrderWithToppings() {
        val name = "Isidro Apolinar"
        val clicks = 2
        val totalPrice = "13.0"

        onView(withId(R.id.et_user_name)).perform(typeText(name), ViewActions.closeSoftKeyboard())
            .check(matches(withText(name)))
        performClick(clicks, R.id.btn_quantity_add)
        onView(withId(R.id.cb_topping_chocolate)).perform(click())
        onView(withId(R.id.cb_topping_whippedcream)).perform(click())
        onView(withId(R.id.tv_price)).check(matches(withText(totalPrice)))
        onView(withId(R.id.btn_order)).perform(click())
        onView(withId(R.id.tv_ticket)).check(
            matches(
                withText(
                    String.format(
                        TICKET_MESSAGE,
                        name,
                        clicks.toString(),
                        totalPrice,
                        "true",
                        "true"
                    )
                )
            )
        )

        onView(withId(R.id.btn_cancel)).perform(click())
        onView(withId(R.id.cb_topping_whippedcream)).check(matches(isNotEnabled()))
        onView(withId(R.id.cb_topping_chocolate)).check(matches(isNotEnabled()))
        onView(withId(R.id.tv_quantity)).check(matches(withText("Quantity: 0")))
        onView(withId(R.id.tv_price)).check(matches(withText("0.0")))
        onView(withId(R.id.btn_order)).check(matches(isNotEnabled()))
    }

    private fun performClick(numberClicks: Int, buttonId: Int) {
        for (i in 0 until numberClicks) {
            Log.d("ClickEvent", "Click no. $i on $buttonId")
            onView(withId(buttonId)).perform(click())
        }
    }

    companion object {
        const val TICKET_MESSAGE: String = "Name: %s\n" +
                "Quantity: %s cups of coffee\n" +
                "Total price: %s\n" +
                "Whipped cream: %s\n" +
                "Chocolate: %s\n" +
                "Thank you!"
    }
}