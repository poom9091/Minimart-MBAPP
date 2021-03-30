package com.example.minimartv1;

import java.util.ArrayList;
import java.util.List;

public class Saleshistory {

    private String IDPro;
    private String NamePro;
    private String Fprice;
    private String Lprice;
    private String Count;
    private String URL;
    List<Product> bikeList=new ArrayList<>();

    public List<Product> getBikeList() {
        return bikeList;
    }

    public void setBikeList(List<Product> bikeList) {
        this.bikeList = bikeList;
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

    public Saleshistory(){
        this.IDPro=null;
        this.NamePro=null;
        this.Fprice=null;
        this.Lprice=null;
        this.Count=null;
        this.URL=null;
    }
    public Saleshistory(String namePro,String lprice,String url){
        this.NamePro=namePro;
        this.Lprice=lprice;
        this.URL=url;
    }
    public Saleshistory(String namePro,String lprice,String url,String Code,String countPro){
        this.NamePro=namePro;
        this.Lprice=lprice;
        this.URL=url;
        this.IDPro=Code;//บาร์โค๊ต
        this.Count=countPro;//จำนวน
    }

}

