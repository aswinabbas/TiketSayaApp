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

public class SuccesRegisterAct extends AppCompatActivity {

    Button btn_explore;
    Animation toptobuttom, textbuttomsplashalpha;
    ImageView succes_icon_app;
    TextView app_title, app_subtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succes_register);

        //load animasi
        toptobuttom = AnimationUtils.loadAnimation(this, R.anim.toptobuttom);
        textbuttomsplashalpha= AnimationUtils.loadAnimation(this, R.anim.textbuttomsplashalpha);

        btn_explore = findViewById(R.id.btn_explore);
        succes_icon_app = findViewById(R.id.succes_icon_app);
        app_title = findViewById(R.id.app_title);
        app_subtitle = findViewById(R.id.app_subtitle);

        //run animation
        succes_icon_app.startAnimation(toptobuttom);
        app_title.startAnimation(textbuttomsplashalpha);
        app_subtitle.startAnimation(textbuttomsplashalpha);
        btn_explore.startAnimation(textbuttomsplashalpha);


        btn_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotohome = new Intent(SuccesRegisterAct.this, HomeAct.class);
                startActivity(gotohome);
            }
        });
    }
}
