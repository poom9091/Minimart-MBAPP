package com.example.minimartv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Main2Activity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView scannerView;
    public String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView=new ZXingScannerView(this);
        setContentView(scannerView);
        /////////รับค่า///////////////
        Bundle bundle  =getIntent().getExtras();
        if(bundle != null){
            username=bundle.getString("Username");
        }
        ////////////////////////
    }

    @Override
    public void handleResult(Result result) {
        Intent i = new Intent(Main2Activity.this,INSERT.class);
        i.putExtra("Username",username);
        i.putExtra("ID",result.getText());
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
