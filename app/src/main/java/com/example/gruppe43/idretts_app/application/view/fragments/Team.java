package com.example.gruppe43.idretts_app.application.view.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.controll.DataBaseHelperA;
import com.example.gruppe43.idretts_app.application.controll.DataBaseHelperB;
import com.example.gruppe43.idretts_app.application.controll.DatabaseHelperC;
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
    private ArrayList<String> absentPlayersIdsToBeSent;
    private ArrayList<View> correspondingAbsentPlayerView;
    private RelativeLayout adtoAbsentHolderRLayout;
    private Button button2AddToAbsentLists;
    private static ArrayList<String> listOfPlayersAlreadMarkedAsAbsent;
    private static ArrayList<String> listOfTobeRemovedFromAbsent;


    private static ArrayList<String> allUserImages;
    private static ArrayList<String> allUserFirstNameAndLastNames;

    public static ArrayList<String> getAllUserImages() {
        return allUserImages;
    }

    public static void setAllUserImages(ArrayList<String> allUserImages) {
        Team.allUserImages = allUserImages;
    }

    public static ArrayList<String> getAllUserFirstNameAndLastNames() {
        return allUserFirstNameAndLastNames;
    }

    public static void setAllUserFirstNameAndLastNames(ArrayList<String> allUserFirstNameAndLastNames) {
        Team.allUserFirstNameAndLastNames = allUserFirstNameAndLastNames;
    }

    public static void setListOfPlayersAlreadMarkedAsAbsent(ArrayList<String> listOfPlayersAlreadMarkedAsAbsent) {
        Team.listOfPlayersAlreadMarkedAsAbsent = listOfPlayersAlreadMarkedAsAbsent;
    }

    public static ArrayList<String> getListOfTobeRemovedFromAbsent() {
        return listOfTobeRemovedFromAbsent;
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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_team, container, false);
        isForCheckAbsence = false;
        listOfTobeRemovedFromAbsent = new ArrayList<>();

        absentPlayersIdsToBeSent = new ArrayList<>();
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
                //TODO for rmoved abc REMOVE array
                //TODO for added abce ADD  arra
/*
                for (int i = 0; i < array.length; i++) {
                     =array[i];

                }*/

                DataBaseHelperB dbha = new DataBaseHelperB(mCallback.getContext());
                dbha.setAbsenceForArrayOfAbsentPlayerIds(absentPlayersIdsToBeSent, FullActivityInfo.getActivityType(), TrainerActivityRegistration.getSelectedActivityPostKey());
                mCallback.getmFragmentManager().popBackStack();
            }
        });
        try {
            Bundle bundle = this.getArguments();
            isForCheckAbsence = bundle.getBoolean("absenceCheck");
            if (isForCheckAbsence) {
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
                if (isForCheckAbsence) {
                    viewHolder.getAbsenceIndicatorImageView().setImageResource(R.drawable.check_black);
                    viewHolder.getView().setBackgroundColor(Color.rgb(0, 163, 0));
                    //TODO go through and reset absence set if any on this activity,,,,,,
                }
                viewHolder.setProfileImage(mCallback.getContext(), model.getImage());
                String nameAndDate = model.getFirstName() + " " + model.getLastName();
                viewHolder.setUserNameLastName(nameAndDate);
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    public View buttonParametre;
                    @Override
                    public void onClick(View v) {
                        this.buttonParametre = v;
                        //TODO if child already marked as absent, and clicked then DO SOMEthing
                        if (isForCheckAbsence && !correspondingAbsentPlayerView.contains(v)) {
                            if(listOfPlayersAlreadMarkedAsAbsent.contains(getRef(position).getKey())){

                                AlertDialog.Builder builder1 = new AlertDialog.Builder(mCallback.getContext());
                                builder1.setTitle(R.string.userAbsenceRegistrationTeamTitle);
                                builder1.setMessage(R.string.userAbsenceRegistrationTeamTextInfo);

                                builder1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        listOfTobeRemovedFromAbsent.add(getRef(position).getKey());
                                    }
                                });
                                builder1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        buttonParametre.setBackgroundColor(Color.rgb(176, 11, 11));
                                        viewHolder.getAbsenceIndicatorImageView().setImageResource(R.drawable.x);
                                    }
                                });
                                AlertDialog alert11 = builder1.create();
                                alert11.show();
                            }else if(isForCheckAbsence && !correspondingAbsentPlayerView.contains(v)){
                                v.setBackgroundColor(Color.rgb(176, 11, 11));
                                viewHolder.getAbsenceIndicatorImageView().setImageResource(R.drawable.x);
                                correspondingAbsentPlayerView.add(v);
                                absentPlayersIdsToBeSent.add(getRef(position).getKey());//userIds/////////TODO//////////////////////////////////////////problem!!!!!!

                                System.out.println("////////////////// add// "+"pos: "+position+" "+getRef(position).getKey());

                            }

                        } else if (isForCheckAbsence && correspondingAbsentPlayerView.contains(v)) {
                            System.out.println("////////////////// remove// "+"pos: "+position+" "+getRef(position).getKey());

                            v.setBackgroundColor(Color.rgb(0, 163, 0));
                            viewHolder.getAbsenceIndicatorImageView().setImageResource(R.drawable.check_black);
                            correspondingAbsentPlayerView.remove(v);
                            absentPlayersIdsToBeSent.remove(getRef(position).getKey());//userIds
                        } else {
                            DataBaseHelperB dbhb = new DataBaseHelperB(mCallback.getContext());
                            dbhb.getProfileViewDataForUser(getRef(position).getKey());
                            //TODO goto team with info
                            System.out.println("//////////////////// "+"pos: "+position+" "+getRef(position).getKey());
                        }
                    }
                });
            }
        };

        recyclerViewTeamRV.setAdapter(teamAdapter);
        recyclerViewTeamRV.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        return view;
    }

    public static class TeamViewHolder extends RecyclerView.ViewHolder {
        View mView;
        ImageView profileImage;
        TextView userNameLastName;
        ImageView absenceIndicatorIV;

        public TeamViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            absenceIndicatorIV = (ImageView) mView.findViewById(R.id.indicateBoxAbsence);
        }

        public ImageView getAbsenceIndicatorImageView() {
            return absenceIndicatorIV;
        }

        public View getView() {
            return mView;
        }

        public void setProfileImage(Context ctx, String image) {
            profileImage = (ImageView) mView.findViewById(R.id.userImageIV);
            // Picasso.with(ctx).load(image).into(profileImage); TODO
            profileImage.setImageResource(R.drawable.pph_s);//TEMP
        }

        public void setUserNameLastName(String firstLastName) {
            userNameLastName = (TextView) mView.findViewById(R.id.userFirstAndLastName);
            userNameLastName.setText(firstLastName);
        }
    }

}

