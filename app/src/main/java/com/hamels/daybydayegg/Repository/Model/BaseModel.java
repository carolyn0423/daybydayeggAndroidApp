package com.hamels.daybydayegg.Repository.Model;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class BaseModel<T> {
    public static final String TAG = BaseModel.class.getSimpleName();

    private String link;
    private String method;
    private int code;
    private String Message;
    private T Data;
    private boolean isSuccess;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public T getItems() {
        return Data;
    }

    public void setItems(T Data) {
        this.Data = Data;
    }

    @NonNull
    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("link", link);
            jsonObject.put("method", method);
            jsonObject.put("code", code);
            jsonObject.put("isSuccess", isSuccess);
            jsonObject.put("Message", Message);
            jsonObject.put("Data", Data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }
}
