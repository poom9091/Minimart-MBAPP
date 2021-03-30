package com.example.minimartv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainAfterLogin extends AppCompatActivity {
    Button buttonADDPro;
    Button CheckPro;
    TextView Namestore;
    Button Sum;
    Button EditUser;
    Button Logout;
    public String username;

    FirebaseDatabase database;
    DatabaseReference ref;
    User user;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<Product> ProductList=new ArrayList<>();
    ArrayList<String> model = new ArrayList<>();
    ArrayList<String> description = new ArrayList<>();

    Product product;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_after_login);
        buttonADDPro=findViewById(R.id.button);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("User");
        user = new User();
        Namestore = findViewById(R.id.nameStore);
        /////////รับค่า///////////////
        Bundle bundle  =getIntent().getExtras();
        if(bundle != null){
            username=bundle.getString("Username");
        }
        ////////////////////////

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(username).exists()) {
                    User user = dataSnapshot.child(username).getValue(User.class);
                    Namestore.setText(user.getName());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        buttonADDPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///////////ส่งEmail/////////////////////
                Intent intent = new Intent(MainAfterLogin.this,Main2Activity.class);
                intent.putExtra("Username",username);
                startActivity(intent);
                ////////////////////////////////

            }
        });

        CheckPro=findViewById(R.id.CheckPro);
        CheckPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainAfterLogin.this,checkStork.class);
                intent.putExtra("Username",username);
                startActivity(intent);
            }
        });

        Sum=findViewById(R.id.Sum);
        Sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainAfterLogin.this,BucketUser.class);
                intent.putExtra("Username",username);
                intent.putExtra("ID","0");
                intent.putExtra("count","1");
                startActivity(intent);
            }
        });

        EditUser = findViewById(R.id.EDITUSER);
        EditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainAfterLogin.this,EditStore.class);
                intent.putExtra("Username",username);
                startActivity(intent);
            }
        });

    }
    public void SaleHistory(View v){
        Intent intent = new Intent(MainAfterLogin.this,ListSale.class);
        intent.putExtra("Username",username);
        startActivity(intent);
    }

    public void Logout (View view)
    {
        Intent intent = new Intent( this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(MainAfterLogin.this,"ออกจากระบบ",Toast.LENGTH_LONG).show();
        startActivity(new Intent(MainAfterLogin.this,MainActivity.class));
    }



}
