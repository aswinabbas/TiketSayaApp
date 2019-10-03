package com.awee.tiketku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class TicketCheckoutAct extends AppCompatActivity {

    Button btn_pay_now, btnminus, btnplus;
    TextView textjumlahpesanan, textmybalance, texttotalharga, nama_wisata, lokasi, ketentuan;
    Integer valuejumlahpesanan = 1;
    Integer mybalance = 0;
    Integer valuetotalharga = 0;
    Integer valuehargatiket = 0;
    ImageView icon_warning_bayar;
    LinearLayout btn_back;


    DatabaseReference reference, reference2, reference3, reference4;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    String date_wisata="";
    String time_wisata="";

    Integer sisa_balance = 0;

    //bilangan random diperlukan untuk transaksi unik
    Integer nomor_transaksi = new Random().nextInt();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_checkout);

        getUsernameLocal();

        //mengambil data dari Intent (dari TikcketDetailAct)
        Bundle bundle = getIntent().getExtras();
        final String jenis_tiket_baru = bundle.getString("jenis_tiket");

        btn_pay_now = findViewById(R.id.btn_pay_now);
        btnminus = findViewById(R.id.btnminus);
        btnplus = findViewById(R.id.btnplus);
        textjumlahpesanan = findViewById(R.id.textjumlahpesanan);
        textmybalance = findViewById(R.id.textmybalance);
        texttotalharga = findViewById(R.id.texttotalharga);
        icon_warning_bayar = findViewById(R.id.icon_warning_bayar);
        btn_back = findViewById(R.id.btn_back);

        nama_wisata = findViewById(R.id.nama_wisata);
        lokasi = findViewById(R.id.lokasi);
        ketentuan = findViewById(R.id.ketentuan);

        //mengatur ke nilai integer awal
        textjumlahpesanan.setText(valuejumlahpesanan.toString()); //untuk tombol jumlah pesanan


        //set awal agar jumlah pesanan tidak 0
        btnminus.animate().alpha(0).setDuration(300).start(); //buttom minus hilang
        btnminus.setEnabled(false);
        icon_warning_bayar.setVisibility(View.GONE);

        //mengambil data dari firebase balance (data Users)
        reference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mybalance = Integer.valueOf(dataSnapshot.child("user_balance").getValue().toString());
                textmybalance.setText("US$ " + mybalance+ "");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //mengambil data dari Firebase berdasarkan Intent (data Wisata)
        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(jenis_tiket_baru);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //menimpa data yang ada dengan data yang baru
                nama_wisata.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                lokasi.setText(dataSnapshot.child("lokasi").getValue().toString());
                ketentuan.setText(dataSnapshot.child("ketentuan").getValue().toString());

                //memperbarui string dengan inputan di interface
                time_wisata = dataSnapshot.child("time_wisata").getValue().toString();
                date_wisata = dataSnapshot.child("date_wisata").getValue().toString();

                valuehargatiket = Integer.valueOf(dataSnapshot.child("harga_tiket").getValue().toString());

                valuetotalharga = valuehargatiket * valuejumlahpesanan; //untuk harga pesanan
                texttotalharga.setText("US$ " + valuetotalharga + "");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //penjumlahan jumlahan pesanan tiket
        btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valuejumlahpesanan += 1;
                textjumlahpesanan.setText(valuejumlahpesanan.toString());

                if (valuejumlahpesanan > 1){
                    btnminus.animate().alpha(1).setDuration(300).start(); //buttom minus tetap muncul
                    btnminus.setEnabled(true);
                }

                valuetotalharga = valuehargatiket * valuejumlahpesanan;
                texttotalharga.setText("US$ " + valuetotalharga + "");

                if (valuetotalharga > mybalance){
                    btn_pay_now.animate().translationY(250).alpha(0).setDuration(300)
                            .start();
                    btn_pay_now.setEnabled(false);
                    //buat warna peringatan
                    textmybalance.setTextColor(Color.parseColor("#D1206B"));
                    icon_warning_bayar.setVisibility(View.VISIBLE);
                }
            }
        });

        //pengurangan jumlahan pesanan tiket
        btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valuejumlahpesanan -= 1;
                textjumlahpesanan.setText(valuejumlahpesanan.toString());

                if (valuejumlahpesanan < 2){
                    btnminus.animate().alpha(0).setDuration(300).start(); //buttum minus hilang
                    btnminus.setEnabled(false);
                }

                valuetotalharga = valuehargatiket * valuejumlahpesanan;
                texttotalharga.setText("US$ " + valuetotalharga + "");

                if (valuetotalharga < mybalance){
                    btn_pay_now.animate().translationY(0).alpha(1).setDuration(300)
                            .start();
                    btn_pay_now.setEnabled(true);
                    //buat warna peringatan
                    textmybalance.setTextColor(Color.parseColor("#203DD1"));
                    icon_warning_bayar.setVisibility(View.GONE);
                }
            }
        });




        btn_pay_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //menyimpan data user ke firebase dan membuat tabel baru "MyTickets"
                reference3 = FirebaseDatabase.getInstance().getReference().child("MyTickets")
                        .child(username_key_new)
                        .child(nama_wisata.getText().toString() + nomor_transaksi);
                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        reference3.getRef().child("nama_wisata").setValue(nama_wisata.getText().toString());
                        reference3.getRef().child("id_ticket").setValue(nama_wisata.getText().toString() + nomor_transaksi);
                        reference3.getRef().child("lokasi").setValue(lokasi.getText().toString());
                        reference3.getRef().child("ketentuan").setValue(ketentuan.getText().toString());
                        reference3.getRef().child("jumlah_tiket").setValue(valuejumlahpesanan.toString());
                        reference3.getRef().child("time_wisata").setValue(time_wisata);
                        reference3.getRef().child("date_wisata").setValue(date_wisata);

                        Intent gotopay = new Intent(TicketCheckoutAct.this, SuccesBuyTicketAct.class);
                        startActivity(gotopay);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                //mengambil data dari firebase balance dg mengupdate data balance ke user
                reference4 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
                reference4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        sisa_balance = mybalance - valuetotalharga;
                        reference4.getRef().child("user_balance").setValue(sisa_balance);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    //penyimpanan file local
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
