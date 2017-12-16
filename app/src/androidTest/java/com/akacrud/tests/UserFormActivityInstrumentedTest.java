package com.akacrud.tests;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.akacrud.*;
import com.akacrud.R;
import com.akacrud.model.User;
import com.akacrud.ui.activities.UserFormActivity;

import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by luisvespa on 12/16/17.
 */

public class UserFormActivityInstrumentedTest {

    @Rule
    public ActivityTestRule<UserFormActivity> rule  = new  ActivityTestRule<UserFormActivity>(UserFormActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            InstrumentationRegistry.getTargetContext();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.putExtra("mode_update", true);
            intent.putExtra("user", new User(587, "Use Test", "2002-03-21T00:00:00"));
            return intent;
        }
    };

    @Test
    public void ensureIntentDataIsDisplayed() throws Exception {
        UserFormActivity activity = rule.getActivity();

        View viewName = activity.findViewById(R.id.textViewName);

        assertThat(viewName,notNullValue());
        assertThat(viewName, instanceOf(AutoCompleteTextView.class));
        AutoCompleteTextView textViewName = (AutoCompleteTextView) viewName;
        assertThat(textViewName.getText().toString(),is("Use Test"));

        View viewBirthDate = activity.findViewById(R.id.textViewBirthDate);
        assertThat(viewName,notNullValue());
        assertThat(viewName, instanceOf(TextView.class));
        TextView textViewBirthDate = (TextView) viewName;
        assertThat(textViewBirthDate.getText().toString(),is("2002-03-21T00:00:00"));

    }

}
