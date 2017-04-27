package com.example.gruppe43.idretts_app.application.view.fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.controll.Authentication;
import com.example.gruppe43.idretts_app.application.controll.DataBaseHelper;
import com.example.gruppe43.idretts_app.application.helper_classes.DatePickerFragment;
import com.example.gruppe43.idretts_app.application.helper_classes.TimePickerFragment;


public class NewActivityRegistration extends Fragment {
    private Spinner newActSpinnerActivityType;
    private Spinner newActIntensitySpinner;
    private EditText newActDate;
    private EditText newActTimeFrom;
    private EditText newActTimeTo;
    private EditText location;
    private TextView newActAdditionalTextInfo;
    private String activityTitle;
    private String setIntensity;
    private Authentication authClass;

    private boolean isForStarttime;


    public NewActivityRegistration() {
        // Required empty public constructor 
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_activity_registration, container, false);
        authClass = new Authentication();
        newActSpinnerActivityType = (Spinner) view.findViewById(R.id.spinnerActivityType);
        newActDate = (EditText) view.findViewById(R.id.newActDate);
        newActTimeFrom = (EditText) view.findViewById(R.id.newActTimeFrom);
        newActTimeTo = (EditText) view.findViewById(R.id.newActTimeTo);
        location = (EditText) view.findViewById(R.id.newActLocation);
        newActIntensitySpinner = (Spinner) view.findViewById(R.id.newActIntensitySpinner);
        newActAdditionalTextInfo = (TextView) view.findViewById(R.id.newActTextInfo);


        newActDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePicker();//////////ok
                    location.requestFocus();
                }
            }
        });
        newActTimeFrom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
               /* DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getActivity().getFragmentManager(), "DIALOG_TIME");*/
                if (hasFocus) {
                    isForStarttime = true;
                    showTimePicker();
                    location.requestFocus();
                }
            }
        });

        newActTimeTo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    isForStarttime = false;
                    showTimePicker();
                    location.requestFocus();
                }

            }
        });


        String[] activityType = {"Football training", "Gym/Strength", "Theory/meeting", "Camp"};
        ArrayAdapter<String> actTypeSpinnerArray = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, activityType);
        actTypeSpinnerArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newActSpinnerActivityType.setAdapter(actTypeSpinnerArray);
        newActSpinnerActivityType.setSelection(0);
        newActSpinnerActivityType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                activityTitle = (String) parent.getItemAtPosition(pos);

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        String[] intensity = {"Easy 20%", "Normal 40%", "Moderate 60%", "High 80%", "Max 100%"};
        ArrayAdapter<String> intensitySpinner = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, intensity);
        intensitySpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newActIntensitySpinner.setAdapter(intensitySpinner);
        newActIntensitySpinner.setSelection(0);
        newActIntensitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                setIntensity = (String) parent.getItemAtPosition(pos);
                switch (pos) {
                    case 0:
                        setIntensity = "20%";
                        break;
                    case 1:
                        setIntensity = "40%";
                        break;
                    case 2:
                        setIntensity = "60%";
                        break;
                    case 3:
                        setIntensity = "80%";
                        break;
                    case 4:
                        setIntensity = "100%";
                        break;
                    default:
                        setIntensity = "20%";
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        DataBaseHelper dbh = new DataBaseHelper();
        String actDate = newActDate.getText().toString().trim();
        String timeFrom = newActTimeFrom.getText().toString().trim();
        String timeTo = newActTimeTo.getText().toString().trim();
        String actPlace = location.getText().toString().trim();
        String textInfo = newActAdditionalTextInfo.getText().toString().trim();

        if (actDate.isEmpty() || timeFrom.isEmpty() || timeTo.isEmpty() || actPlace.isEmpty() || textInfo.isEmpty()) {
            authClass.alert(getString(R.string.tittleTrainerPostFieldEmpty), getString(R.string.tittleTrainerPostFieldEmptyTextInfo));
        } else {
            dbh.postTrainerActivity(activityTitle, actDate, timeFrom, timeTo, actPlace, setIntensity, textInfo);
        }
    }

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        date.setCallBack(ondate);
        date.show(getActivity().getFragmentManager(), "Date Picker");
    }

    private void showTimePicker() {
        TimePickerFragment time = new TimePickerFragment();
        time.setCallBack(ontime);
        time.show(getActivity().getFragmentManager(), "Time Picker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            newActDate.setText(dayOfMonth + "." + monthOfYear + 1 + "." + year);
        }
    };
    TimePickerDialog.OnTimeSetListener ontime = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if (isForStarttime) {
                newActTimeFrom.setText(hourOfDay + ":" + minute);
            } else {
                newActTimeTo.setText(hourOfDay + ":" + minute);
            }
        }
    };
}
