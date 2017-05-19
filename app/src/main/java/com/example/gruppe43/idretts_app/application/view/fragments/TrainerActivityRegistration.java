package com.example.gruppe43.idretts_app.application.view.fragments;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ParseException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.Authentication.DatabaseInterface.DataBaseHelperA;
import com.example.gruppe43.idretts_app.application.helper_classes.DatePickerFragment;
import com.example.gruppe43.idretts_app.application.helper_classes.TimePickerFragment;
import com.example.gruppe43.idretts_app.application.interfaces.FragmentActivityInterface;

//Idretts-app bachelor oppgave 2017
//Ole-Kristian Steiro, Tasmia Faruque, Gebi Beshir

public class TrainerActivityRegistration extends Fragment {
    private Spinner newActSpinnerActivityType;
    private SeekBar newActIntensitySeeker;
    private EditText newActDate;
    private EditText newActTimeFrom;
    private EditText newActTimeTo;
    private EditText location;
    private TextView newActAdditionalTextInfo;
    private EditText nonFunctionalEditTextForUse;
    private TextView seekBarPersentageDisplay;
    private String activityTitle;
    public static TrainerActivityRegistration nar;
    private FragmentActivityInterface mCallback;
    private String[] cacheDataForEdit;
    private static String selectedActivityPostKey;

    private boolean isForStarttime;
    private String titleSpinnerPos;

    public TrainerActivityRegistration() {
        // Required empty public constructor
    }

    //set the selected post key in case of editing the key is needed.
    public static void setSelectedActivityPostKey(String selectedActivityPostKey) {
        TrainerActivityRegistration.selectedActivityPostKey = selectedActivityPostKey;
    }

    public static String getSelectedActivityPostKey() {
        return selectedActivityPostKey;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (FragmentActivityInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement IFragmentToActivity");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trainer_activity_registration, container, false);
        nar = this;
        newActSpinnerActivityType = (Spinner) view.findViewById(R.id.spinnerActivityType);
        newActDate = (EditText) view.findViewById(R.id.newActDate);
        newActTimeFrom = (EditText) view.findViewById(R.id.newActTimeFrom);
        newActTimeTo = (EditText) view.findViewById(R.id.newActTimeTo);
        location = (EditText) view.findViewById(R.id.newActLocation);
        newActIntensitySeeker = (SeekBar) view.findViewById(R.id.newActIntensitySlider);
        newActAdditionalTextInfo = (TextView) view.findViewById(R.id.newActTextInfo);
        nonFunctionalEditTextForUse = (EditText) view.findViewById(R.id.makeGapAndForFocusShiftingPurpose);
        seekBarPersentageDisplay = (TextView) view.findViewById(R.id.sliderPercentage);

        String[] activityType = {"Football training", "Gym/Strength", "Theory/meeting", "Football match"};
        ArrayAdapter<String> actTypeSpinnerArray = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, activityType);
        actTypeSpinnerArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newActSpinnerActivityType.setAdapter(actTypeSpinnerArray);
        newActSpinnerActivityType.setSelection(0);

        newActIntensitySeeker.setMinimumHeight(0);
        newActIntensitySeeker.setMax(100);
        newActIntensitySeeker.setProgress(20);
        seekBarPersentageDisplay.setText("20%");
        try {
            if (FullActivityInfo.getIsEditClicked()) {
                DataBaseHelperA dbh = new DataBaseHelperA(mCallback.getContext());
                cacheDataForEdit = dbh.getActivityDataCache();
                FullActivityInfo.setIsEditClicked(false);
                if (cacheDataForEdit != null) {
                    int spinnerIndexToBeSet = 0;
                    if (cacheDataForEdit[0].equals("Football training") || cacheDataForEdit[0].equals("Fotballtrening")) {
                        spinnerIndexToBeSet = 0;
                    } else if (cacheDataForEdit[0].equals("Gym/Strength") || cacheDataForEdit[0].equals("Gym/Styrke")) {
                        spinnerIndexToBeSet = 1;
                    } else if (cacheDataForEdit[0].equals("Theory/meeting") || cacheDataForEdit[0].equals("Teori/møte")) {
                        spinnerIndexToBeSet = 2;
                    } else if (cacheDataForEdit[0].equals("Football match") || cacheDataForEdit[0].equals("Fotballkamp")) {
                        spinnerIndexToBeSet = 3;
                    }
                    newActSpinnerActivityType.setSelection(spinnerIndexToBeSet);
                    newActDate.setText(cacheDataForEdit[1]);
                    newActTimeFrom.setText(cacheDataForEdit[2]);
                    newActTimeTo.setText(cacheDataForEdit[3]);
                    location.setText(cacheDataForEdit[4]);

                    try{
                        String intensityLevel = cacheDataForEdit[5].substring(0,cacheDataForEdit[5].length()-1);
                        int intValue = Integer.parseInt(intensityLevel);
                        newActIntensitySeeker.setProgress(intValue);
                        seekBarPersentageDisplay.setText(intValue+"%");
                    }catch (ParseException pe){
                        pe.printStackTrace();
                    }

                    String tempIntensity = cacheDataForEdit[5];
                    String numberPart = tempIntensity.substring(0, tempIntensity.length() - 1);
                    int intensityValue = Integer.parseInt(numberPart);
                    newActIntensitySeeker.setProgress(intensityValue);

                    newActAdditionalTextInfo.setText(cacheDataForEdit[7]);
                }
            }else{
                selectedActivityPostKey = "";
            }
        } catch (Exception e) {
            Log.i("Bundle::::", "Exception");
        }
        try {
            newActIntensitySeeker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    seekBarPersentageDisplay.setText(progress + "%");
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
        } catch (Exception e) {
            System.out.println("Seeker failure!");
        }

        newActDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePicker();
                    nonFunctionalEditTextForUse.requestFocus();
                }
            }
        });
        newActTimeFrom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    isForStarttime = true;
                    showTimePicker();
                    nonFunctionalEditTextForUse.requestFocus();
                }
            }
        });

        newActTimeTo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    isForStarttime = false;
                    showTimePicker();
                    nonFunctionalEditTextForUse.requestFocus();
                }
            }
        });
        location.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    location.setHint("");
                }
            }
        });

        newActAdditionalTextInfo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    newActAdditionalTextInfo.setHint("");
                } else if (!hasFocus && newActAdditionalTextInfo.getText().toString().trim().equals("")) {
                    newActAdditionalTextInfo.setHint("");
                }
            }
        });

        newActSpinnerActivityType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                activityTitle = (String) parent.getItemAtPosition(pos);
                titleSpinnerPos = pos + "";
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return view;
    }


    public void registerActivity() {
        DataBaseHelperA dbh = new DataBaseHelperA(mCallback.getContext());
        String actDate = newActDate.getText().toString().trim();
        String timeFrom = newActTimeFrom.getText().toString().trim();
        String timeTo = newActTimeTo.getText().toString().trim();
        String actPlace = location.getText().toString().trim();
        String textInfo = newActAdditionalTextInfo.getText().toString().trim();
        String setIntensity = seekBarPersentageDisplay.getText().toString();
        String icon = "";
        if (titleSpinnerPos.equals("0")) {
            icon = "training_f";
        } else if (titleSpinnerPos.equals("1")) {
            icon = "training";
        } else if (titleSpinnerPos.equals("2")) {
            icon = "meeting";
        } else if (titleSpinnerPos.equals("3")) {
            icon = "match";
        }

        if (actDate.isEmpty() || timeFrom.isEmpty() || timeTo.isEmpty() || actPlace.isEmpty() || textInfo.isEmpty()) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
            builder1.setTitle(R.string.tittleTrainerPostFieldEmpty);
            builder1.setMessage(getString(R.string.tittleTrainerPostFieldEmptyTextInfo));
            builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //ingen action.
                }
            });
            AlertDialog alert11 = builder1.create();
            alert11.show();
        } else {
            dbh.postTrainerActivity(activityTitle, actDate, timeFrom, timeTo, actPlace, setIntensity, textInfo, icon,selectedActivityPostKey);
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
            newActDate.setText(dayOfMonth + "." + (monthOfYear+1) + "." + year);
        }
    };
    TimePickerDialog.OnTimeSetListener ontime = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String modifMinut = "";
            String modifHour = "";
            if (minute < 10) {
                modifMinut = "0" + minute;
            } else {
                modifMinut = "" + minute;
            }
            if (hourOfDay < 10) {
                modifHour = "0" + hourOfDay;
            } else {
                modifHour = "" + hourOfDay;
            }
            if (isForStarttime) {
                newActTimeFrom.setText(modifHour + ":" + modifMinut);
            } else {
                newActTimeTo.setText(modifHour + ":" + modifMinut);
            }
        }
    };

    @Override
    public void onStop() {
        super.onStop();
        mCallback.setIsOnNewActivityRegisterPage(false);
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
