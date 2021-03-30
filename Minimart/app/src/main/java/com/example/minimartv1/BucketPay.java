package com.example.minimartv1;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BucketPay {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private String NamePro;
    private String Fprice;
    private String Lprice;
    private String Count;
    private String URL;
    private String IDPro;
    private String Num;

    public String getNum() {
        return Num;
    }

    public void setNum(String Num) {
        this.Num = Num;
    }

    public String getIDPro() {
        return IDPro;
    }

    public void setIDPro(String IDPro) {
        this.IDPro = IDPro;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getNamePro() {
        return NamePro;
    }

    public void setNamePro(String namePro) {
        NamePro = namePro;
    }

    public String getFprice() {
        return Fprice;
    }

    public void setFprice(String fprice) {
        Fprice = fprice;
    }

    public String getLprice() {
        return Lprice;
    }

    public void setLprice(String lprice) {
        Lprice = lprice;
    }

    public String getCount() {
        return Count;
    }

    public void setCount(String count) {
        Count = count;
    }

    public String toShow(){
        return NamePro+"   "+Fprice+"   "+Lprice+"   "+Count;
    }

    public BucketPay() {
    }
}
