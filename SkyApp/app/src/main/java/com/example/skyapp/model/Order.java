package com.example.skyapp.model;

import java.util.HashMap;

public class Order {

    public String userId;
    public double totalPrice;
    public  int numOfItem;
    public HashMap<String,Object> products;
    public String orderDate;
    public Order() {
    }

    public Order(String userId, double totalPrice, int numOfItem, HashMap<String, Object> products, String orderDate) {
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.numOfItem = numOfItem;
        this.products = products;
        this.orderDate = orderDate;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getNumOfItem() {
        return numOfItem;
    }

    public void setNumOfItem(int numOfItem) {
        this.numOfItem = numOfItem;
    }

    public HashMap<String, Object> getProducts() {
        return products;
    }

    public void setProducts(HashMap<String, Object> products) {
        this.products = products;
    }
}
