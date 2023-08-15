package com.hamels.daybydayegg.Repository.Model;

public class ProductConfAmout {

    private String sIDList;
    private String confname;
    private String qty;
    private String price;
    private String soldout_today;

    public ProductConfAmout(String sIDList, String confname, String qty, String price) {
        this.sIDList = sIDList;
        this.confname = confname;
        this.qty = qty;
        this.price = price;
    }

    public void setQty(String sQty) {
        this.qty = sQty;
    }

    public String getsIDList() {
        return sIDList;
    }

    public String getConfname() {
        return confname;
    }

    public String getQty() {
        return qty;
    }

    public String getPrice() {
        return price;
    }
}