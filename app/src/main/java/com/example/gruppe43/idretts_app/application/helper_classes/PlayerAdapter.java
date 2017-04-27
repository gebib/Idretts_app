package com.example.gruppe43.idretts_app.application.helper_classes;

/**
 * Created by gebi9 on 01-Apr-17.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.model.PlayerModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by gebi9 on 31-Mar-17.
 */

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {
    private Context context;
    List<PlayerModel> data = Collections.emptyList();// takes care of that we dont have empty null pointer exception
    private LayoutInflater inflater;
    private int previousPosition = 0;


    public PlayerAdapter(Context context, List<PlayerModel> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = inflater.inflate(R.layout.player_items, parent, false);
        PlayerViewHolder holder = new PlayerViewHolder(view);
        return holder;
    }

    @Override//we use this to fill the data items inn
    public void onBindViewHolder(PlayerViewHolder playerViewholder, final int position) {

        playerViewholder.title.setText(data.get(position).tittle);
        playerViewholder.icon.setImageResource(data.get(position).iconId);
        previousPosition = position;


        final int currentPosition = position;
        final PlayerModel ddata = data.get(position);

        playerViewholder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "OnClick Called at position " + position, Toast.LENGTH_SHORT).show();
                // addItem(currentPosition, ddata); /*NEW ITEM ADDING COULD BE DONE HERE!///////////////////////////////////////////////////////////////////*/
            }
        });

        playerViewholder.icon.setOnLongClickListener(new View.OnLongClickListener() {
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
    class PlayerViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView icon;

        public PlayerViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.listTextPlayer);
            icon = (ImageView) itemView.findViewById(R.id.listIconPlayer);
        }
    }

    // This method adds(duplicates) a Object (item ) to our Data set as well as Recycler View.
    private void addItem(int position, PlayerModel infoData) {

        data.add(position, infoData);
        notifyItemInserted(position);
    }

    // This removes the data from our Dataset and Updates the Recycler View.
    private void removeItem(PlayerModel infoData) {
        int currPosition = data.indexOf(infoData);
        data.remove(currPosition);
        notifyItemRemoved(currPosition);
    }
}