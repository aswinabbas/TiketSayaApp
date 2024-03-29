package com.awee.tiketku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class GetStartedAct extends AppCompatActivity {

    Button btn_sign_in, btn_new_account_create;
    Animation toptobuttom, textbuttomsplashalpha;
    ImageView emblem_logo;
    TextView intro_app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        //load animasi
        toptobuttom = AnimationUtils.loadAnimation(this, R.anim.toptobuttom);
        textbuttomsplashalpha = AnimationUtils.loadAnimation(this, R.anim.textbuttomsplashalpha);

        btn_sign_in = findViewById(R.id.btn_sign_in);
        btn_new_account_create = findViewById(R.id.btn_new_account_create);
        emblem_logo = findViewById(R.id.emblem_logo);
        intro_app = findViewById(R.id.intro_app);

        //run animation
        emblem_logo.startAnimation(toptobuttom);
        intro_app.startAnimation(toptobuttom);
        btn_sign_in.startAnimation(textbuttomsplashalpha);
        btn_new_account_create.startAnimation(textbuttomsplashalpha);


        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotosign = new Intent(GetStartedAct.this, SignInAct.class);
                startActivity(gotosign);
            }
        });

        btn_new_account_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoregisterone = new Intent(GetStartedAct.this, RegisterOneAct.class);
                startActivity(gotoregisterone);
            }
        });
    }
}
