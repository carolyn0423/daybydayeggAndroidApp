package com.hamels.daybydayegg.Repository.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Faq implements Parcelable {
    public static final String TAG = Faq.class.getSimpleName();
    public static final String STATUS_READ = "read";
    public static final String STATUS_DELETE = "delete";

    @SerializedName("faq_id")
    private int id = 0;
    private String faq_type_id = "";
    private String question = "";
    private String answer = "";


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFaq_type_id() {
        return faq_type_id;
    }

    public void setFaq_type_id(String faq_type_id) {
        this.faq_type_id = faq_type_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }



    protected Faq(Parcel in) {
        id = in.readInt();
        faq_type_id = in.readString();
        question = in.readString();
        answer = in.readString();
    }

    public static final Creator<Faq> CREATOR = new Creator<Faq>() {
        @Override
        public Faq createFromParcel(Parcel in) {
            return new Faq(in);
        }

        @Override
        public Faq[] newArray(int size) {
            return new Faq[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(faq_type_id);
        dest.writeString(question);
        dest.writeString(answer);

    }
}

