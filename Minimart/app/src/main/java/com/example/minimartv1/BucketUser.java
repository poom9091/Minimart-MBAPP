package com.example.minimartv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BucketUser extends AppCompatActivity {
    ListView listView;
    List<Product> ProductList = new ArrayList<>();

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    public DatabaseReference ref, Dele;
    TextView textView;
    EditText editText;

    Product product;
    BucketPay bucketPay;
    Saleshistory Shistory;

    Button pay;
    Button AddinB;
    public String username, ID, count;
    int a = 0, check = 0, c = 1, bath = 0;
    int amount = 1;
    int cOld = c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket_user);
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("User");


        /////////รับค่า///////////////
        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            username = bundle.getString("Username");
            ID = bundle.getString("ID");
            count = bundle.getString("count");

        }
        textView = findViewById(R.id.textView3);
        editText = findViewById(R.id.editText);
        ////////////////////////
        product = new Product();
        listView = (ListView) findViewById(R.id.listViewtxt);
        //ListAdapter adapter=new ListAdapter(BucketUser.this,ProductList);

        ref = firebaseDatabase.getReference("User");
        Dele = firebaseDatabase.getReference("User/" + username + "/Bucket");
        bucketPay = new BucketPay();
        Shistory = new Saleshistory();



        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.child(username).child("Product").getChildren()) {
                    product = ds.getValue(Product.class);
                    if (ID.equals(product.getIDPro())) {
                        amount = Integer.parseInt(count);
                        if (amount <= Integer.parseInt(product.getCount())) {
                            bucketPay.setNamePro(product.getNamePro());
                            bucketPay.setLprice(product.getLprice());
                            bucketPay.setIDPro(product.getIDPro());
                            bucketPay.setURL(product.getURL());
                            bucketPay.setCount(String.valueOf(amount));
                            ref.child(username).child("Bucket").child(ID).setValue(bucketPay);
                            check = 1;
                            amount = 1;
                            bath = 0;
                        } else
                            Toast.makeText(BucketUser.this, "มีสินค้าไม่เพียงพอ", Toast.LENGTH_SHORT).show();
                        check = 1;
                    }
                }
                prepareDate();
                if (check == 0 && !ID.equals("0")) {
                    Toast.makeText(BucketUser.this, "คุณไม่พมีข้อมูลินค้าชิ้นนี้", Toast.LENGTH_SHORT).show();
                }

            }

            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        pay = findViewById(R.id.Pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                product = new Product();
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        SimpleDateFormat sdf = new SimpleDateFormat("ddMM");
                        final String currentDateandTime = sdf.format(new Date());
                        for (DataSnapshot ds : dataSnapshot.child(username).child("Product").getChildren()) {
                            product = ds.getValue(Product.class);
                            int s=0;
                            for (DataSnapshot dsi : dataSnapshot.child(username).child("Bucket").getChildren()) {
                                bucketPay = dsi.getValue(BucketPay.class);
                                if (bucketPay.getIDPro().equals(product.getIDPro())) {
                                    int sum;
                                    s=Integer.parseInt(bucketPay.getCount());
                                    sum = Integer.parseInt(product.getCount()) - Integer.parseInt(bucketPay.getCount());
                                    product.setCount(String.valueOf(sum));
                                    product.setNamePro(product.getNamePro());
                                    product.setLprice(product.getLprice());
                                    product.setIDPro(product.getIDPro());
                                    product.setFprice(product.getFprice());
                                    product.setURL(product.getURL());

                                    bucketPay.setCount(bucketPay.getCount());
                                    bucketPay.setNamePro(product.getNamePro());
                                    bucketPay.setLprice(product.getLprice());
                                    bucketPay.setIDPro(product.getIDPro());
                                    bucketPay.setFprice(product.getFprice());
                                    bucketPay.setURL(product.getURL());

                                    Map map = new HashMap();
                                    map.put("timestamp",currentDateandTime);

                                    ref.child(username).child("Product").child(product.getIDPro()).setValue(product);
                                    ref.child(username).child("Saleshistory").child(currentDateandTime).updateChildren(map);
                                    ref.child(username).child("Saleshistory").child(currentDateandTime).child("Product").child(product.getIDPro()).setValue(bucketPay);
                                    for (DataSnapshot dss : dataSnapshot.child(username).child("Saleshistory").child(currentDateandTime).child("Product").getChildren()) {
                                        Shistory = dss.getValue(Saleshistory.class);
                                        if (Shistory.getIDPro().equals(bucketPay.getIDPro())) {
                                            int sum2;
                                            sum2 = Integer.parseInt(Shistory.getCount()) + s;
                                            Shistory.setCount(String.valueOf(sum2));
                                            Shistory.setNamePro(product.getNamePro());
                                            Shistory.setLprice(product.getLprice());
                                            Shistory.setIDPro(product.getIDPro());
                                            Shistory.setFprice(product.getFprice());
                                            Shistory.setURL(product.getURL());
                                            ref.child(username).child("Saleshistory").child(currentDateandTime).child("Product").child(product.getIDPro()).setValue(Shistory);
                                        }else{
                                            Shistory.setCount(String.valueOf(s));
                                            Shistory.setNamePro(product.getNamePro());
                                            Shistory.setLprice(product.getLprice());
                                            Shistory.setIDPro(product.getIDPro());
                                            Shistory.setFprice(product.getFprice());
                                            Shistory.setURL(product.getURL());
                                            ref.child(username).child("Saleshistory").child(currentDateandTime).child("Product").child(product.getIDPro()).setValue(Shistory);
                                        }
                                    }

                                }
                            }


                        }
                        Dele.removeValue();
                        Toast.makeText(BucketUser.this, "Complete Pay", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(BucketUser.this, MainAfterLogin.class);
                        i.putExtra("Username", username);
                        startActivity(i);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


    }

    //
//    private void getShow() {
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot ds : dataSnapshot.child(username).child("Bucket").getChildren()) {
//                    bucketPay = ds.getValue(BucketPay.class);
//                        arrayList.add("     " + bucketPay.getNamePro() + "        " + bucketPay.getLprice() + "      " + bucketPay.getCount());
//                        a = Integer.parseInt(bucketPay.getLprice()) * Integer.parseInt(bucketPay.getCount());
//                       bath += a;
//                        a = 0;
//                    }
//                    textView.setText(String.valueOf(bath));
//                    listView.setAdapter(arrayAdapter);
//                    bath = 0;
//               }
//           @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//           }
//        });
//    }


    public void ScanPay(View v){
        Intent intent = new Intent(BucketUser.this,ScanSUM.class);
        intent.putExtra("Username",username);
        intent.putExtra("count",editText.getText().toString());
        startActivity(intent);
    }

    private void prepareDate(){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> Pname = new ArrayList<>();
                ArrayList<String> PLprice = new ArrayList<>();
                ArrayList<String> PIMG = new ArrayList<>();
                ArrayList<String> Code = new ArrayList<>();
                ArrayList<String> countPro = new ArrayList<>();

                for(DataSnapshot ds: dataSnapshot.child(username).child("Bucket").getChildren()){
                    product= ds.getValue(Product.class);
                    Pname.add(product.getNamePro());
                    PLprice.add(product.getLprice());
                    PIMG.add(product.getURL());
                    Code.add(product.getIDPro());//บาร์โค๊ต
                    countPro.add(product.getCount());//จำนวน
                    a = Integer.parseInt(product.getLprice()) * Integer.parseInt(product.getCount());
                    bath += a;
                }
                for(int i=0;i<Pname.size();i++){
                    Product product = new Product(Pname.get(i),PLprice.get(i),PIMG.get(i),Code.get(i),countPro.get(i));
                    ProductList.add(product);

                }
                ListAdapterPay adapter=new com.example.minimartv1.ListAdapterPay(BucketUser.this,ProductList);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String text = ProductList.get(position).getIDPro();
                        Toast.makeText(BucketUser.this,"ลบรายการ"+text+" สำเร็จ",Toast.LENGTH_LONG).show();
                        Dele.child(text).removeValue();
                        Intent intent = new Intent(BucketUser.this,BucketUser.class);
                        intent.putExtra("Username",username);
                        intent.putExtra("ID","0");
                        intent.putExtra("count","1");
                        startActivity(intent);
                    }
                });
                a = 0;
                textView.setText(String.valueOf(bath));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });
   }
    public void BackToBasic(View v){
        Dele.removeValue();
        Intent intent = new Intent(BucketUser.this,MainAfterLogin.class);
        intent.putExtra("Username",username);
        startActivity(intent);
    }
}
