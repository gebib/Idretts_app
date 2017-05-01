package com.example.gruppe43.idretts_app.application.helper_classes;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;

import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by gebi9 on 27-Apr-17.
 */

public class TimePickerFragment extends DialogFragment{
    TimePickerDialog.OnTimeSetListener onTimeSet;
    //private int hour, minute;
    public TimePickerFragment() {}


    public void setCallBack(TimePickerDialog.OnTimeSetListener ontime){
        onTimeSet = ontime;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(),onTimeSet, hour, minute, true);
    }
}