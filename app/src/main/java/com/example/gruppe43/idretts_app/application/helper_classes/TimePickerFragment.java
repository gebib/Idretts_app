package com.example.gruppe43.idretts_app.application.helper_classes;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;

import java.util.Calendar;

//Idretts-app bachelor oppgave 2017
//Ole-Kristian Steiro, Tasmia Faruque, Gebi Beshir

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