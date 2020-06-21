package com.example.skyapp.model;

import java.io.Serializable;

public class Product  implements Serializable {

     public   String title ,description , color , brand ,image_url ;

    public double price  , discount ;

    public Product() {
        discount=0;
    }

    public Product(String title, String description, String color, String brand, String image_url, double price, double discount) {
        this.title = title;
        this.description = description;
        this.color = color;
        this.brand = brand;
        this.image_url = image_url;
        this.price = price;
        this.discount = discount;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
