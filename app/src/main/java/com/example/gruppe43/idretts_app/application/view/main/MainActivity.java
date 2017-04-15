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
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.fragment_interfaces.FragmentActivityInterface;
import com.example.gruppe43.idretts_app.application.view.fragments.FirstPage;
import com.example.gruppe43.idretts_app.application.view.fragments.FullActivityInfo;
import com.example.gruppe43.idretts_app.application.view.fragments.ListOfConversations;
import com.example.gruppe43.idretts_app.application.view.fragments.Login;
import com.example.gruppe43.idretts_app.application.view.fragments.Messages;
import com.example.gruppe43.idretts_app.application.view.fragments.NewActivityRegistration;
import com.example.gruppe43.idretts_app.application.view.fragments.Player;
import com.example.gruppe43.idretts_app.application.view.fragments.ProfileView;
import com.example.gruppe43.idretts_app.application.view.fragments.Registration;
import com.example.gruppe43.idretts_app.application.view.fragments.ShowAllPlayers;
import com.example.gruppe43.idretts_app.application.view.fragments.Tabs;
import com.example.gruppe43.idretts_app.application.view.fragments.Team;
import com.example.gruppe43.idretts_app.application.view.fragments.Trainer;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentActivityInterface {
    private DrawerLayout mDrawerLayout;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private MainActivity ma;
    Boolean trainerIsShowing;
    Boolean playerIsShowing;
    Boolean teamIsShowing;
    FloatingActionButton fab;
    boolean isPlayerSignedIn;
    boolean isTrainerSignedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new FirstPage()).commit();
        ma = this;

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ma, "add", Toast.LENGTH_LONG).show();
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

        //default states assignments
        fab.hide();
        fab.setRippleColor(Color.GREEN);

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
            xfragmentTransaction.replace(R.id.containerView, new ListOfConversations()).commit();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.nav_home) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.containerView, new Tabs()).commit();
        }
        if (menuItem.getItemId() == R.id.nav_messages) {
            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.replace(R.id.containerView, new Messages()).commit();
        }
        if (menuItem.getItemId() == R.id.nav_trainer) {
            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.replace(R.id.containerView, new Trainer()).commit();
        }
        if (menuItem.getItemId() == R.id.navn_player) {
            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.replace(R.id.containerView, new Player()).commit();
        }
        if (menuItem.getItemId() == R.id.nav_Team) {
            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.replace(R.id.containerView, new Team()).commit();
        }
        if (menuItem.getItemId() == R.id.nav_exit) {
            System.exit(1);
        }

                /*MIDLERTIDIG navigasjon///////////////////////////////////////////////////////*/
        if (menuItem.getItemId() == R.id.firstpage) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.containerView, new FirstPage()).commit();
        }
        if (menuItem.getItemId() == R.id.logginPage) {
            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.replace(R.id.containerView, new Login()).commit();
        }
        if (menuItem.getItemId() == R.id.registrationPage) {
            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.replace(R.id.containerView, new Registration()).commit();
        }
        if (menuItem.getItemId() == R.id.homePage) {
            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.replace(R.id.containerView, new Tabs()).commit();
        }
        if (menuItem.getItemId() == R.id.profilePage) {
            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.replace(R.id.containerView, new ProfileView()).commit();
        }
        if (menuItem.getItemId() == R.id.activityInformationPage) {
            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.replace(R.id.containerView, new FullActivityInfo()).commit();
        }
        if (menuItem.getItemId() == R.id.newActivityRegistrationPage) {
            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.replace(R.id.containerView, new NewActivityRegistration()).commit();
        }
        if (menuItem.getItemId() == R.id.showPlayersPage) {
            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.replace(R.id.containerView, new ShowAllPlayers()).commit();
        }
        if (menuItem.getItemId() == R.id.showTeamPage) {
            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.replace(R.id.containerView, new Team()).commit();
        }
        if (menuItem.getItemId() == R.id.messegesListPage) {
            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.replace(R.id.containerView, new ListOfConversations()).commit(); /* denne fragmenten viser liste av foretatte
                    tidligere meldinger som som står lagret, når man trykker på en .. skal den åpne for melding redigerings sisde , kan scrolles tilbake for tidligere meldinger med denne personen*/
        }
        if (menuItem.getItemId() == R.id.messegesEditgPage) {
            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.replace(R.id.containerView, new Messages()).commit(); /* denne fragmenten brukes for selve meldingene, som i melding siden vårt i prototypen.*/
        }

        if (menuItem.getItemId() == R.id.nav_exit) {
            System.exit(1);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;//returning true keeps the item selected, selected.
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    //set current fragment
    public void currentShowingFragment(String tabId) {
        if (tabId.equals("trainer")) {
            trainerIsShowing = true;
            playerIsShowing = false;
            teamIsShowing = false;
            fab.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.primary_darker, null)));
        } else if (tabId.equals("player")) {
            trainerIsShowing = false;
            playerIsShowing = true;
            teamIsShowing = false;
            fab.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.jet, null)));
        } else if (tabId.equals("team")) {
            trainerIsShowing = false;
            playerIsShowing = false;
            teamIsShowing = true;
        }
        updatesWhileSwiping();//update states
    }

    //set fragment transactions/navigation
    public void replaceFragmentWith(Fragment fragmentClass) {
        FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
        xfragmentTransaction.replace(R.id.containerView, fragmentClass).commit();
    }

    public void updatesWhileSwiping() {
        if (teamIsShowing) {
            fab.hide();
            //Toast.makeText(this, "hide", Toast.LENGTH_LONG).show();
        } else {
            fab.show();
        }
    }
}