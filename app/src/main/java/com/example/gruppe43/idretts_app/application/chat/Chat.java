package com.example.gruppe43.idretts_app.application.chat;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.helper_classes.PrefferencesClass;
import com.example.gruppe43.idretts_app.application.interfaces.FragmentActivityInterface;
import com.example.gruppe43.idretts_app.application.model.ChatModel;
import com.example.gruppe43.idretts_app.application.model.UsersModel;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

//Idretts-app bachelor oppgave 2017
//Ole-Kristian Steiro, Tasmia Faruque, Gebi Beshir

public class Chat extends Fragment {
    private FragmentActivityInterface mCallback;
    private Spinner allUsersSpinnerInChat;
    private ListView chatListView;
    private EditText inputArea;
    private ImageButton chatSendButton;
    protected String nowDate, nowMonth, nowYear, nowHour, nowMinute;
    DatabaseReference sessionPointer;

    private static ArrayList<String> playerNamesChat;
    private static ArrayList<String> playerIdsChat;
    private String playerIdSelectedChat;
    private boolean isAdpterSet;
    private boolean hasRunOnce;


    public void setPlayerIdsChat(ArrayList<String> playerIdsChat) {
        this.playerIdsChat = playerIdsChat;
    }

    public void setPlayerNamesChat(ArrayList<String> playerNamesChat) {
        this.playerNamesChat = playerNamesChat;
    }

    public Chat() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (FragmentActivityInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement IFragmentToActivity");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mCallback.getFab().hide();
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        isAdpterSet = false;
        hasRunOnce = false;
        allUsersSpinnerInChat = (Spinner) view.findViewById(R.id.allUsersSpinner);
        chatListView = (ListView) view.findViewById(R.id.list);
        inputArea = (EditText) view.findViewById(R.id.inputArea);
        chatSendButton = (ImageButton) view.findViewById(R.id.chatSendButton);

        chatSendButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        chatSendButton.setBackgroundResource(R.drawable.round_corners_defused_blue);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        if (playerIdSelectedChat.equals("")) {
                            Toast.makeText(mCallback.getContext(), R.string.campRecordsSelectUserToast, Toast.LENGTH_SHORT).show();
                        } else {
                            if (!inputArea.getText().toString().trim().equals("") && sessionPointer != null) {
                                postMessage();
                            } else if (sessionPointer == null) {
                                selectChatSessionIfExist();
                            } else {
                                Toast.makeText(mCallback.getContext(), R.string.typeAmessageToSend, Toast.LENGTH_SHORT).show();
                            }
                        }
                        chatSendButton.setBackgroundResource(R.drawable.round_corners_black);
                        break;
                    default:
                        chatSendButton.setBackgroundResource(R.drawable.round_corners_black);
                }

                return true;
            }
        });

        ArrayAdapter<String> playerNamesSpinner = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, playerNamesChat);
        playerNamesSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        allUsersSpinnerInChat.setAdapter(playerNamesSpinner);

        allUsersSpinnerInChat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                playerIdSelectedChat = playerIdsChat.get(pos);
                if (!playerIdSelectedChat.equals("")) {
                    selectChatSessionIfExist();
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return view;
    }

    //get the current timeDate
    public void getCurrentDate() {
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        nowDate = today.monthDay + "";
        nowMonth = today.month + 1 + "";
        nowYear = today.year + "";
        nowHour = today.hour + "";
        nowMinute = today.minute + "";
    }


    //select appropriate adapter on user selection changes.
    private void selectChatSessionIfExist() {
        FirebaseAuth fbAuth = FirebaseAuth.getInstance();
        final String fromUserKey = fbAuth.getCurrentUser().getUid();
        String toUserKey = playerIdSelectedChat;

        if (playerIdSelectedChat.equals("")) {
            Toast.makeText(mCallback.getContext(), R.string.campRecordsSelectUserToast, Toast.LENGTH_SHORT).show();
            return;
        }

        //check if any of these exist first and use the right one
        final DatabaseReference particularSessionPointerA = FirebaseDatabase.getInstance().getReference().child("Chat").child(fromUserKey + "_" + toUserKey);
        final DatabaseReference particularSessionPointerB = FirebaseDatabase.getInstance().getReference().child("Chat").child(toUserKey + "_" + fromUserKey);

        particularSessionPointerA.addListenerForSingleValueEvent(new ValueEventListener() { // check if YOU vs THEPORSON session exist FIRST PRIORITY
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // session does exist where this user has created with the user this user is trying to talk to.
                    setListAdapterOnChatSessionChange(particularSessionPointerA);
                    sessionPointer = particularSessionPointerA;

                } else if (!dataSnapshot.exists()) {
                    particularSessionPointerB.addListenerForSingleValueEvent(new ValueEventListener() { // check if THEPERSON vs YOU session exist SECOND PRIORITY
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                //session does exist where its made by the user this user is trying to talk to.
                                setListAdapterOnChatSessionChange(particularSessionPointerB);
                                sessionPointer = particularSessionPointerB;

                            } else if (!dataSnapshot.exists()) {
                                //session has never been created between this user and the user this user is trying to comunicate to, >>create new session!
                                setListAdapterOnChatSessionChange(particularSessionPointerA);
                                sessionPointer = particularSessionPointerA;

                            } else {//first time when the chat fragment loads, selected user to chat to is null!!!
                                sessionPointer = null;
                                chatListView.setAdapter(null);//if no session exist reset the list view adapter.
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    //given that session is identified, sender/receiver selected, post message to destination session!.
    private void postMessage() {
        final String[] senderName = new PrefferencesClass(mCallback.getContext()).loadUserName();
        final String senderFullName = senderName[0] + " " + senderName[1];
        FirebaseAuth fbAuth = FirebaseAuth.getInstance();
        final String fromUserKey = fbAuth.getCurrentUser().getUid();

        try {
            getCurrentDate();
            sessionPointer.push();
            DatabaseReference chatNode = sessionPointer.push();
            chatNode.child("senderName").setValue(senderFullName);
            chatNode.child("message").setValue(inputArea.getText().toString().trim());
            chatNode.child("date").setValue(nowDate + "." + nowMonth + "." + nowYear + " " + nowHour + ":" + nowMinute);
            chatNode.child("userKey").setValue(fromUserKey);
            inputArea.setText("");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    //load the chat sestion where this player is right or left child. if session exist
    private void setListAdapterOnChatSessionChange(DatabaseReference sessionRef) {
       FirebaseListAdapter<ChatModel> adapter = new MessageAdapter(mCallback.getContext(), ChatModel.class, R.layout.item_in_message, sessionRef);
        chatListView.setAdapter(adapter);

    }
}


































