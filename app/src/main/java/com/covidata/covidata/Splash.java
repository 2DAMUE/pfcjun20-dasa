package com.covidata.covidata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {

    ImageView ceImagen;
    ImageView punto1;
    ImageView punto2;
    ImageView punto3;
    ImageView punto4;
    ImageView punto5;
    ImageView punto6;
    ImageView logoInferior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ceImagen=findViewById(R.id.centroc);
        Animation parpadeo = AnimationUtils.loadAnimation(this,R.anim.blinksplash2);
        ceImagen.startAnimation(parpadeo);

        punto1=findViewById(R.id.punto1);
        Animation translacionpunto1 = AnimationUtils.loadAnimation(this,R.anim.translatesplash2punto1);
        punto1.startAnimation(translacionpunto1);

        punto2=findViewById(R.id.punto2);
        Animation translacionpunto2 = AnimationUtils.loadAnimation(this,R.anim.translatesplash2punto2);
        punto2.startAnimation(translacionpunto2);

        punto3=findViewById(R.id.punto3);
        Animation translacionpunto3 = AnimationUtils.loadAnimation(this,R.anim.translatesplash2punto3);
        punto3.startAnimation(translacionpunto3);

        punto4=findViewById(R.id.punto4);
        Animation translacionpunto4 = AnimationUtils.loadAnimation(this,R.anim.translatesplash2punto4);
        punto4.startAnimation(translacionpunto4);

        punto5=findViewById(R.id.punto5);
        Animation translacionpunto5 = AnimationUtils.loadAnimation(this,R.anim.translatesplash2punto5);
        punto5.startAnimation(translacionpunto5);

        punto6=findViewById(R.id.punto6);
        Animation translacionpunto6 = AnimationUtils.loadAnimation(this,R.anim.translatesplash2punto6);
        punto6.startAnimation(translacionpunto6);

        logoInferior=findViewById(R.id.logoInferior);
        Animation fadeIn = AnimationUtils.loadAnimation(this,R.anim.fadeinlogoinferior);
        logoInferior.startAnimation(fadeIn);
    }
}
