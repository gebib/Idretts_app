package com.example.gruppe43.idretts_app.application.view.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.model.TrainerModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Trainer extends Fragment {
    private DatabaseReference fbDbRef;
    private Context context;
    private RecyclerView trainerRecyclerView;

    public Trainer() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trainer, container, false);

        trainerRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewTrainer);

        fbDbRef =  FirebaseDatabase.getInstance().getReference().child("TrainerActivities");
        FirebaseRecyclerAdapter<TrainerModel, TrainerViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<TrainerModel, TrainerViewHolder>(
                TrainerModel.class,
                R.layout.trainer_items,
                TrainerViewHolder.class,
                fbDbRef
        ) {
            @Override
            protected void populateViewHolder(TrainerViewHolder viewHolder, TrainerModel model, final int position) {
                viewHolder.setActivityTitleDate(model.getTrainerPostTitleDate());
                viewHolder.setActivityStartEndTime(model.getTrainerPostStartStopTime());
                viewHolder.setActivityPlace(model.getTrainerPostPlace());
                viewHolder.setTrainerActivityTime(model.getTrainerPostTime());
                viewHolder.setTrainerActivityIntensity(model.getTrainerPostIntensity());
                viewHolder.setTrainerActivityIcon(model.getTrainerPostIcon());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO go to the full activity info page
                        System.out.println("//////////////////////////////RECYCLER click "+position);
                    }
                });
            }
        };
        trainerRecyclerView.setAdapter(firebaseRecyclerAdapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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

        public void setActivityTitleDate (String titleDate) {
            trainerPostTitleDate = (TextView) mView.findViewById(R.id.listTextTrainer);
            trainerPostTitleDate.setText(titleDate);
        }

        public void setActivityStartEndTime(String startEndTime) {
            trainerPostTitleDate = (TextView) mView.findViewById(R.id.detailInfo1Trainer);
            trainerPostStartStopTime .setText(startEndTime);
        }

        public void setActivityPlace(String place ) {
            trainerPostStartStopTime = (TextView) mView.findViewById(R.id.detailInfo2Trainer);
            trainerPostPlace.setText(place);
        }

        public void setTrainerActivityTime(String postTime) {
            trainerPostPlace = (TextView) mView.findViewById(R.id.timePostedTrainer);
            trainerPostTime.setText(postTime);
        }

        public void setTrainerActivityIntensity(String intensity) {
            trainerPostTime = (TextView) mView.findViewById(R.id.percentageTrainer);
            trainerPostIntensity.setText(intensity);
        }

        public void setTrainerActivityIcon (String icon) {
            trainerPostIcon = (ImageView) mView.findViewById(R.id.listIconTrainer);
            if(icon.equals("camp")){
                trainerPostIcon.setImageResource(R.drawable.cmp);
            }else if(icon.equals("training_f")){//football
                trainerPostIcon.setImageResource(R.drawable.training);
            }else if(icon.equals("meeting")){
                trainerPostIcon.setImageResource(R.drawable.meeting);
            }else if(icon.equals("training")){//styrke
                trainerPostIcon.setImageResource(R.drawable.training_s);
            }
        }

    }///////////////////////////////
}