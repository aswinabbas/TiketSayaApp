package com.awee.tiketku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.florent37.shapeofview.shapes.CircleView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HomeAct extends AppCompatActivity {

    LinearLayout ic_pisa, ic_torri, ic_pagoda, ic_candi, ic_spins, ic_monas;
    CircleView btn_my_profile;
    ImageView photo_home_user;
    TextView bio, nama_lengkap, user_balance;


    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getUsernameLocal();

        btn_my_profile = findViewById(R.id.btn_my_profile);
        ic_pisa = findViewById(R.id.ic_pisa);
        ic_torri = findViewById(R.id.ic_torri);
        ic_pagoda = findViewById(R.id.ic_pagoda);
        ic_candi = findViewById(R.id.ic_candi);
        ic_spins = findViewById(R.id.ic_spins);
        ic_monas = findViewById(R.id.ic_monas);


        photo_home_user = findViewById(R.id.photo_home_user);
        bio = findViewById(R.id.bio);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        user_balance = findViewById(R.id.user_balance);


        //cek/mengambil data dari database
        reference = FirebaseDatabase.getInstance().getReference().
                child("Users").child(username_key_new);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_lengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                bio.setText(dataSnapshot.child("bio").getValue().toString());
                user_balance.setText(dataSnapshot.child("user_balance").getValue().toString());

                Picasso.with(HomeAct.this).load(dataSnapshot.child("url_photo_profile").
                        getValue().toString()).centerCrop().fit().into(photo_home_user);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ic_pisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotopisadetail = new Intent(HomeAct.this, TicketDetailAct.class);
                //meletakkan data ke Intent (ticketDetailAct)
                gotopisadetail.putExtra("jenis_tiket", "Pisa");
                startActivity(gotopisadetail);
            }
        });

        ic_torri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotorrisadetail = new Intent(HomeAct.this, TicketDetailAct.class);
                gotorrisadetail.putExtra("jenis_tiket", "Torri");
                startActivity(gotorrisadetail);
            }
        });

        ic_pagoda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotopagodadetail = new Intent(HomeAct.this, TicketDetailAct.class);
                gotopagodadetail.putExtra("jenis_tiket", "Pagoda");
                startActivity(gotopagodadetail);
            }
        });

        ic_candi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotocandidetail = new Intent(HomeAct.this, TicketDetailAct.class);
                gotocandidetail.putExtra("jenis_tiket", "Candi");
                startActivity(gotocandidetail);
            }
        });

        ic_spins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotospinsdetail = new Intent(HomeAct.this, TicketDetailAct.class);
                gotospinsdetail.putExtra("jenis_tiket", "Spins");
                startActivity(gotospinsdetail);
            }
        });

        ic_monas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotomonasdetail = new Intent(HomeAct.this, TicketDetailAct.class);
                gotomonasdetail.putExtra("jenis_tiket", "Monas");
                startActivity(gotomonasdetail);
            }
        });




        btn_my_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoprofile = new Intent(HomeAct.this, MyProfileAct.class);
                startActivity(gotoprofile);
            }
        });
    }

    //penyimpanan file local
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
