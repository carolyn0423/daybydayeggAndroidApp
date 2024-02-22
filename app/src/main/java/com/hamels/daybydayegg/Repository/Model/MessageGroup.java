package com.hamels.daybydayegg.Repository.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class MessageGroup implements Parcelable {
    public static final String TAG = MessageGroup.class.getSimpleName();

    private String message_id = "0";
    private String member_id = "";
    private String name = "";
    private String mobile = "";
    private String message_date = "";
    private String message_content = "";
    private String reply_status = "";

    public String getId() {
        return message_id;
    }

    public String getMemberID() {
        return member_id;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getMessageDate(){
        return message_date;
    }

    public String getMessageContent(){
        return message_content;
    }

    public String getReplyStatus(){
        return reply_status;
    }

    protected MessageGroup(Parcel in) {
        message_id = in.readString();
        member_id = in.readString();
        name = in.readString();
        mobile = in.readString();
        message_date = in.readString();
        message_content = in.readString();
        reply_status = in.readString();
    }

    public static final Creator<MessageGroup> CREATOR = new Creator<MessageGroup>() {
        @Override
        public MessageGroup createFromParcel(Parcel in) {
            return new MessageGroup(in);
        }

        @Override
        public MessageGroup[] newArray(int size) {
            return new MessageGroup[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(message_id);
        dest.writeString(member_id);
        dest.writeString(name);
        dest.writeString(mobile);
        dest.writeString(message_date);
        dest.writeString(message_content);
        dest.writeString(reply_status);
    }
}
