package com.awee.tiketku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashAct extends AppCompatActivity {

    Animation app_splash, textbuttomsplashalpha;
    ImageView app_logo;
    TextView app_title;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getUsernameLocal();

        //load animasi
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        textbuttomsplashalpha= AnimationUtils.loadAnimation(this, R.anim.textbuttomsplashalpha);

        //find element
        app_logo = findViewById(R.id.app_logo);
        app_title = findViewById(R.id.app_title);


        //run animation
        app_logo.startAnimation(app_splash);
        app_title.startAnimation(textbuttomsplashalpha);

    }

    //mengatur agar user yg sedang login tetap masuk saat aplikasi berjalan/belum logout dari MyProfileAct
    //penyimpanan file local
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
        if (username_key_new.isEmpty()) {
            //membuat timer pindah activity
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    //pindah activity
                    Intent gogetstarted = new Intent(SplashAct.this, GetStartedAct.class);
                    startActivity(gogetstarted);
                    finish();
                }
            }, 2000);
        }else {
            //membuat timer pindah activity
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    //pindah activity
                    Intent gotohome = new Intent(SplashAct.this, HomeAct.class);
                    startActivity(gotohome);
                    finish();
                }
            }, 2000);
        }
    }
}
