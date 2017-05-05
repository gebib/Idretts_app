package com.example.gruppe43.idretts_app.application.view.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import com.example.gruppe43.idretts_app.application.model.UsersModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Team extends Fragment {

    private DatabaseReference fbDbRef;
    private DatabaseReference fbDbUsersRef;
    private DataBaseHelper databaseHelper;
    private FragmentActivityInterface mCallback;
    private PrefferencesClass prefs;
    private RecyclerView recyclerViewTeamRV;

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
        View view = inflater.inflate(R.layout.fragment_team,container,false);

        recyclerViewTeamRV = (RecyclerView) view.findViewById(R.id.recyclerViewTeam);

         fbDbRef = FirebaseDatabase.getInstance().getReference().child("PlayerPosts");
        fbDbUsersRef = FirebaseDatabase.getInstance().getReference().child("Users");


        FirebaseRecyclerAdapter<UsersModel, Team.TeamViewHolder> teamAdapter = new FirebaseRecyclerAdapter<UsersModel, Team.TeamViewHolder>(
                UsersModel.class,
                R.layout.team_items,
                Team.TeamViewHolder.class,
                fbDbUsersRef
        ) {
            @Override
            protected void populateViewHolder(TeamViewHolder viewHolder, UsersModel model, final int position) {

                viewHolder.setProfileImage(mCallback.getContext(), model.getImage());
                String nameAndDate = model.getFirstName() + " " + model.getLastName();
                viewHolder.setUserNameLastName(nameAndDate);
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseHelper = new DataBaseHelper(mCallback.getContext());
                        String userId= getRef(position).getKey();//you will get which user you clicked on uId?
                        System.out.println("///////////////////////"+userId);
                    }
                });
            }
        };

        recyclerViewTeamRV.setAdapter(teamAdapter);
        recyclerViewTeamRV.setLayoutManager(new GridLayoutManager(getActivity(),4));
        return view;
    }

    public static class TeamViewHolder extends RecyclerView.ViewHolder {
        View mView;
        ImageView profileImage;
        TextView userNameLastName;

        public TeamViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setProfileImage(Context ctx, String image) {
            profileImage = (ImageView) mView.findViewById(R.id.userImageIV);
            // Picasso.with(ctx).load(image).into(profileImage); TODO
            profileImage.setImageResource(R.drawable.pph_s);//TEMP
        }

        public void setUserNameLastName(String firstLastName){
            userNameLastName = (TextView) mView.findViewById(R.id.userFirstAndLastName);
            userNameLastName.setText(firstLastName);
        }
    }

}
