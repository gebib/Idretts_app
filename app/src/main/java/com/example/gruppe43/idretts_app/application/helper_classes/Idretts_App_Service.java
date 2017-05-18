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
import com.example.gruppe43.idretts_app.application.model.ChatModel;
import com.example.gruppe43.idretts_app.application.model.ChatNotificationModel;
import com.example.gruppe43.idretts_app.application.model.PlayerPostsModel;
import com.example.gruppe43.idretts_app.application.model.UsersModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

//Idretts-app bachelor oppgave 2017
//Ole-Kristian Steiro, Tasmia Faruque, Gebi Beshir

public class Idretts_App_Service extends Service {
    public static boolean serviceRunning = true;
    private static int NOTIFICATION_ID = 1;
    private static int loggedInUser;

    //BakgrunnService klassen kan ha flere traader saa vi maa ha id for hver service traad.

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    //BakgrunnService klassen kan ha flere traader saa vi maa ha id for hver service traad.
    final class bgThreadKlasse implements Runnable {
        int serviceId;

        bgThreadKlasse(int serviceId) {
            this.serviceId = serviceId;
        }

        @Override
        public void run() {
            synchronized (this) {
                while (serviceRunning) {
                    try {
                        wait(2000);//Interval for sjekking i bakgrunn service 1000x = 1 sekund
                    } catch (InterruptedException e) {
                        Log.i("bg Servic", "wait interrupted!");
                    }
                    checkNumberOfNodesOnTrainerPost();
                }
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Thread thread = new Thread(new bgThreadKlasse(startId));
        thread.start();//start bakgrunn servisen i separat straad
        serviceRunning = true;
         System.out.println("////////////////////////// BG onStart");
        return START_STICKY;
    }

    @Override//kalles av android system hvis service stoppes av android
    public void onDestroy() {
        serviceRunning = false;
        System.out.println("////////////////////////// BG onDestroy");
    }

    //check for new trainer post then notify all users once per post.
    private void checkNumberOfNodesOnTrainerPost() {
        final DatabaseReference trainerPosts = FirebaseDatabase.getInstance().getReference().child("TrainerPosts");
        trainerPosts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    PrefferencesClass pc = new PrefferencesClass(getApplicationContext());

                    long locallySavedChildCount = pc.loadLocallySavedTrainerChildCount();
                    long checkedNewTrainerCHildCount = dataSnapshot.getChildrenCount();

                    String restore = pc.loadSharedPrefData("isAdmin");
                   // System.out.println("////////////////////////// newPost " + checkedNewTrainerCHildCount + " Local " + locallySavedChildCount);

                    if (checkedNewTrainerCHildCount > locallySavedChildCount && restore.equals("false")) {// will not notify the trainer himself.
                     //   System.out.println("//////////////////////////NOTIFY!");
                        notifyUser(getString(R.string.trainerPostNotifTitle), getString(R.string.trainerHasPostedNewPostText));
                        pc.saveUpdateOnTrainerChildCount(checkedNewTrainerCHildCount);// update local.
                    } else if (locallySavedChildCount > checkedNewTrainerCHildCount) {
                        pc.saveUpdateOnTrainerChildCount(checkedNewTrainerCHildCount);// update local.
                    }

                    trainerPosts.removeEventListener(this);
                    checkForNewMessageFromUser();
                }else{
                    trainerPosts.removeEventListener(this);
                    checkForNewMessageFromUser();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //check for new messages and notify user.
    private void checkForNewMessageFromUser() {
        System.out.println("////////////////////////// BG checkMESSAGE");
        final ArrayList<String> chatNotifDsToBeDeleted = new ArrayList<>();
        final DatabaseReference chatNotifications = FirebaseDatabase.getInstance().getReference().child("ChatNotifications");
        chatNotifications.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    FirebaseAuth fbAuth = FirebaseAuth.getInstance();
                    String currentUserId = fbAuth.getCurrentUser().getUid();

                    Iterable<DataSnapshot> chatNotifIterable = dataSnapshot.getChildren();
                    for (DataSnapshot chatNotifNodes : chatNotifIterable) {
                        ChatNotificationModel cnm = chatNotifNodes.getValue(ChatNotificationModel.class);
                        String notifNodeKey = chatNotifNodes.getKey();
                        String toUser = cnm.getToUserKey();
                        if (toUser.equals(currentUserId)) {// t hen the notification is for t his user!
                            notifyUser("Message.", cnm.getSenderName() + " has sent you a new message!");
                            chatNotifDsToBeDeleted.add(notifNodeKey); // all this users notification nodes need to be deleted after the user is notified.
                        }
                    }
                    for (int i = 0; i < chatNotifDsToBeDeleted.size(); i++) {
                        chatNotifications.child(chatNotifDsToBeDeleted.get(i)).removeValue();
                    }
                    chatNotifications.removeEventListener(this);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void notifyUser(String title, String message) {
        //  if (!message.getUserId().equals("fromKey..blbl")) {
        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ball)
                .setContentTitle(title)
                .setContentText(message)
                .setOnlyAlertOnce(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        mBuilder.setAutoCancel(true);
        mBuilder.setLocalOnly(false);
        mNotificationManager.notify(NOTIFICATION_ID++, mBuilder.build());
    }
}
