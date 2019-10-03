package com.awee.tiketku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class TicketDetailAct extends AppCompatActivity {

    Button btn_continue;
    TextView title_ticket, location_ticket,
            photo_spot, wifi_available,
            festival, short_message;
    ImageView header_ticket_detail;
    LinearLayout btn_back;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        btn_continue = findViewById(R.id.btn_continue);

        title_ticket = findViewById(R.id.title_ticket);
        location_ticket = findViewById(R.id.location_ticket);
        photo_spot = findViewById(R.id.photo_spot);
        wifi_available = findViewById(R.id.wifi_available);
        festival = findViewById(R.id.festival);
        short_message = findViewById(R.id.short_message);

        header_ticket_detail = findViewById(R.id.header_ticket_detail);
        btn_back = findViewById(R.id.btn_back);

        //mengambil data dari Intent (dari HomeAct)
        Bundle bundle = getIntent().getExtras();
        final String jenis_tiket_baru = bundle.getString("jenis_tiket");

        //Toast.makeText(getApplicationContext(), "Jenis Tiket: " + jenis_tiket_baru, Toast.LENGTH_SHORT).show();

        //mengambil data dari Firebase berdasarkan Intent
        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(jenis_tiket_baru);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //menimpa data yang ada dengan data yang baru
                title_ticket.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                location_ticket.setText(dataSnapshot.child("lokasi").getValue().toString());
                photo_spot.setText(dataSnapshot.child("is_photo_spot").getValue().toString());
                wifi_available.setText(dataSnapshot.child("is_wifi").getValue().toString());
                festival.setText(dataSnapshot.child("is_fetsival").getValue().toString());
                short_message.setText(dataSnapshot.child("short_desc").getValue().toString());

                Picasso.with(TicketDetailAct.this).load(dataSnapshot.child("url_thumbnail").
                        getValue().toString()).centerCrop().fit().into(header_ticket_detail);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotobuytiket = new Intent(TicketDetailAct.this, TicketCheckoutAct.class);
                //meletakkan data ke Intent (ticketDetailAct)
                gotobuytiket.putExtra("jenis_tiket", jenis_tiket_baru);
                startActivity(gotobuytiket);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}












