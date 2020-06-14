package com.covidata.covidata;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


public class AcercaDeFragment extends Fragment {

    View view;
    ImageButton instagram;
    ImageButton twitter;
    ImageButton gmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_acerca_de, container, false);

        ImageView imagen1 = view.findViewById(R.id.imagen1);
        ImageView imagen2 = view.findViewById(R.id.imagen2);
        Glide.with(this).load(R.drawable.david).circleCrop().into(imagen1);
        Glide.with(this).load(R.drawable.sara).circleCrop().into(imagen2);

        imagen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getActivity().getApplicationContext(), "David López Carmona", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_HORIZONTAL,-200,-350);
                toast.show();
            }
        });

        imagen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Sara Mulas Tejera", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_HORIZONTAL,200,-350);
                toast.show();
            }
        });

        instagram=view.findViewById(R.id.instagram);
        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callInstagram();
            }
        });

        twitter=view.findViewById(R.id.twitter);
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callTwitter();
            }
        });

        gmail=view.findViewById(R.id.email);
        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callEmail();
            }
        });

        return view;
    }

    private void callInstagram() {
        Intent intent = null;
        try {
            // get the Twitter app if possible
            getActivity().getApplicationContext().getPackageManager().getPackageInfo("com.instagram.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/_u/covidataApp"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            // no Twitter app, revert to browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/covidataApp"));
        }
        this.startActivity(intent);

    }


    private void callTwitter() {
        Intent intent = null;
        try {
            // get the Twitter app if possible
            getActivity().getApplicationContext().getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=1271786837409923073"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            // no Twitter app, revert to browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/covidataApp"));
        }
        this.startActivity(intent);
    }

    private void callEmail(){
        try {
            Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "covidata.contacto@gmail.com"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "your_subject");
            intent.putExtra(Intent.EXTRA_TEXT, "your_text");
            startActivity(intent);
        } catch(Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), "Lo siento... No tienes ninguna aplicación de email instalada", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

}
