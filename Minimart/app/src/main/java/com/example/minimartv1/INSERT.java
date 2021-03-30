package com.example.minimartv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class INSERT extends AppCompatActivity {
    public String username;
    public FirebaseDatabase database;
    public DatabaseReference ref;
    StorageReference storageReference;
    public static TextView textView;
    public EditText NamePro;
    public EditText Fprice;
    public EditText Lprice;
    public EditText Count;
    public Button bIMG;
    public ImageView imageView;
    public Uri imageUri;
    Uri FilePathUri;
    public String URL_IMG="asd";
    public TextView textURL;
    Product Product;
    private int REQUEST_CODE = 1;
    ProgressDialog progressDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        storageReference = FirebaseStorage.getInstance().getReference("Images");
        imageView=findViewById(R.id.imageView);
        textView=findViewById(R.id.textviewName);
        NamePro=findViewById(R.id.NameProE);
        Fprice=findViewById(R.id.FpriceE);
        Lprice=findViewById(R.id.LpriceE);
        Count=findViewById(R.id.CountE);

        /////////รับค่า///////////////
        Bundle bundle  =getIntent().getExtras();
        if(bundle != null){
            username=bundle.getString("Username");
            textView.setText(bundle.getString("ID"));
        }
        ////////////////////////

        database =FirebaseDatabase.getInstance();
        ref=database.getReference("User/"+username+"/Product");
        Product=new Product();

        bIMG=findViewById(R.id.button4);
        bIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent,REQUEST_CODE);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){

        super.onActivityResult(requestCode,resultCode,data);

        if (resultCode == RESULT_OK) {
            try {
                imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }


    private void getValues(){
        Product.setIDPro(textView.getText().toString());
        Product.setNamePro(NamePro.getText().toString());
        Product.setFprice(Fprice.getText().toString());
        Product.setLprice(Lprice.getText().toString());
        Product.setCount(Count.getText().toString());
        Product.setURL(URL_IMG);
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
            Toast.makeText(getApplicationContext(),"กรุณารอสักครู่ ระบบกำลังเพิ่มข้อมูล", Toast.LENGTH_LONG).show();
            upLoadImg();

        }catch (NumberFormatException ex){
            Toast.makeText(getApplicationContext(),"กรุณากรอกราคาทุนกับราคาขายเป็นตัวเลข",Toast.LENGTH_LONG).show();
            return;
        }

    }

    public void upLoadImg(){

        if (imageUri != null) {

            final StorageReference storageReference2 = storageReference.child(username).child("Product/"+textView.getText().toString());
            storageReference2.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //URL_IMG= taskSnapshot.getStorage().getDownloadUrl().toString();
                            storageReference.child(username).child("Product/"+textView.getText().toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                   URL_IMG=String.valueOf(uri);
                                    Toast.makeText(getApplicationContext(),"เพิ่มข้อมูลเสร็จสิน",Toast.LENGTH_LONG).show();
                                    ref.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            getValues();
                                            ref.child(textView.getText().toString()).setValue(Product);
                                        Intent intent = new Intent(INSERT.this,MainAfterLogin.class);
                                        intent.putExtra("Username",username);
                                        startActivity(intent);
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }
                            });
                        }
                    });
        }
        else {

            Toast.makeText(this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
        //Toast.makeText(this, URL_IMG,Toast.LENGTH_LONG).show();
    }
    public void BackToBasic(View v){
        Intent intent = new Intent(INSERT.this,MainAfterLogin.class);
        intent.putExtra("Username",username);
        startActivity(intent);
    }

}
