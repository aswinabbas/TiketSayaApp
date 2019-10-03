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

public class SuccesBuyTicketAct extends AppCompatActivity {

    Animation toptobuttom, textbuttomsplashalpha;
    ImageView icon_succesbuy;
    TextView title_succes, subtitle_succes;
    Button btn_view_ticket, btn_mydashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succes_buy_ticket);

        //load animasi
        toptobuttom = AnimationUtils.loadAnimation(this, R.anim.toptobuttom);
        textbuttomsplashalpha= AnimationUtils.loadAnimation(this, R.anim.textbuttomsplashalpha);

        icon_succesbuy = findViewById(R.id.icon_succesbuy);
        title_succes = findViewById(R.id.title_succes);
        subtitle_succes = findViewById(R.id.subtitle_succes);
        btn_view_ticket = findViewById(R.id.btn_view_ticket);
        btn_mydashboard = findViewById(R.id.btn_mydashboard);

        //run animation
        icon_succesbuy.startAnimation(toptobuttom);
        title_succes.startAnimation(toptobuttom);
        subtitle_succes.startAnimation(toptobuttom);
        btn_view_ticket.startAnimation(textbuttomsplashalpha);
        btn_mydashboard.startAnimation(textbuttomsplashalpha);

        btn_view_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoviewtiket = new Intent(SuccesBuyTicketAct.this, MyProfileAct.class);
                startActivity(gotoviewtiket);
            }
        });

        btn_mydashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotomydashboar = new Intent(SuccesBuyTicketAct.this, HomeAct.class);
                startActivity(gotomydashboar);
            }
        });

    }
}
