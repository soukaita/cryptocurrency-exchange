package com.indev.cryptocurrency.exchange;

public class Customer {

    private int quantity;
    private String cryptocurrency;
    private int balance;


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCryptocurrency() {
        return cryptocurrency;
    }

    public void setCryptocurrency(String cryptocurrency) {
        this.cryptocurrency = cryptocurrency;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Customer withCryptocurrency(String bitcoin, int i) {
        this.cryptocurrency = bitcoin;
        this.quantity = i;
        return this;
    }

    public Customer withBalance(int i) {
        this.balance = i;
        return this;
    }

    @Override
    public String toString() {
        if (quantity > 0) {
            //seller
            return balance + ":" + "$," + quantity + ":" + cryptocurrency;
        }
        else {
            //buyer
            return balance + ":" + "$";
        }

    }
}