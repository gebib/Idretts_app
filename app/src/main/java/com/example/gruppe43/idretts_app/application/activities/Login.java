package com.example.gruppe43.idretts_app.application.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.helpers.AppActivity;


public class Login extends AppActivity {

    private static final String TAG = "testRun";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button loginBT = (Button) findViewById(R.id.loginBT);
        loginBT.setOnClickListener(this);

        Button registrationBT = (Button) findViewById(R.id.registrationBT);
        registrationBT.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first_page, menu);
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

            String test = "UploadChange";
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginBT:
                Log.i(TAG, "debuggingtekst i stedet for intent-> home");
                //Sett inn kode
                break;

            case R.id.registrationBT:
                Log.i(TAG, "debuggingtekst i stedet for intent-> registrering");
                //Sett inn kode her
                break;

            default:
                break;

        }
    }

}
