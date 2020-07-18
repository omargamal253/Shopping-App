package com.example.skyapp.model;

import java.util.HashMap;

public class Order {

    public String userId;
    public double totalPrice;
    public  int numOfItem;
    public HashMap<String,Object> products;
    public String orderDate;


    public String firstName;
    public String lastName;
    public String city;
    public String street;
    public String buildingNum;
    public String mobile;
    public String paymentState;

    public Order() {
    }

    public Order(String userId, double totalPrice, int numOfItem, HashMap<String, Object> products, String orderDate, String firstName, String lastName, String city, String street, String buildingNum, String mobile, String paymentState) {
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.numOfItem = numOfItem;
        this.products = products;
        this.orderDate = orderDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.street = street;
        this.buildingNum = buildingNum;
        this.mobile = mobile;
        this.paymentState = paymentState;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuildingNum() {
        return buildingNum;
    }

    public void setBuildingNum(String buildingNum) {
        this.buildingNum = buildingNum;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(String paymentState) {
        this.paymentState = paymentState;
    }
}
