package com.example.auctionleb.SignInRegister;

public class User {
    public String FirstName,Email,PhNumber,Balance;

    public User(){

    }

    public User(String name, String email, String phone,String balance) {
        this.FirstName = name;
        this.Email = email;
        this.PhNumber = phone;
        this.Balance=balance;
    }

}
