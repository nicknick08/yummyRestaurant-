package com.example.yummyres;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.yummyres.models.Data;
import com.example.yummyres.models.Shop;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Callback;

public class ProgramAdapter extends ArrayAdapter<Shop> {


    public ProgramAdapter(Activity context, List<Shop> resource) {
        super(context, 0,resource);

    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        //prevent the list is null
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.singe_item,parent,false);
        }
        //find the data
        Shop currentShop = getItem(position);

        //put into the layout
        TextView tName = listItemView.findViewById(R.id.itemName);
        tName.setText(currentShop.getName());
        TextView tAccess = listItemView.findViewById(R.id.itemAccess);
        tAccess.setText(currentShop.getAccess());
        ImageView img = listItemView.findViewById(R.id.samure);
        Picasso.get()
                .load(currentShop.photo.mobile.getL())
                .into(img);
        return listItemView;
    }
}
