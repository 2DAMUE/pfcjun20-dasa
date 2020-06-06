package com.covidata.covidata;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageController extends FragmentPagerAdapter {
    int numeroTabs;

    public PageController(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.numeroTabs=behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new AutodiagnosticoFragment();
            case 1:
                return new RecomendacionesFragment();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return numeroTabs;
    }
}
