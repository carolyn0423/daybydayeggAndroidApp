package com.hamels.daybydayegg.Repository.Model;

public class OftenMobile {
    private String key;
    private String value;
    private String mobile;
    private String nick;

    public OftenMobile(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        if(key.isEmpty()){
            return value;
        }else if(value.isEmpty()){
            return key;
        }else{
            return key + " - " + value;
        }
    }
}
