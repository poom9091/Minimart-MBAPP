package com.example.minimartv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class showDaySale extends AppCompatActivity {
    ListView listView;
    TextView sumAll;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    List<Product> ProductList=new ArrayList<>();
    Integer sum=0;

    public DatabaseReference Dele;
    Product product;
    public String username;
    public String DaySale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_day_sale);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User");
        sumAll = findViewById(R.id.textView15);
        product = new Product();
        listView = (ListView) findViewById(R.id.ListShowDay);
        prepareDate();


        /////////รับค่า///////////////
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            username = bundle.getString("Username");
            DaySale = bundle.getString("DaySale");
        }
        ////////////////////////
    }

    private void prepareDate(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> Pname = new ArrayList<>();
                ArrayList<String> PFprice = new ArrayList<>();
                ArrayList<String> PLprice = new ArrayList<>();
                ArrayList<String> PIMG = new ArrayList<>();
                ArrayList<String> Code = new ArrayList<>();
                ArrayList<String> countPro = new ArrayList<>();
                for(DataSnapshot ds: dataSnapshot.child(username).child("Saleshistory").child(DaySale).child("Product").getChildren()){
                    product= ds.getValue(Product.class);
                    Pname.add(product.getNamePro());
                    PFprice.add(product.getFprice());
                    PLprice.add(product.getLprice());
                    PIMG.add(product.getURL());
                    Code.add(product.getIDPro());//บาร์โค๊ต
                    countPro.add(product.getCount());//จำนวน
                    sum += (Integer.parseInt(product.getLprice()) - Integer.parseInt(product.getFprice()))* Integer.parseInt(product.getCount());
                }
                for(int i=0;i<Pname.size();i++){
                    Product product = new Product(Pname.get(i),PFprice.get(i),PLprice.get(i),PIMG.get(i),Code.get(i),countPro.get(i));
                    ProductList.add(product);

                }
                ListAdapterDatsale adapter=new com.example.minimartv1.ListAdapterDatsale(showDaySale.this,ProductList);
                listView.setAdapter(adapter);
                sumAll.setText(String.valueOf(sum));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });
    }
    public void Del(View v){
        Dele = firebaseDatabase.getReference("User/" + username + "/Saleshistory/"+DaySale);
        Dele.removeValue();
        Toast.makeText(showDaySale.this, "Delete Complete", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(showDaySale.this,MainAfterLogin.class);
        intent.putExtra("Username",username);
        startActivity(intent);
    }
    public void BackToBasic(View v){
        Intent intent = new Intent(showDaySale.this,MainAfterLogin.class);
        intent.putExtra("Username",username);
        startActivity(intent);
    }

}
