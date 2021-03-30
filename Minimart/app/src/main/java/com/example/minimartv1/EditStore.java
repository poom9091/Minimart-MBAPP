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

import java.util.ArrayList;

public class EditStore extends AppCompatActivity {
    String username= "";
    EditText edtName , edtNewPassword , edtEmail;
    Button Submit , Back;
    FirebaseDatabase database;
    DatabaseReference ref;
    User user;

    ArrayList<String> NameUserList= new ArrayList<>();
    ArrayList<String> EmailUserList= new ArrayList<>();
    ArrayList<String> PassUserList= new ArrayList<>();

    public String Name = "";
    TextView UserName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_store);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("User");
        user = new User();


        Submit = (Button)findViewById(R.id.submit);
        UserName = (TextView) findViewById(R.id.txtUsername);
        edtName = (EditText) findViewById(R.id.edtName);
        edtEmail = (EditText) findViewById(R.id.email);
        edtNewPassword = (EditText) findViewById(R.id.edtNewPassword);

        // GET USERNAME of MainAfterLogin//
        Bundle bundle  = getIntent().getExtras();
        if(bundle != null){
            username = bundle.getString("Username");
        }

        UserName.setText(username);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(username).exists()) {
                    User user = dataSnapshot.child(username).getValue(User.class);
                    edtName.setText(user.getName());
                    edtEmail.setText(user.getEmail());
                    edtNewPassword.setText(user.getPassword());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void getValues() {
        user.setName(edtName.getText().toString());
        user.setEmail(edtEmail.getText().toString());
        user.setPassword(edtNewPassword.getText().toString());
    }

    public void submit(View view) {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //getValues();

                ref.child(UserName.getText().toString()).child("name").setValue(edtName.getText().toString());
                ref.child(UserName.getText().toString()).child("email").setValue(edtEmail.getText().toString());
                ref.child(UserName.getText().toString()).child("password").setValue(edtNewPassword.getText().toString());

                //ref.child(UserName.getText().toString()).setValue(user);


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EditStore.this,"ผิดพลาด",Toast.LENGTH_LONG).show();
            }
        });
        Toast.makeText(EditStore.this,"แก้ไขข้อมูลสำเร็จ",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(EditStore.this,MainAfterLogin.class);
        intent.putExtra("Username",username);
        startActivity(intent);
    }
    /*
    public void back(){
        Intent intent = new Intent(EditStore.this,MainAfterLogin.class);
        intent.putExtra("Username",username);
        startActivity(intent);
        //super.onBackPressed();
    }
     */
}
