package com.example.yummyres;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;



import android.os.Bundle;
import android.util.Log;
import android.view.View;


import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;


import com.example.yummyres.models.Data;
import com.example.yummyres.models.Shop;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    //layout variable
    private Button button;
    private ListView resList;
    private SeekBar sbRange;
    TextInputEditText searchText;
    //google map variable
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    int zoom=14;
    //location variable
    double lat, lng;
    //Api variable
    List<Shop> shopList;
    int range=3;
    int count = 30;
    int order =3;
    String searchName="";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resList = findViewById(R.id.myRes);



        //<---SearchButton--->
        searchText = (TextInputEditText) findViewById(R.id.searchString);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchName = searchText.getEditableText().toString();
                Log.w("MyActivity", "test");
                onResume();
            }
        });
        //<---SearchButton---/>

        //<get the Range from seekBar>
        sbRange = (SeekBar)findViewById(R.id.sbRange);
        sbRange.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                range=i+1;
                switch(i){
                    case 0:
                        zoom=16;
                        break;
                    case 1:
                        zoom=15;
                        break;
                    case 2:
                        zoom=14;
                        break;
                    case 3:
                        zoom=13;
                        break;
                    case 4:
                        zoom=12;
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                onResume();
            }
        });
        //<get the Range from seekBar/>
    }

    @Override
    protected void onResume() {
        super.onResume();
        //<googleMap layout>
        //Assign variable in googleMap
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);

        //Initialize fused location
        client = LocationServices.getFusedLocationProviderClient(this);


        //Check permission
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //when permission grated
            //Call method
            getCurrentLoaction();


        } else {
            //when permission denied
            //Request permission
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        //<googleMap layout/>
    }

    public void getApi() {
        super.onResume();
        //<retrofitAPI>

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://webservice.recruit.co.jp/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Log.d("position2","lat"+lat);
        Call<Data> call;
        if(searchName==""){
            call = apiService.getData("bf067949c8390ff7", lat, lng,range,count,order,"json");
        }else{
            call = apiService.getDataFromName("bf067949c8390ff7", lat, lng,range,count,order,searchName,"json");
        }



        call.enqueue(new Callback<Data>() {

            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Log.d("order:","Good");
                shopList = response.body().results.getShop();
                //<add shop marker into map>
                supportMapFragment.getMapAsync(new OnMapReadyCallback(){
                    @Override
                    public void onMapReady(GoogleMap googleMap){
                        Log.w("order", "google map");
                        //reset the mark
                        googleMap.clear();
                        //add marker in ur position
                        LatLng latLng = new LatLng(lat, lng);
                        MarkerOptions options = new MarkerOptions().position(latLng)
                                .title("You");
                        googleMap.addMarker(options);

                        //add marker in shop address
                        for(int i =0 ; i <shopList.size();i++){
                            LatLng shopLatLng = new LatLng(shopList.get(i).getLat(), shopList.get(i).getLng());
                            MarkerOptions shopOptions = new MarkerOptions().position(shopLatLng)
                                    .title(shopList.get(i).getName());

                            googleMap.addMarker(shopOptions);

                        }

                    }
                });
                //<add marker into map/>

                //<Listview layout>
                ProgramAdapter programAdapter = new ProgramAdapter(MainActivity.this, shopList);

                resList.setAdapter(programAdapter);


                resList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        Shop currentShop = programAdapter.getItem(position);
                        Intent intent = new Intent(MainActivity.this, detail1.class);
                        intent.putExtra("currentShop",currentShop);
                        Log.d("click","currentShop:"+currentShop);

                        startActivity(intent);
                    }
                });
                //<Listview layout/>
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                button.setText(t.getMessage());
                Log.d("Fail", "onFailure: "+t.getMessage());
            }

        });


        //<retrofitAPI/>
    }

//<googleMap>
    private void getCurrentLoaction() {
        //Initialize task location
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(this,new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                //when success
                if(location != null){

                    lat = location.getLatitude();
                    lng = location.getLongitude();
                    Log.w("order", "lat:"+lat+"\nlng:"+lng);
                    //after get the Lat Lng
                    getApi();


                    //Sync map
                    supportMapFragment.getMapAsync(new OnMapReadyCallback(){
                        @Override
                        public void onMapReady(GoogleMap googleMap){
                            Log.w("order", "google map");
                            //Initialize Lat Lng
                            LatLng latLng = new LatLng(lat, lng);
                            //create marker options
                            MarkerOptions options = new MarkerOptions().position(latLng)
                                    .title("I am there");
                            //Zoom map
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
                            //Add marker on map
                            googleMap.addMarker(options);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) { // for googleMap
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //When permission grated
                //Call method
                getCurrentLoaction();
            }
        }
    }

    //<googleMap/>
}