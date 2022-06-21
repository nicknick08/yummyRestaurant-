package com.example.yummyres.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//Data from api
public class Data {
    @SerializedName("results")
    @Expose
    public Results results;

    public Data(Results results){
        this.results = results;
    }
}
