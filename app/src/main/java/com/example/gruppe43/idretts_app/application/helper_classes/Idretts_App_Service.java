package com.example.gruppe43.idretts_app.application.helper_classes;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.view.main.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//Idretts-app bachelor oppgave 2017
//Ole-Kristian Steiro, Tasmia Faruque, Gebi Beshir

public class Idretts_App_Service extends Service {
   public static boolean serviceRunning = true;
    static int NOTIFICATION_ID = 1;
    static boolean hasNotifiedOnce = true;
    //BakgrunnService klassen kan ha flere traader saa vi maa ha id for hver service traad.

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        serviceRunning = true;
       // Thread thread = new Thread(new bgThreadKlasse(startId));
       // thread.start();//start bakgrunn servisen i separat straad
        System.out.println("////////////////////////// BG STARTED");
        DatabaseReference trainerPosts = FirebaseDatabase.getInstance().getReference().child("TrainerPosts");
        trainerPosts.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()){
                   if(!MainActivity.isTrainerSignedIn){ // trainer does not need to have the notification.
                       hasNotifiedOnce = false;
                       notifyUser(getString(R.string.coachNotif),getString(R.string.coackckak));
                   }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return START_STICKY;
    }

    @Override//kalles av android system hvis service stoppes av android
    public void onDestroy() {
        serviceRunning = false;
        System.out.println("////////////////////////// BG stoped");
    }

    private void notifyUser(String title, String message){
        System.out.println("////////////////////////// tasking...");
        if (!hasNotifiedOnce) {
            hasNotifiedOnce = true;
            NOTIFICATION_ID++;
            //  if (!message.getUserId().equals("fromKey..blbl")) {
            NotificationManager mNotificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ball)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setOnlyAlertOnce(true)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
            mBuilder.setAutoCancel(true);
            mBuilder.setLocalOnly(false);
            mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        }

    }
}
