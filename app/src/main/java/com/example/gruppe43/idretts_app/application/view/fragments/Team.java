package com.example.gruppe43.idretts_app.application.view.fragments;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.controll.DataBaseHelperA;
import com.example.gruppe43.idretts_app.application.controll.DataBaseHelperB;
import com.example.gruppe43.idretts_app.application.helper_classes.PrefferencesClass;
import com.example.gruppe43.idretts_app.application.interfaces.FragmentActivityInterface;
import com.example.gruppe43.idretts_app.application.model.UsersModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class Team extends Fragment {

    private DatabaseReference fbDbRef;
    private DatabaseReference fbDbUsersRef;
    private DataBaseHelperA databaseHelper;
    private FragmentActivityInterface mCallback;
    private PrefferencesClass prefs;
    private RecyclerView recyclerViewTeamRV;
    static boolean isForCheckAbsence;
    private ArrayList<String> absentPlayersIds;
    private ArrayList<View> correspondingAbsentPlayerView;
    private RelativeLayout adtoAbsentHolderRLayout;
    private Button button2AddToAbsentLists;



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
            isForCheckAbsence = false;
        absentPlayersIds = new ArrayList<>();
        correspondingAbsentPlayerView = new ArrayList<>();
        recyclerViewTeamRV = (RecyclerView) view.findViewById(R.id.recyclerViewTeam);

         fbDbRef = FirebaseDatabase.getInstance().getReference().child("PlayerPosts");
        fbDbUsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        adtoAbsentHolderRLayout = (RelativeLayout) view.findViewById(R.id.adtoAbsentHolderRLayout);
        button2AddToAbsentLists = (Button) view.findViewById(R.id.button2AddToAbsentLists);
        adtoAbsentHolderRLayout.setVisibility(view.GONE);

        button2AddToAbsentLists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelperB dbhb = new DataBaseHelperB(mCallback.getContext());
                dbhb.registerAbsence(absentPlayersIds,TrainerActivityRegistration.getSelectedActivityPostKey());
            }
        });
        try {
            Bundle bundle = this.getArguments();
            isForCheckAbsence = bundle.getBoolean("absenceCheck");
            if(isForCheckAbsence){
                adtoAbsentHolderRLayout.setVisibility(view.VISIBLE);
            }
        } catch (Exception e) {
            Log.e("Bundle::::", "isNull");
        }

        FirebaseRecyclerAdapter<UsersModel, Team.TeamViewHolder> teamAdapter = new FirebaseRecyclerAdapter<UsersModel, Team.TeamViewHolder>(
                UsersModel.class,
                R.layout.team_items,
                Team.TeamViewHolder.class,
                fbDbUsersRef
        ) {
            @Override
            protected void populateViewHolder(final TeamViewHolder viewHolder, UsersModel model, final int position) {
                if(isForCheckAbsence){
                    viewHolder.getAbsenceIndicatorImageView().setImageResource(R.drawable.check_black);
                    viewHolder.getView().setBackgroundColor(Color.rgb(0,163,0));
                }
                viewHolder.setProfileImage(mCallback.getContext(), model.getImage());
                String nameAndDate = model.getFirstName() + " " + model.getLastName();
                viewHolder.setUserNameLastName(nameAndDate);
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isForCheckAbsence && !correspondingAbsentPlayerView.contains(v)){
                            v.setBackgroundColor(Color.rgb(176,11,11));
                            viewHolder.getAbsenceIndicatorImageView().setImageResource(R.drawable.x);
                            correspondingAbsentPlayerView.add(v);
                            absentPlayersIds.add(getRef(position).getKey());//userIds
                        }else if(isForCheckAbsence && correspondingAbsentPlayerView.contains(v)){
                            v.setBackgroundColor(Color.rgb(0,163,0));
                            viewHolder.getAbsenceIndicatorImageView().setImageResource(R.drawable.check_black);
                            correspondingAbsentPlayerView.remove(v);
                            absentPlayersIds.remove(getRef(position).getKey());//userIds
                        }else{
                            DataBaseHelperB dbhb = new DataBaseHelperB(mCallback.getContext());
                            dbhb.getProfileViewDataForUser(getRef(position).getKey());
                        }
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
        ImageView absenceIndicatorLIght;
        public TeamViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            absenceIndicatorLIght = (ImageView) mView.findViewById(R.id.indicateBoxAbsence);
            absenceIndicatorLIght.setEnabled(false);
            //absenceIndicatorLIght.setEnabled(false);
            if(isForCheckAbsence){
                absenceIndicatorLIght.setVisibility(mView.VISIBLE);
            }else{
                absenceIndicatorLIght.setVisibility(mView.GONE);
            }

        }

        public ImageView getAbsenceIndicatorImageView() {
            return absenceIndicatorLIght;
        }

        public View getView() {
            return mView;
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
