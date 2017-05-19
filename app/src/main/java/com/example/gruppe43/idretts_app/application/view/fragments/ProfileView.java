package com.example.gruppe43.idretts_app.application.view.fragments;

//Idretts-app bachelor oppgave 2017
//Ole-Kristian Steiro, Tasmia Faruque, Gebi Beshir
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.Authentication.DatabaseInterface.DatabaseHelperC;
import com.example.gruppe43.idretts_app.application.helper_classes.EditProfileDialog;
import com.example.gruppe43.idretts_app.application.interfaces.FragmentActivityInterface;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class ProfileView extends Fragment {
    private FragmentActivityInterface mCallback;

    public static ImageView profImageIV;//profile image

    private TextView profileNameTv;
    public static TextView textView3PlayerNr;
    private TextView textView11PlayerAge;
    public static TextView textView8Status;

    private ImageView imageButtonEditProfile;//use as button

    private TextView dateRegisteredDate;
    public static TextView playerTypeText;
    private TextView numberOfminutePlayedText;
    private TextView numberOfRedCardText;
    private TextView numberOfGreenCardText;
    private TextView numberOfYellowCardText;
    private TextView numberOfPerfectPassingsText;
    private TextView personalActivityActivitiesText;

    private TextView nAbsFb;
    private TextView nGym;
    private TextView nAbsTheor;
    private TextView nAbsMatch;
    private TextView nAccidents;
    private TextView nTotalAttended;

    private Button profileViewSaveChange;


    private static ArrayList<String> selectedUserIfoData;
    private String selectedUserIdInTeam;

    public static final int GALLERY_REQUEST = 1;
    public static Uri imageUri;


    public void setSelectedUserIfoData(ArrayList<String> selectedUserIfoData) {
        this.selectedUserIfoData = selectedUserIfoData;
    }

    public ProfileView() {
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
        View view = inflater.inflate(R.layout.fragment_profile_view, container, false);
        mCallback.getFab().hide();

        selectedUserIdInTeam = Team.selectedUserIdInTeam;

        profileNameTv = (TextView) view.findViewById(R.id.profileNameTv);
        profImageIV = (ImageView) view.findViewById(R.id.profImageIV);
        textView3PlayerNr = (TextView) view.findViewById(R.id.textView3PlayerNr);
        textView11PlayerAge = (TextView) view.findViewById(R.id.textView11PlayerAge);
        textView8Status = (TextView) view.findViewById(R.id.textView8Status);

        imageButtonEditProfile = (ImageView) view.findViewById(R.id.imageButtonEditProfile);

        dateRegisteredDate = (TextView) view.findViewById(R.id.dateRegisteredDate);
        playerTypeText = (TextView) view.findViewById(R.id.playerTypeText);
        numberOfminutePlayedText = (TextView) view.findViewById(R.id.numberOfminutePlayedText);
        numberOfRedCardText = (TextView) view.findViewById(R.id.numberOfRedCardText);
        numberOfGreenCardText = (TextView) view.findViewById(R.id.numberOfGreenCardText);
        numberOfYellowCardText = (TextView) view.findViewById(R.id.numberOfYellowCardText);
        numberOfPerfectPassingsText = (TextView) view.findViewById(R.id.numberOfPerfectPassingsText);

        personalActivityActivitiesText = (TextView) view.findViewById(R.id.personalActivityActivitiesText);

        nAbsFb = (TextView) view.findViewById(R.id.nAbsFb);
        nGym = (TextView) view.findViewById(R.id.nGym);
        nAbsTheor = (TextView) view.findViewById(R.id.nAbsTheor);
        nAbsMatch = (TextView) view.findViewById(R.id.nAbsMatch);

        nAccidents = (TextView) view.findViewById(R.id.nAccidents);
        nTotalAttended = (TextView) view.findViewById(R.id.nTotalAttended);

        profileViewSaveChange = (Button) view.findViewById(R.id.profileViewSaveChange);

        profileViewSaveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String statuss = textView8Status.getText().toString().trim();
                String playerNr = textView3PlayerNr.getText().toString().trim();
                String playerType = playerTypeText.getText().toString().trim();
                if (statuss.equals("") && playerNr.equals("") && playerType.equals("")) {
                    Toast.makeText(mCallback.getContext(), R.string.nocahngesTosaveProfileEdit, Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseHelperC dbhc = new DatabaseHelperC(mCallback.getContext());
                    dbhc.updateProfileEdit(statuss, playerNr, playerType);
                }
            }
        });

        FirebaseAuth fbAuth = FirebaseAuth.getInstance();
        String currentUserId = fbAuth.getCurrentUser().getUid();

        if (!Team.selectedUserIdInTeam.equals(currentUserId)) {
            imageButtonEditProfile.setVisibility(view.GONE);
            profileViewSaveChange.setVisibility(view.GONE);
        } else {
            profImageIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    galleryIntent.setType("image/*");
                    mCallback.getContext().startActivityForResult(galleryIntent, ProfileView.GALLERY_REQUEST);
                }
            });

            imageButtonEditProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditProfileDialog epd = new EditProfileDialog();
                    epd.setCtx(mCallback.getContext());
                    epd.show(mCallback.getContext().getFragmentManager(), "");
                }
            });
        }

        if (selectedUserIfoData != null) {
            try {
                profileNameTv.setText(selectedUserIfoData.get(0));
                textView3PlayerNr.setText(selectedUserIfoData.get(1));
                textView11PlayerAge.setText(selectedUserIfoData.get(2));
                textView8Status.setText(selectedUserIfoData.get(3));

                dateRegisteredDate.setText(selectedUserIfoData.get(4));
                playerTypeText.setText(selectedUserIfoData.get(5));
                numberOfminutePlayedText.setText(selectedUserIfoData.get(6));
                numberOfRedCardText.setText(selectedUserIfoData.get(7));
                numberOfGreenCardText.setText(selectedUserIfoData.get(8));
                numberOfYellowCardText.setText(selectedUserIfoData.get(9));
                numberOfPerfectPassingsText.setText(selectedUserIfoData.get(10));

                personalActivityActivitiesText.setText(selectedUserIfoData.get(11));

                nAbsFb.setText(selectedUserIfoData.get(12));
                nGym.setText(selectedUserIfoData.get(13));
                nAbsTheor.setText(selectedUserIfoData.get(14));
                nAbsMatch.setText(selectedUserIfoData.get(15));

                nAccidents.setText(selectedUserIfoData.get(16));
                nTotalAttended.setText(selectedUserIfoData.get(17));

                final String profileImage = selectedUserIfoData.get(18);
                Picasso.with(mCallback.getContext())
                        .load(profileImage)
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(profImageIV, new Callback() {
                            @Override
                            public void onSuccess() {
                                //picture exist in cache
                            }

                            @Override
                            public void onError() {
                                Picasso.with(mCallback.getContext())
                                        .load(profileImage)
                                        .error(R.drawable.pph_s)
                                        .into(profImageIV);
                            }
                        });


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return view;
    }
}
