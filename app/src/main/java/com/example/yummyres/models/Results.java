package com.example.yummyres.models;



import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//the results from api in data
public class Results {

    @SerializedName("api_version")
    @Expose
    private String apiVersion;
    @SerializedName("results_available")
    @Expose
    private Integer resultsAvailable;
    @SerializedName("results_returned")
    @Expose
    private String resultsReturned;
    @SerializedName("results_start")
    @Expose
    private Integer resultsStart;
    @SerializedName("shop")
    @Expose
    public ArrayList<Shop> shop = null;

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public Integer getResultsAvailable() {
        return resultsAvailable;
    }

    public void setResultsAvailable(Integer resultsAvailable) {
        this.resultsAvailable = resultsAvailable;
    }

    public String getResultsReturned() {
        return resultsReturned;
    }

    public void setResultsReturned(String resultsReturned) {
        this.resultsReturned = resultsReturned;
    }

    public Integer getResultsStart() {
        return resultsStart;
    }

    public void setResultsStart(Integer resultsStart) {
        this.resultsStart = resultsStart;
    }

    public ArrayList<Shop> getShop() {
        return shop;
    }

    public void setShop(ArrayList<Shop> shop) {
        this.shop = shop;
    }

}
