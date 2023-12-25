package com.hamels.daybydayegg.Repository.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Address implements Parcelable {
    public static final String TAG = Address.class.getSimpleName();
    public static final String STATUS_READ = "read";
    public static final String STATUS_DELETE = "delete";

    @SerializedName("ID")
    private int id = 0;
    private String Zip = "";
    private String City = "";
    private String Area = "";

    public Address(String City, String Area) {
        if(!City.isEmpty()) this.City = City;
        if(!Area.isEmpty()) this.Area = Area;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getZip() {
        return Zip;
    }

    public void setZip(String Zip) {
        this.Zip = Zip;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String Area) {
        this.Area = Area;
    }

    protected Address(Parcel in) {
        id = in.readInt();
        Zip = in.readString();
        City = in.readString();
        Area = in.readString();
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(Zip);
        dest.writeString(City);
        dest.writeString(Area);
    }

    public String toData() {
        if(City.isEmpty()){
            return Area;
        }else{
            return City;
        }
    }
}

