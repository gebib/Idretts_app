package com.example.gruppe43.idretts_app.application.view.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.controll.Authentication;
import com.example.gruppe43.idretts_app.application.controll.DataBaseHelper;
import com.example.gruppe43.idretts_app.application.interfaces.FragmentActivityInterface;
import com.example.gruppe43.idretts_app.application.view.fragments.FullActivityInfo;
import com.example.gruppe43.idretts_app.application.view.fragments.Login;
import com.example.gruppe43.idretts_app.application.view.fragments.Messages;
import com.example.gruppe43.idretts_app.application.view.fragments.NewActivityRegistration;
import com.example.gruppe43.idretts_app.application.view.fragments.Player;
import com.example.gruppe43.idretts_app.application.view.fragments.ProfileView;
import com.example.gruppe43.idretts_app.application.view.fragments.Tabs;
import com.example.gruppe43.idretts_app.application.view.fragments.Team;
import com.example.gruppe43.idretts_app.application.view.fragments.Trainer;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentActivityInterface {
    private DrawerLayout mDrawerLayout;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private Boolean trainerIsShowing;
    private Boolean playerIsShowing;
    private FloatingActionButton fab;
    public static Boolean isPlayerSignedIn;
    public static Boolean isTrainerSignedIn;
    private Boolean editActivityIsShowing;
    protected static Context mainContext;
    private String ADMINISTRATION_ID = 1234 + "";//this is used to identify the cuach!
    private FirebaseAuth.AuthStateListener mAuthListner;
    private FirebaseAuth mAuth;
    private Authentication authClass;
    private DataBaseHelper databaseHelper;
    public static boolean onRegisterPage;
    private boolean loggingIn;
    protected static boolean isRegiteringActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentManager = getSupportFragmentManager();
        mainContext = this;
        authClass = new Authentication();

        mAuth = FirebaseAuth.getInstance();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
        fab.setRippleColor(Color.GREEN);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!onRegisterPage) {
                    showFragmentOfGivenCondition();
                    onRegisterPage = true;
                } else if (onRegisterPage) {
                    NewActivityRegistration nar = new NewActivityRegistration();
                    nar.registerActivity();
                    onRegisterPage = false;
                }
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
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

         SharedPreferences prefs = getSharedPreferences("sAdPlayValues", MODE_PRIVATE);
        String restore = prefs.getString("isAdmin", "No name defined");
        if (restore.equals("false")) {
            isPlayerSignedIn = true;
            isTrainerSignedIn = false;
            actionBar.show();
        } else if (restore.equals("true")) {
            isPlayerSignedIn = false;
            isTrainerSignedIn = true;
            actionBar.show();
        }
        // listen for if user has logged in
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if ((firebaseAuth.getCurrentUser() == null)) {
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    mFragmentTransaction.replace(R.id.containerView, new Login()).commit();

                } else if (isTrainerSignedIn != null || isPlayerSignedIn != null) {
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    mFragmentTransaction.replace(R.id.containerView, new Tabs()).commit();
                    loggingIn = false;
                    if (isPlayerSignedIn) {
                        initAfterLogin("notAdmin");
                    } else if (isTrainerSignedIn) {
                        initAfterLogin("admin");
                    }
                }

            }
        };
    }

    //navigate to appropriate fragment after registration.
    @Override
    public void showFragmentOfGivenCondition() {
        if (isTrainerSignedIn && trainerIsShowing) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.addToBackStack("");
            fragmentTransaction.replace(R.id.containerView, new NewActivityRegistration()).commit();
            currentShowingFragment("editActivity");
        } else if (isPlayerSignedIn && playerIsShowing) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.addToBackStack("");
            fragmentTransaction.replace(R.id.containerView, new NewActivityRegistration()).commit();
            currentShowingFragment("editActivity");
        } else if (isPlayerSignedIn && editActivityIsShowing) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentManager.popBackStack();
            fragmentTransaction.replace(R.id.containerView, new Tabs()).commit();
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    Tabs.viewPager.setCurrentItem(1);
                }
            });
            currentShowingFragment("player");
        } else if (isTrainerSignedIn && editActivityIsShowing) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentManager.popBackStack();
            fragmentTransaction.replace(R.id.containerView, new Tabs()).commit();
            currentShowingFragment("trainer");
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);//listen for if the user has signed in already
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
        // initPreLogin();//hide menus
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.toolbar_home) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentManager.popBackStack();
            fragmentTransaction.replace(R.id.containerView, new Tabs()).commit();
            return true;
        }
        if (id == R.id.toolbar_messages) {
            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.addToBackStack("messages");
            xfragmentTransaction.replace(R.id.containerView, new Messages()).commit();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
    }


    /*//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.nav_profile) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.addToBackStack("");
            fragmentTransaction.replace(R.id.containerView, new ProfileView()).commit();
            currentShowingFragment("");
        }
        if (menuItem.getItemId() == R.id.nav_messages) {
            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.addToBackStack("");
            xfragmentTransaction.replace(R.id.containerView, new Messages()).commit();
            //currentShowingFragment("");
        }
        if (menuItem.getItemId() == R.id.nav_trainer) {
            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.addToBackStack("");
            xfragmentTransaction.replace(R.id.containerView, new Trainer()).commit();
            // currentShowingFragment("trainer");
        }
        if (menuItem.getItemId() == R.id.navn_player) {
            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.addToBackStack("");
            xfragmentTransaction.replace(R.id.containerView, new Player()).commit();
            // currentShowingFragment("player");
        }
        if (menuItem.getItemId() == R.id.nav_Team) {
            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.addToBackStack("");
            xfragmentTransaction.replace(R.id.containerView, new Team()).commit();
            //currentShowingFragment("");
        }
        if (menuItem.getItemId() == R.id.nav_exit) {
            onSignOut();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.containerView, new Login()).commit();
        }

                /*MIDLERTIDIG navigasjon///////////////////////////////////////////////////////*/

        if (menuItem.getItemId() == R.id.activityInformationPage) {
            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.addToBackStack("");
            xfragmentTransaction.replace(R.id.containerView, new FullActivityInfo()).commit();
            //currentShowingFragment("");
        }
        if (menuItem.getItemId() == R.id.newActivityRegistrationPage) {
            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.addToBackStack("");
            xfragmentTransaction.replace(R.id.containerView, new NewActivityRegistration()).commit();
            //currentShowingFragment("");
        }

        if (menuItem.getItemId() == R.id.messegesEditgPage) {
            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.addToBackStack("");
            xfragmentTransaction.replace(R.id.containerView, new Messages()).commit(); /* dette er fragment siden for all melding osv..*/
            //currentShowingFragment("");
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
    @Override
    public void currentShowingFragment(String tabId) {
        if (tabId.equals("trainer")) {
            trainerIsShowing = true;
            playerIsShowing = false;
            editActivityIsShowing = false;
            fab.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.primary_darker, null)));
        } else if (tabId.equals("player")) {
            trainerIsShowing = false;
            playerIsShowing = true;
            editActivityIsShowing = false;
            fab.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.jet, null)));
        } else if (tabId.equals("editActivity")) {
            trainerIsShowing = false;
            playerIsShowing = false;
            editActivityIsShowing = true;
        } else {
            trainerIsShowing = false;
            playerIsShowing = false;
            editActivityIsShowing = false;
        }
        if (isTrainerSignedIn != null && isPlayerSignedIn != null) {
            updatesWhileSwiping();//update states
        }
    }

    //set fragment transactions/navigation
    public void replaceFragmentWith(Fragment fragmentClass, String from) {
        if (from.equals("login")) {
            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.replace(R.id.containerView, fragmentClass).commit();
        } else if (from.equals("reg")) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.containerView, fragmentClass).commit();
            dumpBackStack();
        } else {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.addToBackStack("ok");
            fragmentTransaction.replace(R.id.containerView, fragmentClass).commit();
        }
    }

    //update changes
    public void updatesWhileSwiping() {
        if (trainerIsShowing && isTrainerSignedIn) {
            fab.setImageResource(R.drawable.add24dp);
            fab.show();
            //Toast.makeText(this, "hide", Toast.LENGTH_LONG).show();
        } else if (playerIsShowing && isPlayerSignedIn) {
            fab.setImageResource(R.drawable.add24dp);
            fab.show();
        } else if (editActivityIsShowing) {
            fab.show();
            fab.setImageResource(R.drawable.check24dp);
            isRegiteringActivity = true;
        } else {
            fab.hide();
        }
        //if no one is signed in dont show the actionbar menu.
    }

    //init things that should be initialyzed after a successful sign in.
    @Override
    public void initAfterLogin(String userType) {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        if (userType.equals("admin")) {
            isTrainerSignedIn = true;
            isPlayerSignedIn = false;
            SharedPreferences.Editor editor = getSharedPreferences("sAdPlayValues", MODE_PRIVATE).edit();
            editor.putString("isAdmin", "true");
            editor.commit();
        } else {
            isTrainerSignedIn = false;
            isPlayerSignedIn = true;
            SharedPreferences.Editor editor = getSharedPreferences("sAdPlayValues", MODE_PRIVATE).edit();
            editor.putString("isAdmin", "false");
            editor.commit();
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();
        currentShowingFragment("trainer");
        onRegisterPage = false;
        replaceFragmentWith(new Tabs(), "login"); //load the homepage
    }

    //set state sign out!
    @Override
    public void onSignOut() {
        if (mAuth.getCurrentUser() != null && !loggingIn) {
            mAuth.signOut();
            loggingIn = false;

            SharedPreferences.Editor editor = getSharedPreferences("sAdPlayValues", MODE_PRIVATE).edit();
            editor.putString("isAdmin", "none");
            editor.commit();
            onRegisterPage = false;
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        fab.hide();
        dumpBackStack();
    }

    @Override
    public void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }


    //requiere first time admin pass
    @Override
    public void requIreAdminPass() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(" Administrator");
        final EditText input = new EditText(this);

        input.setBackgroundColor(Color.WHITE);
        input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        input.setHint("Admin password");
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                if (value.equals(ADMINISTRATION_ID)) {
                    initAfterLogin("admin");
                    databaseHelper = new DataBaseHelper();
                    databaseHelper.setIsAdmin();//only first time.

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(mainContext);
                    builder1.setTitle(getString(R.string.welcomeCoachTittle));
                    builder1.setMessage(R.string.wecomeCoachText);
                    builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //ingen action.
                        }
                    });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                    initAfterLogin("admin");
                } else {
                    Toast.makeText(MainActivity.this, R.string.adminCodMismatched, Toast.LENGTH_SHORT).show();
                    requIreAdminPass();
                }
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                authClass.deleteFirstUserNotValid();
                Toast.makeText(MainActivity.this, R.string.administratorNotSet, Toast.LENGTH_SHORT).show();
                replaceFragmentWith(new Login(), "");
                onSignOut();
            }
        });
        alert.show();
    }

    //dump fragments from backstack
    private void dumpBackStack() {
        for (int i = 0; i < mFragmentManager.getBackStackEntryCount(); ++i) {
            mFragmentManager.popBackStack();
        }
    }
}