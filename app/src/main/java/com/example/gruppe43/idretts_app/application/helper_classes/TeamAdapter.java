package com.example.gruppe43.idretts_app.application.helper_classes;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.model.TeamModel;

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

/**
 * Created by gebi9 on 01-Apr-17.
 */


public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {
    public static Context context;
    List<TeamModel> data;
    private LayoutInflater inflater;
    private int previousPosition = 0;
    private boolean teamDataPulled;
    public static TeamAdapter teamModelContext;


    private DatabaseReference fbDbRef;
    private DatabaseReference fbDbUsersRef;
    private DataBaseHelperA databaseHelper;
    private FragmentActivityInterface mCallback;
    private PrefferencesClass prefs;
    private RecyclerView recyclerViewTeamRV;
    static boolean isForCheckAbsence;
    private ArrayList<String> absentPlayersIdsToBeSent;
    private ArrayList<View> correspondingAbsentPlayerView; // holds the temp views
    private RelativeLayout adtoAbsentHolderRLayout;
    private Button button2AddToAbsentLists;
    private static ArrayList<String> listOfPlayersAlreadMarkedAsAbsent;//init before
    private static ArrayList<String> listOfTobeRemovedFromAbsent;// add to from list of already existing players

    public TeamAdapter(Context context, List<TeamModel> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
        teamDataPulled = false;
        teamModelContext = this;
    }


    @Override
    public TeamViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = inflater.inflate(R.layout.team_items, parent, false);
        TeamViewHolder holder = new TeamViewHolder(view);
        return holder;
    }

    @Override//we use this to fill the data items inn
    public void onBindViewHolder(final TeamViewHolder TeamViewholder, final int position) {


        TeamViewholder.setUserNameLastName("testSetName");
        TeamViewholder.setProfileImage(context, "");


        previousPosition = position;
        final int currentPosition = position;
        // final TeamModel ddata = data.get(position);

        TeamViewholder.getView().setOnClickListener(new View.OnClickListener() {
            public View buttonParametre;
            @Override
            public void onClick(View v) {
                System.out.println("////////////////////////// " + TeamViewholder.getPosition());
                // removeItem(ddata);
               //_____________________________________________

                //___________________________________
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    /*here is where the data is fed into the recycler view///////////////////////////////////////////////*/
    class TeamViewHolder extends RecyclerView.ViewHolder {
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






    /*// This method adds(duplicates) a Object (item ) to our Data set as well as Recycler View.
    public void addItem(int position, TeamModel infoData) {
        System.out.println("position: " + position);
        System.out.println("data: " + infoData.tittle);
        data.add(position, infoData);
        notifyItemInserted(position);
    }


    // This removes the data from our Dataset and Updates the Recycler View.
    private void removeItem(TeamModel infoData) {
        int currPosition = data.indexOf(infoData);
        data.remove(currPosition);
        notifyItemRemoved(currPosition);
    }*/


