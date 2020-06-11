package com.covidata.covidata;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;

import nl.psdcompany.duonavigationdrawer.views.DuoOptionView;

class MenuAdapter extends BaseAdapter {

    private ArrayList<String> mOptions = new ArrayList<>();
    private ArrayList<DuoOptionView> mOptionViews = new ArrayList<>();
    Context contexto;

    MenuAdapter(ArrayList<String> options,Context contexto) {
        mOptions = options;
        this.contexto=contexto;
    }

    @Override
    public int getCount() {
        return mOptions.size();
    }

    @Override
    public Object getItem(int position) {
        return mOptions.get(position);
    }

    void setViewSelected(int position, boolean selected) {

        // Looping through the options in the menu
        // Selecting the chosen option
        for (int i = 0; i < mOptionViews.size(); i++) {
            if (i == position) {
                mOptionViews.get(i).setSelected(selected);
            } else {
                mOptionViews.get(i).setSelected(!selected);
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String option = mOptions.get(position);
        Drawable icono=null;

        Log.e("opcion"," "+position);

        // Using the DuoOptionView to easily recreate the demo
        final DuoOptionView optionView;
        if (convertView == null) {
            optionView = new DuoOptionView(parent.getContext());
        } else {
            optionView = (DuoOptionView) convertView;
        }

        if(position==0){
            icono = contexto.getDrawable(R.drawable.ic_earth);
        }else if(position==1){
            icono = contexto.getDrawable(R.drawable.ic_country);
        }else if(position==2){
            icono = contexto.getDrawable(R.drawable.ic_ccaa);
        }else if(position==3){
            icono = contexto.getDrawable(R.drawable.ic_foldednewspaper);
        }else if(position==4){
            icono = contexto.getDrawable(R.drawable.ic_healthcare_and_medical);
        }else if(position==5){
            icono = contexto.getDrawable(R.drawable.ic_communications);
        }
        // Using the DuoOptionView's default selectors
        optionView.bind(option, icono, null);

        // Adding the views to an array list to handle view selection
        mOptionViews.add(optionView);

        return optionView;
    }
}
