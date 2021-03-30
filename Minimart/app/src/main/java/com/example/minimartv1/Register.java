package com.example.minimartv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {
    EditText name,email,username,password;
    Button insert;
    FirebaseDatabase database;
    DatabaseReference ref;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = (EditText)findViewById(R.id.name);
        email = (EditText)findViewById(R.id.email);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        insert = (Button)findViewById(R.id.btnInsert);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("User");
        user = new User();

    }
    private void getValues(){
        user.setName(name.getText().toString());
        user.setPassword(password.getText().toString());
        user.setEmail(email.getText().toString());
    }
    public void btnInsert(View view) {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getValues();
                ref.child(username.getText().toString()).setValue(user);
                Toast.makeText(Register.this,"เพิ่มข้อมูลสำเร็จ",Toast.LENGTH_LONG).show();
                startActivity(new Intent(Register.this,MainActivity.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

