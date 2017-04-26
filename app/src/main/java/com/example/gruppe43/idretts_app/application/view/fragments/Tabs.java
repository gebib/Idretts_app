package com.example.gruppe43.idretts_app.application.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.interfaces.FragmentActivityInterface;

public class Tabs extends Fragment implements TabLayout.OnTabSelectedListener {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3;
    private FragmentActivityInterface mCallback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View x = inflater.inflate(R.layout.fragment_tabs, null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        tabLayout.addOnTabSelectedListener(this);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return x;
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
    public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getText().equals("Trainer")) {
           // mCallback.currentShowingFragment("trainer");
        } else if (tab.getText().equals("Player")) {
           // mCallback.currentShowingFragment("player");
        } else if (tab.getText().equals("Team")) {
           // mCallback.currentShowingFragment("team");
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

    class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new Trainer();
                case 1:
                    return new Player();
                case 2:
                    return new Team();
            }
            return null;
        }

        @Override
        public int getCount() {
            return int_items;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Trainer";
                case 1:
                    return "Player";
                case 2:
                    return "Team";
            }
            return null;
        }
    }

}