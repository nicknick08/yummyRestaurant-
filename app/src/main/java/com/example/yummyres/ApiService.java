package com.example.yummyres;

import com.example.yummyres.models.Data;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/hotpepper/gourmet/v1/")
    Call<Data> getData(@Query("key") String key,
                       @Query("lat") double lat,
                       @Query("lng") double lng,
                       @Query("range") int range,
                       @Query("count") int count,
                       @Query("order") int order,
                       @Query("format") String format);


    @GET("/hotpepper/gourmet/v1/")
    Call<Data> getDataFromName(@Query("key") String key,
                               @Query("lat") double lat,
                               @Query("lng") double lng,
                               @Query("range") int range,
                               @Query("count") int count,
                               @Query("order") int order,
                               @Query("keyword") String keyword,
                               @Query("format") String format);
}
