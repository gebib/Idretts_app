package com.example.gruppe43.idretts_app.application.view.fragments;

import android.content.Context;
import android.os.Bundle;
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
import com.example.gruppe43.idretts_app.application.model.TrainerModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Trainer extends Fragment {
    private RecyclerView recyclerViewTrainerRV;
    private DatabaseReference fbDbRef;
    private DataBaseHelper databaseHelper;
    private FragmentActivityInterface mCallback;
    private PrefferencesClass prefs;

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
        View view = inflater.inflate(R.layout.fragment_trainer, container, false);

        recyclerViewTrainerRV = (RecyclerView) view.findViewById(R.id.recyclerViewTrainer);

        fbDbRef = FirebaseDatabase.getInstance().getReference().child("TrainerPosts");

        FirebaseRecyclerAdapter<TrainerModel, TrainerViewHolder> trainerAdapter = new FirebaseRecyclerAdapter<TrainerModel, TrainerViewHolder>(
                TrainerModel.class,
                R.layout.trainer_items,
                TrainerViewHolder.class,
                fbDbRef
        ) {
            @Override
            protected void populateViewHolder(TrainerViewHolder viewHolder, TrainerModel model, final int position) {

                viewHolder.setActivityTitleDate(model.getTitle() + " " + model.getActivityDate());
                viewHolder.setActivityStartEndTime(getString(R.string.actStarts) +" "+ model.getStartTime() + " " + getString(R.string.actEnds) +" "+ model.getEndTime());
                viewHolder.setActivityPlace(getString(R.string.actLocation) +" "+ model.getPlace() + " ");
                viewHolder.setTrainerActivityTime(model.getTimePosted());
                viewHolder.setTrainerActivityIntensity(model.getIntensity());
                viewHolder.setTrainerActivityIcon(model.getIcon());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseHelper = new DataBaseHelper(mCallback.getContext());
                        String postKey = getRef(position).getKey();
                        databaseHelper.getSelectedActivityInfo(postKey);
                        NewActivityRegistration.setSelectedActivityPostKey(postKey);
                    }
                });
            }
        };

        recyclerViewTrainerRV.setAdapter(trainerAdapter);
        recyclerViewTrainerRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    public static class TrainerViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView trainerPostTitleDate;
        TextView trainerPostStartStopTime;
        TextView trainerPostPlace;
        TextView trainerPostTime;
        TextView trainerPostIntensity;
        ImageView trainerPostIcon;


        public TrainerViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setActivityTitleDate(String titleDate) {
            trainerPostTitleDate = (TextView) mView.findViewById(R.id.listTextTrainer);
            trainerPostTitleDate.setText(titleDate);
        }

        public void setActivityStartEndTime(String startEndTime) {
            trainerPostStartStopTime = (TextView) mView.findViewById(R.id.detailInfo1Trainer);
            trainerPostStartStopTime.setText(startEndTime);
        }

        public void setActivityPlace(String place) {
            trainerPostPlace = (TextView) mView.findViewById(R.id.detailInfo2Trainer);
            trainerPostPlace.setText(place);
        }

        public void setTrainerActivityTime(String postTime) {
            trainerPostTime = (TextView) mView.findViewById(R.id.timePostedTrainer);
            trainerPostTime.setText(postTime);
        }

        public void setTrainerActivityIntensity(String intensity) {
            trainerPostIntensity = (TextView) mView.findViewById(R.id.percentageTrainer);
            trainerPostIntensity.setText(intensity);
        }

        public void setTrainerActivityIcon(String icon) {
            trainerPostIcon = (ImageView) mView.findViewById(R.id.listIconTrainer);
            if (icon.equals("camp")) {
                trainerPostIcon.setImageResource(R.drawable.cmp);
            } else if (icon.equals("training_f")) {//football
                trainerPostIcon.setImageResource(R.drawable.training_f);
            } else if (icon.equals("meeting")) {
                trainerPostIcon.setImageResource(R.drawable.meeting);
            } else if (icon.equals("training")) {//styrke
                trainerPostIcon.setImageResource(R.drawable.training_s);
            }
        }

    }
}
