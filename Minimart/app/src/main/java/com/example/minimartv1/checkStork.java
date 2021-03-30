package com.example.minimartv1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class checkStork extends AppCompatActivity {
    ListView listView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ArrayList<String> Pname = new ArrayList<>();
    ArrayList<String> PFprice = new ArrayList<>();
    ArrayList<String> PLprice = new ArrayList<>();
    ArrayList<String> PIMG = new ArrayList<>();
    ArrayList<String> Code = new ArrayList<>();
    ArrayList<String> countPro = new ArrayList<>();

    List<Product> ProductList=new ArrayList<>();


    Product product;
    public String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_stork);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User");

        /////////รับค่า///////////////
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            username = bundle.getString("Username");
        }
        ////////////////////////

        product = new Product();
        listView = (ListView) findViewById(R.id.listViewDetail);
        prepareDate();
        //ListAdapter adapter=new ListAdapter(BucketUser.this,ProductList);
    }

    private void prepareDate(){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.child(username).child("Product").getChildren()){
                    product= ds.getValue(Product.class);
                    Pname.add(product.getNamePro());
                    PFprice.add(product.getFprice());
                    PLprice.add(product.getLprice());
                    PIMG.add(product.getURL());
                    Code.add(product.getIDPro());//บาร์โค๊ต
                    countPro.add(product.getCount());//จำนวน
                }
                for(int i=0;i<Pname.size();i++){
                    Product product = new Product(Pname.get(i),PFprice.get(i),PLprice.get(i),PIMG.get(i),Code.get(i),countPro.get(i));
                    ProductList.add(product);
                }
                android.widget.ListAdapter adapter=new com.example.minimartv1.ListAdapter(checkStork.this,ProductList);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(checkStork.this,EditPro.class);
                        intent.putExtra("ID",ProductList.get(position).getIDPro());
                        intent.putExtra("Username",username);
                        startActivity(intent);
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });
    }
    public void BackToBasic(View v){
        Intent intent = new Intent(checkStork.this,MainAfterLogin.class);
        intent.putExtra("Username",username);
        startActivity(intent);
    }

}
