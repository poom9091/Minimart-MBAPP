package com.example.minimartv1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ListAdapterSale  extends BaseAdapter {
    private List<date> DateList = new ArrayList<>();
    private Context context;

    ListAdapterSale(Context context, List<date> ProductList) {
        this.context = context;
        this.DateList = ProductList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.list_sale, parent, false);

        TextView tvBike = view.findViewById(R.id.tvBikemodel);
        tvBike.setText(DateList.get(position).getTimestamp());

        return view;
    }

    public int getCount() {
        if (DateList == null)
            return 0;
        return DateList.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }
}
