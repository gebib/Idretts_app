package com.example.gruppe43.idretts_app.application.view.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;


import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.helper_classes.AppCommonResources;
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

public class MainActivity extends AppCommonResources{
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeActivity = this;// for context refference

        /**
         *Setup the DrawerLayout and NavigationView
         */

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.activity_main) ;

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView,new FirstPage()).commit();
        /**
         * Setup click events on the Navigation View Items.
         */

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();
                if (menuItem.getItemId() == R.id.nav_home) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView,new Tabs()).commit();
                }
                if (menuItem.getItemId() == R.id.nav_messages) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new Messages()).commit();
                }
                if (menuItem.getItemId() == R.id.nav_trainer) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new Trainer()).commit();
                }
                if (menuItem.getItemId() == R.id.navn_player) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new Player()).commit();
                }
                if (menuItem.getItemId() == R.id.nav_Team) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new Team()).commit();
                }
                if (menuItem.getItemId() == R.id.nav_exit) {
                     System.exit(1);
                }

                /*MIDLERTIDIG navigasjon///////////////////////////////////////////////////////*/
                if (menuItem.getItemId() == R.id.firstpage) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView,new FirstPage()).commit();
                }
                if (menuItem.getItemId() == R.id.logginPage) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new Login()).commit();
                }
                if (menuItem.getItemId() == R.id.registrationPage) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new Registration()).commit();
                }
                if (menuItem.getItemId() == R.id.homePage) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new Tabs()).commit();
                }
                if (menuItem.getItemId() == R.id.profilePage) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new ProfileView()).commit();
                }
                if (menuItem.getItemId() == R.id.activityInformationPage) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new FullActivityInfo()).commit();
                }
                if (menuItem.getItemId() == R.id.newActivityRegistrationPage) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new NewActivityRegistration()).commit();
                }
                if (menuItem.getItemId() == R.id.showPlayersPage) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new ShowAllPlayers()).commit();
                }
                if (menuItem.getItemId() == R.id.showTeamPage) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new Team()).commit();
                }
                if (menuItem.getItemId() == R.id.messegesListPage) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new ListOfConversations()).commit(); /* denne fragmenten viser liste av foretatte
                    tidligere meldinger som som står lagret, når man trykker på en .. skal den åpne for melding redigerings sisde , kan scrolles tilbake for tidligere meldinger med denne personen*/
                }
                if (menuItem.getItemId() == R.id.messegesEditgPage) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new Messages()).commit(); /* denne fragmenten brukes for selve meldingene, som i melding siden vårt i prototypen.*/
                }

                if (menuItem.getItemId() == R.id.nav_exit) {
                    System.exit(1);
                }
                 /*MIDLERTIDIG navigasjon////////////////////////////////////////////////////////*/
                return false;
            }
        });

        /**
         * Setup Drawer Toggle of the Toolbar
         */

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,
                R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

    }
}