package com.example.gruppe43.idretts_app.application.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.helpers.AppActivity;


public class Login extends AppActivity {
    Button loginBT,registrationBT;
    private static final String TAG = "testRun";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBT = (Button) findViewById(R.id.loginBT);
        registrationBT = (Button) findViewById(R.id.registrationBT);

        loginBT.setOnClickListener(this);
        registrationBT.setOnClickListener(this);
    }
    /*android REST web service*//*android REST web service*//*android REST web service*//*android REST web service*//*android REST web service*//*android REST web service*/
    //create android database app chat app

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginBT:
                Log.i(TAG, "debuggingtekst i stedet for intent-> home");


                /*/////////////////////////bare for test!///////////////////////////////*/
                Intent homeIntent = new Intent(Login.super.getBaseContext(), Home.class);
                startActivity(homeIntent);
                /*////////////////////////////////////////////////////////////////////*/

                //Sett inn kode
                break;

            case R.id.registrationBT:
                Log.i(TAG, "debuggingtekst i stedet for intent-> registrering");
                /*-------------TEST AV REGISTRERING---------------------*/
                Intent regIntent = new Intent(Login.super.getBaseContext(),Registration.class);
                startActivity(regIntent);
                break;

            default:
                break;

        }
    }

}
