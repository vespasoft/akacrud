package com.akacrud.util;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by luisvespa on 12/14/17.
 */

@SuppressLint("ValidFragment")
public class AlertDateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    TextView txtDate;

    public AlertDateDialog(View view) {
        txtDate = (TextView) view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        String date = year+"-"+(month+1)+"-"+day;
        txtDate.setText( date );
    }
}
