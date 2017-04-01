package com.example.gruppe43.idretts_app.application.controll;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.model.TeamDummyData;

/**
 * Created by gebi9 on 01-Apr-17.
 */

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {
    private Context context;
    List<TeamDummyData> data = Collections.emptyList();// takes care of that we dont have empty null pointer exception
    private LayoutInflater inflater;
    private int previousPosition = 0;


    public TeamAdapter(Context context, List<TeamDummyData> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public TeamViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = inflater.inflate(R.layout.team_items, parent, false);
        TeamViewHolder holder = new TeamViewHolder(view);
        return holder;
    }

    @Override//we use this to fill the data items inn
    public void onBindViewHolder(TeamViewHolder TeamViewholder, final int position) {


        TeamViewholder.title.setText(data.get(position).tittle);
        TeamViewholder.icon.setImageResource(data.get(position).iconId);
        previousPosition = position;


        final int currentPosition = position;
        final TeamDummyData ddata = data.get(position);

        TeamViewholder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "OnClick Called at position " + position, Toast.LENGTH_SHORT).show();
                // addItem(currentPosition, ddata); /*NEW ITEM ADDING COULD BE DONE HERE!///////////////////////////////////////////////////////////////////*/
            }
        });

        TeamViewholder.icon.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(context, "OnLongClick Called at position " + position, Toast.LENGTH_SHORT).show();
                // removeItem(ddata); /*NEW ITEM removing!///////////////////////////////////////////////////////////////////*/
                return true;
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    /*here is where the data is fed into the recycler view///////////////////////////////////////////////*/
    class TeamViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView icon;

        public TeamViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.userInfoTV);
            icon = (ImageView) itemView.findViewById(R.id.userImageIV);
        }
    }

    // This method adds(duplicates) a Object (item ) to our Data set as well as Recycler View.
    private void addItem(int position, TeamDummyData infoData) {

        data.add(position, infoData);
        notifyItemInserted(position);
    }

    // This removes the data from our Dataset and Updates the Recycler View.
    private void removeItem(TeamDummyData infoData) {
        int currPosition = data.indexOf(infoData);
        data.remove(currPosition);
        notifyItemRemoved(currPosition);
    }
}
