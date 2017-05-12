package com.example.gruppe43.idretts_app.application.helper_classes;

import android.app.DialogFragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.controll.DatabaseHelperC;
import com.example.gruppe43.idretts_app.application.interfaces.FragmentActivityInterface;
import com.example.gruppe43.idretts_app.application.view.main.MainActivity;

/**
 * Created by gebi9 on 12-May-17.
 */

public class EditProfileDialog extends DialogFragment {
    private EditText myStatus;
    private EditText myPlayerNr;
    private EditText myPlayerType;
    public static  MainActivity ctx;

    private Button profileEditBtn;

    public void setCtx(MainActivity ctx) {
        this.ctx = ctx;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_profile, null);
        getDialog().setTitle(R.string.editDialogTitle);

        myStatus = (EditText) view.findViewById(R.id.myStatus);
        myPlayerNr = (EditText) view.findViewById(R.id.myPlayerNr);
        myPlayerType = (EditText) view.findViewById(R.id.myPlayerType);

        profileEditBtn = (Button) view.findViewById(R.id.profileEditBtn);
        profileEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String statuss = myStatus.getText().toString().trim();
                    String playerNr = myPlayerNr.getText().toString().trim();
                    String playerType = myPlayerType.getText().toString().trim();
                    if (statuss.equals("") && playerNr.equals("") && playerType.equals("")) {
                        Toast.makeText(ctx, R.string.nocahngesTosaveProfileEdit, Toast.LENGTH_SHORT).show();
                    } else {
                        getDialog().dismiss();
                        DatabaseHelperC dbhc = new DatabaseHelperC(ctx);
                        dbhc.updateProfileEdit(statuss, playerNr, playerType);
                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }
}
