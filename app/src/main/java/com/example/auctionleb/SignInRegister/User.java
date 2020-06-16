package com.example.auctionleb.SignInRegister;


public class User {
    public String firstName,email,phNumber,balance;

    public User(){
    }

    public User(String name, String email, String phone,String balance) {
        this.firstName = name;
        this.email = email;
        this.phNumber = phone;
        this.balance=balance;
    }

}
