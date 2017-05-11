package com.example.gruppe43.idretts_app.application.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.example.gruppe43.idretts_app.application.controll.DatabaseHelperC;
import com.example.gruppe43.idretts_app.application.helper_classes.PrefferencesClass;
import com.example.gruppe43.idretts_app.application.interfaces.FragmentActivityInterface;


public class FullActivityInfo extends Fragment {
    private Button activityEditButton;
    private Button activityDeleteButton;

    private ImageView fullactivityinfoImageView;

    private TextView fulActivityInfoTopBar;
    private TextView fulActivityInfoBottomBar;
    private TextView fullactivityinfoTitleTV;
    private TextView fullactivityinfoDate;
    private TextView fullactivityinfoStartEndTime;
    private TextView fullactivityinfoPlaceTV;
    private TextView postOwnerNameAndPostedDate;
    private TextView activityTextInfoTV;
    private FragmentActivityInterface mCallback;
    private String[] postDataToDisplay;
    private static boolean isEditClicked;
    private Button absenceControllButton;
    private RelativeLayout buttonsHolderRelativeLayout;
    private Button addCampRecords;
    private static String activityType;


    public static String getActivityType() {
        return activityType;
    }

    public FullActivityInfo() {
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

    public static boolean getIsEditClicked() {
        return isEditClicked;
    }

    public static void setIsEditClicked(boolean isEditClicked) {
        FullActivityInfo.isEditClicked = isEditClicked;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_activity_info, container, false);
        mCallback.getFab().hide();
        isEditClicked = false;
        activityEditButton = (Button) view.findViewById(R.id.showFullActivityInfoEditButton);
        activityDeleteButton = (Button) view.findViewById(R.id.showFullActivityInfoDeleteButton);
        absenceControllButton = (Button) view.findViewById(R.id.absenceControllButton);
        addCampRecords = (Button) view.findViewById(R.id.addRemoveCampRecords);

        fullactivityinfoImageView = (ImageView) view.findViewById(R.id.fullactivityinfoImageView);

        fulActivityInfoTopBar = (TextView) view.findViewById(R.id.fullActivityInfoTopBar);
        fulActivityInfoBottomBar = (TextView) view.findViewById(R.id.fullActivityInfoBottomBar);
        fullactivityinfoTitleTV = (TextView) view.findViewById(R.id.fullactivityinfoTitleTV);
        fullactivityinfoDate = (TextView) view.findViewById(R.id.fullactivityinfoDate);
        fullactivityinfoStartEndTime = (TextView) view.findViewById(R.id.fullactivityinfoStartEndTime);
        fullactivityinfoPlaceTV = (TextView) view.findViewById(R.id.fullactivityinfoPlaceTV);
        postOwnerNameAndPostedDate = (TextView) view.findViewById(R.id.postDatePostedByOwner);
        activityTextInfoTV = (TextView) view.findViewById(R.id.activityTextInfoTV);

        buttonsHolderRelativeLayout = (RelativeLayout) view.findViewById(R.id.buttonsHolderRelativeLayout);

        absenceControllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelperC dbhc = new DatabaseHelperC(mCallback.getContext());
                dbhc.retrieveAllPlayersRegisteredAsAbsent(); // TODO ok???????     /////////////////////////////////////////////////////////////);/////////////////////////////////////////////////////////////);/////////////////////////////////////////////////////////////);/////////////////////////////////////////////////////////////

               /* Bundle bundle = new Bundle();
                bundle.putBoolean("absenceCheck", true);
                Team teamFrag = new Team();
                teamFrag.setArguments(bundle);

                FragmentTransaction fragmentTransaction = mCallback.getmFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack("");
                fragmentTransaction.replace(R.id.containerView, teamFrag).commit();*/
            }
        });

        addCampRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelperA dbh = new DataBaseHelperA(mCallback.getContext());
                dbh.retrieveAllPlayersNameAndId();
            }
        });

        try {
            Bundle bundle = this.getArguments();
            postDataToDisplay = bundle.getStringArray("postData");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Bundle::::", "isNull");
        }
        activityEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEditClicked = true;
                mCallback.setIsOnNewActivityRegisterPage(true);
                mCallback.showFragmentOfGivenCondition();
            }
        });
        PrefferencesClass prefs = new PrefferencesClass(mCallback.getContext());
        String prefData = prefs.loadSharedPrefData("isAdmin");
        if (prefData.equals("false")) {
            buttonsHolderRelativeLayout.setVisibility(view.GONE);
        }
        activityDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 DataBaseHelperA dbh = new DataBaseHelperA(mCallback.getContext());
                dbh.deleteSelectedPost(TrainerActivityRegistration.getSelectedActivityPostKey(),true,activityType);
                //mCallback.clearBackStack();
                FragmentTransaction fragmentTransaction = mCallback.getmFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, new Tabs()).commit();
            }
        });

        postDataToDisplay = getArguments().getStringArray("postData");
        String icon = postDataToDisplay[9];
        switch (icon) {
            case "training_f":
                fullactivityinfoImageView.setImageResource(R.drawable.training_f);
                activityType = "footballT";
                break;
            case "meeting":
                fullactivityinfoImageView.setImageResource(R.drawable.meeting);
                activityType = "Meet";
                break;
            case "training":
                fullactivityinfoImageView.setImageResource(R.drawable.training_s);
                activityType = "gymT";
                break;
            case "camp":
                fullactivityinfoImageView.setImageResource(R.drawable.cmp);
                activityType = "camp";
                break;
            default:
                fullactivityinfoImageView.setImageResource(R.drawable.ia_logo);
        }
        if (!(postDataToDisplay[0].equals("Football camp") || postDataToDisplay[0].equals("Fotballkamp"))){
            addCampRecords.setEnabled(false);
        }else{
            addCampRecords.setEnabled(true);
        }

        fulActivityInfoTopBar.setText(postDataToDisplay[0]);
        fulActivityInfoBottomBar.setText(postDataToDisplay[0]);
        fullactivityinfoTitleTV.setText(postDataToDisplay[0]);
        fullactivityinfoDate.setText(postDataToDisplay[1]);
        String activityStartEnd = getString(R.string.fullActivityShowStartS) + " " + postDataToDisplay[2] + " " + getString(R.string.fullActivityShowEndS) + " " + postDataToDisplay[3];
        fullactivityinfoStartEndTime.setText(activityStartEnd);
        fullactivityinfoPlaceTV.setText(postDataToDisplay[4]);
        String postDateAndOwner = getString(R.string.by) + " " + postDataToDisplay[10] + " " + postDataToDisplay[11] + " " + postDataToDisplay[6] + " " + postDataToDisplay[8];
        postOwnerNameAndPostedDate.setText(postDateAndOwner);
        activityTextInfoTV.setText(postDataToDisplay[7]);

        return view;
    }
}
