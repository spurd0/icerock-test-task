package com.icerockdev.babenko.activities

import android.support.test.rule.ActivityTestRule

import com.icerockdev.babenko.R
import com.icerockdev.babenko.ui.home.HomeActivity

import org.junit.Rule
import org.junit.Test

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView
import android.support.test.espresso.matcher.ViewMatchers.withId

/**
 * Created by Roman Babenko on 09/05/17.
 */
class HomeActivityTest {
    @Rule
    var mHomeActivityTestRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    @Throws(Exception::class)
    fun requestDataFieldsButtonClicked() {
        onView(withId(R.id.fields_request_url_edit_text)).perform(typeTextIntoFocusedView("www.mocky.io/v2/58fa10ce110000b81ad2106c"))
        onView(withId(R.id.getFieldsButton))
    }

}