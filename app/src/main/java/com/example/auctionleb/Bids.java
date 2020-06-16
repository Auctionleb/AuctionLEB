package com.example.auctionleb;

public class Bids {
    String higestBider,id, title, model,year,condition,description,endDate,price,imageURL,milage,transmition;

    public Bids() {
    }

    public Bids(String higestBider, String id, String title, String model, String year, String condition, String description, String endDate, String price, String imageURL, String milage, String transmition) {
        this.higestBider = higestBider;
        this.id = id;
        this.title = title;
        this.model = model;
        this.year = year;
        this.condition = condition;
        this.description = description;
        this.endDate = endDate;
        this.price = price;
        this.imageURL = imageURL;
        this.milage = milage;
        this.transmition = transmition;
    }

    public String getHigestBider() {
        return higestBider;
    }

    public void setHigestBider(String higestBider) {
        this.higestBider = higestBider;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getMilage() {
        return milage;
    }

    public void setMilage(String milage) {
        this.milage = milage;
    }

    public String getTransmition() {
        return transmition;
    }

    public void setTransmition(String transmition) {
        this.transmition = transmition;
    }
}
