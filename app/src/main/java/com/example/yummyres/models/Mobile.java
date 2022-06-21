
package com.example.yummyres.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Mobile implements Parcelable {

    @SerializedName("l")
    @Expose
    private String l;
    @SerializedName("s")
    @Expose
    private String s;

    protected Mobile(Parcel in) {
        l = in.readString();
        s = in.readString();
    }

    public static final Creator<Mobile> CREATOR = new Creator<Mobile>() {
        @Override
        public Mobile createFromParcel(Parcel in) {
            return new Mobile(in);
        }

        @Override
        public Mobile[] newArray(int size) {
            return new Mobile[size];
        }
    };

    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(l);
        parcel.writeString(s);
    }
}
