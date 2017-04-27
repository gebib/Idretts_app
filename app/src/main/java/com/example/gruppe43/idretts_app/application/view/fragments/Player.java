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
import com.example.gruppe43.idretts_app.application.helper_classes.PlayerAdapter;
import com.example.gruppe43.idretts_app.application.model.PlayerModel;


import java.util.ArrayList;
import java.util.List;

public class Player extends Fragment {

    private RecyclerView recyclerViewPlayerRV;
    private PlayerAdapter playerAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_player,container,false);

        recyclerViewPlayerRV = (RecyclerView) layout.findViewById(R.id.recyclerViewPlayer);
        playerAdapter = new PlayerAdapter(getActivity(),getData());
        recyclerViewPlayerRV.setAdapter(playerAdapter);
        recyclerViewPlayerRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }

    public static List<PlayerModel>getData(){
        List<PlayerModel> data = new ArrayList<>();

        int[] icons = {R.drawable.pph_s,R.drawable.pph_s,R.drawable.pph_s,R.drawable.pph_s,
                R.drawable.pph_s,R.drawable.pph_s,R.drawable.pph_s,R.drawable.pph_s,
                R.drawable.pph_s,R.drawable.pph_s,R.drawable.pph_s,R.drawable.pph_s,};
        String[] tittles = {"Egen training marius","Egen training hansen","marius elite trainer og god",
                "Lorem Ibsum Dolor sit amet","Consektetur adisipesing","marius elite trainer og god","Lorem Ibsum Dolor sit amet",
                "Consektetur adisipesing","marius elite trainer og god","Lorem Ibsum Dolor sit amet","Consektetur adisipesing","marius elite trainer og god"};

        //create list of dummydata objects to display.
        for (int i = 0; i <tittles.length && i < icons.length ; i++) {//so that if any of the arrays are shorter, that the app does not crash here..
            PlayerModel current = new PlayerModel();
            current.iconId = icons[i];
            current.tittle = tittles[i];

            data.add(current);
        }
        return data;
    }
}
