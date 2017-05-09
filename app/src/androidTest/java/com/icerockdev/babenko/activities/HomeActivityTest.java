package com.icerockdev.babenko.activities;

import android.support.test.rule.ActivityTestRule;

import com.icerockdev.babenko.R;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Roman Babenko on 09/05/17.
 */
public class HomeActivityTest {
    @Rule
    public ActivityTestRule mHomeActivityTestRule =  new ActivityTestRule(HomeActivity.class);

    @Test
    public void requestDataFieldsButtonClicked() throws Exception {
        onView(withId(R.id.fieldsRequestUrlEditText)).perform(typeTextIntoFocusedView("www.mocky.io/v2/58fa10ce110000b81ad2106c"));
        onView(withId(R.id.getFieldsButton));
    }

}