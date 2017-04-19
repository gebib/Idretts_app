package com.example.gruppe43.idretts_app.application.view.main;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.fragment_interfaces.FragmentActivityInterface;
import com.example.gruppe43.idretts_app.application.view.fragments.FullActivityInfo;
import com.example.gruppe43.idretts_app.application.view.fragments.Login;
import com.example.gruppe43.idretts_app.application.view.fragments.Messages;
import com.example.gruppe43.idretts_app.application.view.fragments.NewActivityRegistration;
import com.example.gruppe43.idretts_app.application.view.fragments.Player;
import com.example.gruppe43.idretts_app.application.view.fragments.ProfileView;
import com.example.gruppe43.idretts_app.application.view.fragments.Registration;
import com.example.gruppe43.idretts_app.application.view.fragments.Tabs;
import com.example.gruppe43.idretts_app.application.view.fragments.Team;
import com.example.gruppe43.idretts_app.application.view.fragments.Trainer;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentActivityInterface {
    private DrawerLayout mDrawerLayout;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private MainActivity ma;
    private Boolean trainerIsShowing;
    private Boolean playerIsShowing;
    private FloatingActionButton fab;
    private Boolean isPlayerSignedIn;
    private Boolean isTrainerSignedIn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new Login()).commit();
        ma = this;

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
        fab.setRippleColor(Color.GREEN);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ma, "add", Toast.LENGTH_LONG).show();
                updatesWhileSwiping();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        NavigationView view = (NavigationView) findViewById(R.id.nav_view);
        view.setNavigationItemSelectedListener(this);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        /*////////////////////////FLAGS////////////////////////////////*/
        //default states assignments


        /*////////////////////////FLAGS////////////////////////////////*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        initPreLogin();//hide menus
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.toolbar_home) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.containerView, new Tabs()).commit();
            return true;
        }
        if (id == R.id.toolbar_messages) {
            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.replace(R.id.containerView, new Messages()).commit();
        }

        return super.onOptionsItemSelected(item);
    }


/*//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.nav_profile) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.containerView, new ProfileView()).commit();
            currentShowingFragment("");
        }
        if (menuItem.getItemId() == R.id.nav_messages) {
            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.replace(R.id.containerView, new Messages()).commit();
            currentShowingFragment("");
        }
        if (menuItem.getItemId() == R.id.nav_trainer) {
            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.replace(R.id.containerView, new Trainer()).commit();
            currentShowingFragment("trainer");
        }
        if (menuItem.getItemId() == R.id.navn_player) {
            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.replace(R.id.containerView, new Player()).commit();
            currentShowingFragment("player");
        }
        if (menuItem.getItemId() == R.id.nav_Team) {
            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.replace(R.id.containerView, new Team()).commit();
            currentShowingFragment("");
        }
        if (menuItem.getItemId() == R.id.nav_exit) {
            System.exit(1);
        }

                /*MIDLERTIDIG navigasjon///////////////////////////////////////////////////////*/

        if (menuItem.getItemId() == R.id.activityInformationPage) {
            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.replace(R.id.containerView, new FullActivityInfo()).commit();
            currentShowingFragment("");
        }
        if (menuItem.getItemId() == R.id.newActivityRegistrationPage) {
            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.replace(R.id.containerView, new NewActivityRegistration()).commit();
            currentShowingFragment("");
        }

        if (menuItem.getItemId() == R.id.messegesEditgPage) {
            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.replace(R.id.containerView, new Messages()).commit(); /* dette er fragment siden for all melding osv..*/
            currentShowingFragment("");
        }

        if (menuItem.getItemId() == R.id.nav_exit) {
            System.exit(1);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;//returning true keeps the item selected, selected.
    }
    /*////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    //set current fragment
    public void currentShowingFragment(String tabId) {
        if (tabId.equals("trainer")) {
            trainerIsShowing = true;
            playerIsShowing = false;
            fab.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.primary_darker, null)));
        } else if (tabId.equals("player")) {
            trainerIsShowing = false;
            playerIsShowing = true;
            fab.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.jet, null)));
        }else{
            trainerIsShowing = false;
            playerIsShowing = false;
        }
        updatesWhileSwiping();//update states
    }

    //set fragment transactions/navigation
    public void replaceFragmentWith(Fragment fragmentClass) {
        FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
        xfragmentTransaction.replace(R.id.containerView, fragmentClass).commit();
    }
    //update changes
    public void updatesWhileSwiping() {
        if (trainerIsShowing && isTrainerSignedIn) {
            fab.show();
            //Toast.makeText(this, "hide", Toast.LENGTH_LONG).show();
        } else if (playerIsShowing && isPlayerSignedIn) {
            fab.show();
        } else {
            fab.hide();
        }
        //if no one is signed in dont show the actionbar menu.
    }

    //init things before login
    public void initPreLogin(){
        isPlayerSignedIn = false;
        isTrainerSignedIn = false;
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    }
    //init things that should be initialyzed after a successful sign in.
    public void initAfterLogin(Boolean isPlayerSignedIn, Boolean isTrainerSignedIn){
        this.isPlayerSignedIn = isPlayerSignedIn;
        this.isTrainerSignedIn = isTrainerSignedIn;
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

    }

    //init things on logging out or exiting the application!
    public void initOnLoggout(){

    }
}