package com.example.minimartv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class EditPro extends AppCompatActivity {
    public String username,ID;
    public FirebaseDatabase database;
    public DatabaseReference ref;
    StorageReference storageReference;
    public static TextView textView;
    public EditText NamePro;
    public EditText Fprice;
    public EditText Lprice;
    public EditText Count;
    public Button bIMG;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    public String URL_IMG="asd";
    public TextView textURL;
    private Context context;
    Product Product;


    private int REQUEST_CODE = 1;
    ProgressDialog progressDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pro);


        storageReference = FirebaseStorage.getInstance().getReference("Images");


        textView=findViewById(R.id.textviewName);
        NamePro=findViewById(R.id.NameProE);
        Fprice=findViewById(R.id.FpriceE);
        Lprice=findViewById(R.id.LpriceE);
        Count=findViewById(R.id.CountE);

        /////////รับค่า///////////////
        Bundle bundle  =getIntent().getExtras();
        if(bundle != null){
            username=bundle.getString("Username");
            ID = bundle.getString("ID");
        }
        textView.setText(ID);
        ////////////////////////
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User");
        getFirst();

        database =FirebaseDatabase.getInstance();
        ref=database.getReference("User/"+username+"/Product");
        Product=new Product();

    }

    private void getValues(){
        Product.setIDPro(textView.getText().toString());
        Product.setNamePro(NamePro.getText().toString());
        Product.setFprice(Fprice.getText().toString());
        Product.setLprice(Lprice.getText().toString());
        Product.setCount(Count.getText().toString());
        Product.setURL(URL_IMG);
    }
    private void getFirst(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ImageView imageView=findViewById(R.id.imageButton);
                for(DataSnapshot ds: dataSnapshot.child(username).child("Product").getChildren()) {
                    Product = ds.getValue(Product.class);
                    if (ID.equals(Product.getIDPro())) {
                        NamePro.setText(Product.getNamePro());
                        Fprice.setText(Product.getFprice());
                        Lprice.setText(Product.getLprice());
                        Count.setText(Product.getCount());
                        URL_IMG = Product.getURL();

                        Glide.with(getApplicationContext())
                                .load(URL_IMG)
                                .override(500, 500) // resizes the image to these dimensions (in pixel)
                                .centerCrop()
                                .into(imageView);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void insert(View view){

        String strNamePro = NamePro.getText().toString();
        String strFprice = Fprice.getText().toString();
        String strLprice = Lprice.getText().toString();
        String strCount = Count.getText().toString();


        if(strNamePro.equals("")&&strFprice.equals("")&&strLprice.equals("")&&strCount.equals("")) {
            Toast.makeText(getApplicationContext(),"กรุณากรอกข้อมูลให้ครบ",Toast.LENGTH_LONG).show();
            return;
        }try{
            int Fnum = Integer.parseInt(strFprice);
            int Lnum = Integer.parseInt(strLprice);
            if(Fnum>Lnum){
                Toast.makeText(getApplicationContext(),"คุณกรอกเราคาทุนมากว่าราคาขาย",Toast.LENGTH_LONG).show();
                return;
            }
            upLoadImg();

        }catch (NumberFormatException ex){
            Toast.makeText(getApplicationContext(),"กรุณากรอกราคาทุนกับราคาขายเป็นตัวเลข",Toast.LENGTH_LONG).show();
            return;
        }

    }

    public void upLoadImg(){
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getValues();
                ref.child(textView.getText().toString()).setValue(Product);
                Toast.makeText(getApplicationContext(),"เแก้ไขข้อมูลเสร็จสิน",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(EditPro.this,checkStork.class);
                intent.putExtra("Username",username);
                startActivity(intent);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void BackToBasic(View v){
        Intent intent = new Intent(EditPro.this,checkStork.class);
        intent.putExtra("Username",username);
        startActivity(intent);
    }
    public void Del(View v){
        ref.child(ID).removeValue();
        Intent intent = new Intent(EditPro.this,checkStork.class);
        intent.putExtra("Username",username);
        startActivity(intent);
        Toast.makeText(this, "ลบข้อมูลเสร็จสิน", Toast.LENGTH_LONG).show();
    }

}

