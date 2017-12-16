package com.akacrud.tests;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.akacrud.R;
import com.akacrud.ui.activities.UserFormActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by luisvespa on 12/16/17.
 */

@RunWith(AndroidJUnit4.class)
public class CreateUserTest {

    @Rule
    public ActivityTestRule<UserFormActivity> mActivityRule =
            new ActivityTestRule<>(UserFormActivity.class);

    @Test
    public void clickAccept_opensCreateUserScreen() {
        String name = "Fiorella Vespa";
        String birthdate = "2017-12-04T00:00:00";

        //find the firstname edit text and type in the first name
        onView(withId(R.id.textViewName)).perform(typeText(name), closeSoftKeyboard());

        //find the lastname edit text and type in the last name
        // onView(withId(R.id.textViewBirthDate)).perform(click());

        //locate and click on the accept button
        onView(withId(R.id.action_save)).perform(click());

    }
}
