package com.akacrud.tests;

import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;

import com.akacrud.*;
import com.akacrud.R;
import com.akacrud.ui.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by luisvespa on 12/16/17.
 * Instrumented Unit Test of MainActivity.class
 */

public class MainActivityInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> rule  = new  ActivityTestRule<>(MainActivity.class);

    @Test
    // this instrumented unit test verify that recyclerview work correctly
    public void ensureRecyclerViewIsPresent() throws Exception {
        MainActivity activity = rule.getActivity();
        View viewById = activity.findViewById(R.id.recycler_view);
        assertThat(viewById,notNullValue());
        assertThat(viewById, instanceOf(RecyclerView.class));
        RecyclerView recyclerView = (RecyclerView) viewById;
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        assertThat(adapter, instanceOf(RecyclerView.Adapter.class));
        assertTrue(adapter.getItemCount()>=0);
    }

}
