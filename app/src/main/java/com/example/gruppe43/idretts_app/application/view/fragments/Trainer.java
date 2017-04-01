package com.example.gruppe43.idretts_app.application.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.controll.TrainerAdapter;
import com.example.gruppe43.idretts_app.application.model.TrainerDummyData;

import java.util.ArrayList;
import java.util.List;

public class Trainer extends Fragment {
    private RecyclerView recyclerViewTrainerRV;
    private TrainerAdapter trainerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View layout = inflater.inflate(R.layout.fragment_trainer,container,false);

            recyclerViewTrainerRV = (RecyclerView) layout.findViewById(R.id.recyclerViewTrainer);
            trainerAdapter = new TrainerAdapter(getActivity(),getData());
            recyclerViewTrainerRV.setAdapter(trainerAdapter);
            recyclerViewTrainerRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }

    public static List<TrainerDummyData>getData(){
        List<TrainerDummyData> data = new ArrayList<>();

        int[] icons = {R.drawable.activity_logo,R.drawable.activity_training,R.drawable.activity_logo,R.drawable.activity_logo,
                R.drawable.activity_logo,R.drawable.activity_logo,R.drawable.activity_training,R.drawable.activity_logo};
        String[] tittles = {"hardt intesift kollen fem","Stafett fram og tilbake","Football kamp med bor",
                "Samling og teoritid i salen"," intesift kollen fem","Stafett fram og tilbake","Football kamp med bor","Samling og teoritid i salen",};

        //create list of dummydata objects to display.
        for (int i = 0; i <tittles.length && i < icons.length ; i++) {//so that if any of the arrays are shorter, that the app does not crash here..
            TrainerDummyData current = new TrainerDummyData();
            current.iconId = icons[i];
            current.tittle = tittles[i];
            data.add(current);
        }
        return data;
    }

}
