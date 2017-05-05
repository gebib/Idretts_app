package com.example.gruppe43.idretts_app.application.view.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.ParseException;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.controll.DataBaseHelper;
import com.example.gruppe43.idretts_app.application.helper_classes.PrefferencesClass;
import com.example.gruppe43.idretts_app.application.interfaces.FragmentActivityInterface;
import com.example.gruppe43.idretts_app.application.model.PlayerModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Player extends Fragment {
    private DatabaseReference fbDbRef;
    private DatabaseReference fbDbUsersRef;
    private DataBaseHelper databaseHelper;
    private FragmentActivityInterface mCallback;
    private PrefferencesClass prefs;
    private RecyclerView recyclerViewPlayerRV;


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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player, container, false);

        recyclerViewPlayerRV = (RecyclerView) view.findViewById(R.id.recyclerViewPlayer);

        fbDbRef = FirebaseDatabase.getInstance().getReference().child("PlayerPosts");
        fbDbUsersRef = FirebaseDatabase.getInstance().getReference().child("Users");


        FirebaseRecyclerAdapter<PlayerModel, Player.PlayerViewHolder> playerAdapter = new FirebaseRecyclerAdapter<PlayerModel, Player.PlayerViewHolder>(
                PlayerModel.class,
                R.layout.player_items,
                Player.PlayerViewHolder.class,
                fbDbRef
        ) {
            @Override
            protected void populateViewHolder(Player.PlayerViewHolder viewHolder, PlayerModel model, final int position) {

                viewHolder.setProfileImage(mCallback.getContext(), model.getPlayerImage());
                viewHolder.setPlayerPostTitle(model.getTitle());
                viewHolder.setPlayerPostPlace(model.getPlace());
                viewHolder.setIntensity(model.getIntensity());
                String nameAndDate = model.getFirstNAme() + " " + model.getLastName() + " " + model.getDatePosted();
                viewHolder.setNameAndDate(nameAndDate);
                viewHolder.setPostedTime(model.getTimePosted());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseHelper = new DataBaseHelper(mCallback.getContext());
                        String postKey = getRef(position).getKey();
                        databaseHelper.checkDoesPlayerOwnThePost(postKey);
                    }
                });
            }
        };

        recyclerViewPlayerRV.setAdapter(playerAdapter);
        recyclerViewPlayerRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    public static class PlayerViewHolder extends RecyclerView.ViewHolder {
        View mView;
        ImageView profileImage;
        TextView ptTitle;
        TextView ptPlace;
        TextView ptIntensity;
        TextView postedTime;
        TextView nameAndDate;


        public PlayerViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setProfileImage(Context ctx, String image) {
            profileImage = (ImageView) mView.findViewById(R.id.profImageIV);
            // Picasso.with(ctx).load(image).into(profileImage); TODO
        }

        public void setPlayerPostTitle(String pTitle) {
            ptTitle = (TextView) mView.findViewById(R.id.playerPtTitle);
            ptTitle.setText(pTitle);
        }

        public void setPlayerPostPlace(String pPlace) {
            ptPlace = (TextView) mView.findViewById(R.id.playerPtPlace);
            ptPlace.setText(pPlace);
        }

        public void setIntensity(int pIntensity) {
            ptIntensity = (TextView) mView.findViewById(R.id.playerIntensityPersentage);
            if (pIntensity >= 0 && pIntensity <= 30) {
                ptIntensity.setTextColor(Color.rgb(31, 186, 0));
            }
            if (pIntensity >= 30 && pIntensity <= 60) {
                ptIntensity.setTextColor(Color.rgb(214, 196, 0));
            }
            if (pIntensity >= 60 && pIntensity <= 100) {
                ptIntensity.setTextColor(Color.rgb(255, 56, 56));
            }
            ptIntensity.setText(pIntensity + "%");
        }

        public void setPostedTime(String postTime) {
            postedTime = (TextView) mView.findViewById(R.id.playerActivityPostTime);
            postedTime.setText(postTime);
        }

        public void setNameAndDate(String nameDate) {
            nameAndDate = (TextView) mView.findViewById(R.id.nameAndDatePosted);
            nameAndDate.setText(nameDate);
        }
    }


}
