package com.example.testdatabaseimport;

public class Bids {
    String bidId, title, condition, decription, model, bidStartingPrice, nowPrice, year;

    public Bids() {
    }

    public Bids(String bidId, String title, String condition, String decription, String model, String bidStartingPrice, String nowPrice, String year) {
        this.bidId = bidId;
        this.title = title;
        this.condition = condition;
        this.decription = decription;
        this.model = model;
        this.bidStartingPrice = bidStartingPrice;
        this.nowPrice = nowPrice;
        this.year = year;
    }

    public String getBidId() {
        return bidId;
    }

    public void setBidId(String bidId) {
        this.bidId = bidId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBidStartingPrice() {
        return bidStartingPrice;
    }

    public void setBidStartingPrice(String bidStartingPrice) {
        this.bidStartingPrice = bidStartingPrice;
    }

    public String getNowPrice() {
        return nowPrice;
    }

    public void setNowPrice(String nowPrice) {
        this.nowPrice = nowPrice;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
