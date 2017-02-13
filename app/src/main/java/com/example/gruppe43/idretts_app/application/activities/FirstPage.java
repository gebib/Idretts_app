package com.example.gruppe43.idretts_app.application.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.helpers.AppActivity;

public class FirstPage extends AppActivity {
    Button coachButtonBT,playerButtonBT,familyButtoonBT;
    private static final String TAG = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

         coachButtonBT = (Button) findViewById(R.id.coachBT);
         playerButtonBT = (Button) findViewById(R.id.playerBT);
         familyButtoonBT = (Button) findViewById(R.id.familyBT);

        coachButtonBT.setOnClickListener(this);
        playerButtonBT.setOnClickListener(this);
        familyButtoonBT.setOnClickListener(this);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        isCoach = isPlayer = isFamily = false;
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
                case R.id.coachBT:
                    isCoach = true;
                    Toast.makeText(this,"coach OK!", Toast.LENGTH_LONG).show();
                    break;
                case R.id.playerBT:
                    isPlayer = true;
                    Toast.makeText(this,"player OK!", Toast.LENGTH_LONG).show();
                    break;
                case R.id.familyBT:
                    isFamily = true;
                    Toast.makeText(this,"family OK!", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
                if(isCoach || isPlayer || isFamily){
                   Intent loginIntent = new Intent(FirstPage.super.getBaseContext(), Login.class);
                    startActivity(loginIntent);

                }
        }

}
