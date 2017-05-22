package com.example.gruppe43.idretts_app.application.chat;

/**
 * Created by gebi9 on 14-May-17.
 */


import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.model.ChatModel;
import com.example.gruppe43.idretts_app.application.view.main.MainActivity;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

//Idretts-app bachelor oppgave 2017
//Ole-Kristian Steiro, Tasmia Faruque, Gebi Beshir
public class MessageAdapter extends FirebaseListAdapter<ChatModel> {

    private MainActivity activity;
    public MessageAdapter(MainActivity activity, Class<ChatModel> modelClass, int modelLayout, DatabaseReference ref) {
        super(activity, modelClass, modelLayout, ref);
        this.activity = activity;
    }

    @Override
    protected void populateView(View v, ChatModel model, int position) {
        try {
            TextView messageText = (TextView) v.findViewById(R.id.message_text);
            TextView messageUser = (TextView) v.findViewById(R.id.message_user_name);
            TextView messageTime = (TextView) v.findViewById(R.id.message_time);

            messageText.setText(model.getMessage());
            messageUser.setText(model.getSenderName());
            messageTime.setText(model.getDate());
        } catch (NullPointerException e) {
            System.out.println("populate View npe!");
        }
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        ChatModel chatMessage = null;
        String loggedInUserId = null;
        String chatMessageUserId = null;
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            chatMessage = getItem(position);
            loggedInUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            chatMessageUserId = chatMessage.getUserKey();
        }
        if(chatMessageUserId != null){
            if (chatMessageUserId.equals(loggedInUserId)) {
                view = activity.getLayoutInflater().inflate(R.layout.item_out_message, viewGroup, false);
            } else {
                view = activity.getLayoutInflater().inflate(R.layout.item_in_message, viewGroup, false);

            }
            populateView(view, chatMessage, position);
        }
        return view;
    }

    @Override
    public int getViewTypeCount() {
        // return the total number of view types. this value should never change
        // at runtime
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        // return a value between 0 and (getViewTypeCount - 1)
        return position % 2;
    }
}