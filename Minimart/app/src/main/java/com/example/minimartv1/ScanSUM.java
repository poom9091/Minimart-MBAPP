package com.example.minimartv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanSUM extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    ZXingScannerView scannerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;
    public String username,count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref=firebaseDatabase.getReference("User/"+username+"/Bucket");
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        /////////รับค่า///////////////
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            username = bundle.getString("Username");
            count = bundle.getString("count");

        }
        ////////////////////////
    }
    @Override
    public void handleResult(Result result) {
        Intent i = new Intent(ScanSUM.this,BucketUser.class);
        i.putExtra("Username",username);
        i.putExtra("ID",result.getText());
        i.putExtra("count",count);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();

        scannerView.stopCamera();
    }
}
