package com.example.gruppe43.idretts_app.application.view.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.widget.DatePicker;

import com.example.gruppe43.idretts_app.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar d = Calendar.getInstance();
        int year = d.get(Calendar.YEAR);
        int month = d.get(Calendar.MONTH);
        int date = d.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, date);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int date) {
        populateSetDate(year, month+1, date);
    }

    public void populateSetDate(int year, int month, int day){
        //TODO: read up on FragmentManager and FragmentTransaction 18.4.17
        ageET.setText(day+"/"+month+"/"+year);
    }
}
