package com.example.gruppe43.idretts_app.application.view.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.interfaces.FragmentActivityInterface;

public class Messages extends Fragment {
    private FragmentActivityInterface mCallback;

    public Messages() {
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
        mCallback.getFab().hide();
        View view = inflater.inflate(R.layout.fragment_messages, container, false);

        return view;
    }

}
