package com.example.minimartv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListSale extends AppCompatActivity {
    ListView listView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    public String username;

    List<date> DateList=new ArrayList<>();

    date Datesale;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sale);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User");

        /////////รับค่า///////////////
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            username = bundle.getString("Username");
        }
        ////////////////////////

        Datesale = new date();
        listView = (ListView) findViewById(R.id.ListSale);
        prepareDate();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(ListSale.this,DateList.get(position).getTimestamp(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent( ListSale.this,showDaySale.class);
                intent.putExtra("DaySale",DateList.get(position).getTimestamp());
                intent.putExtra("Username",username);
                startActivity(intent);
            }
        });
    }

    private void prepareDate(){
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> DAY = new ArrayList<>();
                for(DataSnapshot ds: dataSnapshot.child(username).child("Saleshistory").getChildren()){
                    Datesale= ds.getValue(date.class);
                    DAY.add(Datesale.getTimestamp());

                }
                for(int i=0;i<DAY.size();i++){
                    date Datesale = new date(DAY.get(i));
                    DateList.add(Datesale);
                    DAY.remove(i);
                }
                ListAdapterSale adapter=new com.example.minimartv1.ListAdapterSale(ListSale.this,DateList);
                listView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });
    }
    public void BackToBasic(View v){
        Intent intent = new Intent(ListSale.this,MainAfterLogin.class);
        intent.putExtra("Username",username);
        startActivity(intent);
    }
}
