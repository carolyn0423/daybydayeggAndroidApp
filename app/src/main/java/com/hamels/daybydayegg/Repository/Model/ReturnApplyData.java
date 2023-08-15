package com.hamels.daybydayegg.Repository.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class ReturnApplyData implements Parcelable {
    public static final String TAG = ReturnApplyData.class.getSimpleName();

    private String name;
    private String address;
    private String tel;
    private String mobile;
    private String email;
    private String bankName;
    private String bankAccount;
    private String bankAccountName;
    private String today;
    private String type;
    private String note;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    protected ReturnApplyData(Parcel in) {
        name = in.readString();
        address = in.readString();
        tel = in.readString();
        mobile = in.readString();
        email = in.readString();
        bankName = in.readString();
        bankAccount = in.readString();
        bankAccountName = in.readString();
        today = in.readString();
        type = in.readString();
        note = in.readString();
    }

    public static final Creator<ReturnApplyData> CREATOR = new Creator<ReturnApplyData>() {
        @Override
        public ReturnApplyData createFromParcel(Parcel in) {
            return new ReturnApplyData(in);
        }

        @Override
        public ReturnApplyData[] newArray(int size) {
            return new ReturnApplyData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(tel);
        dest.writeString(mobile);
        dest.writeString(email);
        dest.writeString(bankName);
        dest.writeString(bankAccount);
        dest.writeString(bankAccountName);
        dest.writeString(today);
        dest.writeString(type);
        dest.writeString(note);
    }
}
