package com.hamels.daybydayegg.Repository.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MemberMessage implements Parcelable {
    public static final String TAG = MemberMessage.class.getSimpleName();
    public static final String STATUS_READ = "read_flag";
    public static final String STATUS_DELETE = "delete_flag";

    @SerializedName("push_id")
    private int id = 0;
    private String title = "";
    private String content = "";
    @SerializedName("schedule_datetime")
    private String completedAt = "";
    private String read_flag = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

    public String getRead_flag(){
        return read_flag;
    }

    public void setRead_flag(String read_flag){
        this.read_flag = read_flag;
    }

    protected MemberMessage(Parcel in) {
        id = in.readInt();
        title = in.readString();
        content = in.readString();
        completedAt = in.readString();
        read_flag = in.readString();
    }

    public static final Creator<MemberMessage> CREATOR = new Creator<MemberMessage>() {
        @Override
        public MemberMessage createFromParcel(Parcel in) {
            return new MemberMessage(in);
        }

        @Override
        public MemberMessage[] newArray(int size) {
            return new MemberMessage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(completedAt);
        dest.writeString(read_flag);
    }
}
