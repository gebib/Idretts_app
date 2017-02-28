package com.example.gruppe43.idretts_app.application.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.helpers.AppActivity;


public class Registration extends AppActivity {

    /*DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    edittext.setOnClickListener(new View.OnClickListener(){

        @Override
        public void onClick(view v){
            new DatePickerDialog(Registration.this, date,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();
        }


    });

    private void updateLabel() {
        String format = "DD/mm/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
        edittext.setText(sdf.format(calendar.getTime));
    }*/


    Button registerBT;
    Calendar calendar = Calendar.getInstance();
    EditText edittext = (EditText) findViewById(R.id.ageET);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        registerBT  =   (Button)    findViewById(R.id.registerBT);
        //toLogInBT   =   (Button)    findViewById(R.id.);

        registerBT.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_registration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.registerBT:
                Toast.makeText(this, "registered!",Toast.LENGTH_LONG).show();
                new DatePickerDialog(Registration.this,listener,
                        calendar.get(calendar.YEAR),
                        calendar.get(calendar.MONTH),
                        calendar.get(calendar.DAY_OF_MONTH)).show();
                break;
            default:
                break;

        }

    }
    DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            edittext.setText(dayOfMonth+"/"+month+"/"+year);
        }
    };



}