package com.example.auctionleb.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.auctionleb.R;


public class CarsViewholder extends RecyclerView.ViewHolder {

     public TextView title,price, model,year;
     public ImageView imageURL;


    public CarsViewholder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.Item_title);
        price = itemView.findViewById(R.id.Item_price);
        model = itemView.findViewById(R.id.Item_model);
        year = itemView.findViewById(R.id.Item_year);
        imageURL = itemView.findViewById(R.id.Item_image);
    }
}
