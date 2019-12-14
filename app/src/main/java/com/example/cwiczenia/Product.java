package com.example.cwiczenia;

public class Product {

    private String id;
    private String name;
    private int price;
    private int quantity;
    private boolean bought;

    public Product() {
    }

    public Product(String id, String name, int price, int quantity, boolean bought){
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.bought = bought;
    }

    public Product(String name, int price, int quantity, boolean bought) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.bought = bought;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }

    public String getId() { return id;  }

    public void setId(String id) {
        this.id = id;
    }
}
