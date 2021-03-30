package com.example.minimartv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {
    EditText tEmail,tPassword;
    Button btnLogin,btnRegister;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tEmail = (EditText)findViewById(R.id.tEmail);
        tPassword = (EditText)findViewById(R.id.tPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = null;
                try {
                    pwd = tPassword.getText().toString();

                }catch (Exception e){
                    e.printStackTrace();
                }
                login(tEmail.getText().toString(),pwd);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btnregister = (Button)findViewById(R.id.btnRegister);
                Intent intent = new Intent(MainActivity.this,Register.class);
                startActivity(intent);
            }
        });

    }

    public void login(final String username,final String password){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(username).exists()) {
                    if (!username.isEmpty()) {
                        User user = dataSnapshot.child(username).getValue(User.class);
                        if (user.getPassword().equals(password)) {
                            Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                            ///////////ส่งEmail/////////////////////
                            Intent intent = new Intent(MainActivity.this,MainAfterLogin.class);
                            intent.putExtra("Username",username);
                            startActivity(intent);
                            ////////////////////////////////

                        } else {
                            Toast.makeText(MainActivity.this, "Password Incorrect", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "User is not register", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(MainActivity.this, "User is not register", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
