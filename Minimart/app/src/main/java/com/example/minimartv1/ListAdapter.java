package com.example.minimartv1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter {
    private List<Product> ProductList=new ArrayList<>();
    private Context context;

    ListAdapter(Context context,List<Product> ProductList){
        this.context=context;
        this.ProductList=ProductList;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View view;
        LayoutInflater inflater=(LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.list_item,parent,false);


        ImageView imageView=view.findViewById(R.id.image);
        TextView tvBike=view.findViewById(R.id.tvBikemodel);
        TextView tvFprice =view.findViewById(R.id.tvFprice);
        TextView tvDescription=view.findViewById(R.id.tvDescription);

        TextView textAmoung=view.findViewById(R.id.textAmoung);


        String url = ProductList.get(position).getURL();
        Glide.with(context)
                .load(url)
                .override(300, 200) // resizes the image to these dimensions (in pixel)
                .centerCrop()
                .into(imageView);

        tvBike.setText(ProductList.get(position).getNamePro());
        tvFprice.setText(ProductList.get(position).getFprice() );
        tvDescription.setText(ProductList.get(position).getLprice() );

        textAmoung.setText(ProductList.get(position).getCount());

        return view;
    }
    public int getCount(){
        if(ProductList==null)
            return 0;
        return ProductList.size();
    }
    public Object getItem(int position){
        return null;
    }
    public long getItemId(int position){
        return 0;
    }

}