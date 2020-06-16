package com.example.auctionleb.models;

public class CarsInfo {

    String model, price, title;

    public CarsInfo() {
    }

    public CarsInfo(String model, String price, String title) {
        this.model = model;
        this.price = price;
        this.title = title;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
