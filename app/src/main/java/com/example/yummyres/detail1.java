package com.example.yummyres;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentProviderOperation;
import android.os.Bundle;

import android.view.textclassifier.TextLinks;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yummyres.models.Shop;
import com.squareup.picasso.Picasso;


public class detail1 extends AppCompatActivity {

    private ImageView img;

    private TextView tName, tAddress,tOpenTime;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail1);

        tName = (TextView) findViewById(R.id.name);
        tAddress = (TextView) findViewById(R.id.address);
        img = (ImageView) findViewById(R.id.image);
        Shop shop = getIntent().getParcelableExtra("currentShop");
        tName.setText("店名："+shop.getName());
        tAddress.setText("住所："+shop.getAddress());
        Picasso.get()
                .load(shop.photo.mobile.getL())
                .into(img);
        tOpenTime = findViewById(R.id.open);
        tOpenTime.setText("営業時間：\n"+shop.getOpen());


    }





}