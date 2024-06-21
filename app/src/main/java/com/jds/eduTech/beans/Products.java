package com.jds.eduTech.beans;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

public class Products {
    @SerializedName("product_name")
    private String product_name;
    @SerializedName("units")
    private String units;
    @SerializedName("quantity")
    private int quantity;
   @SerializedName("product_id")
    private int product_id;
     @SerializedName("price")
    private String price;

    public Products(String product_name, String units, int quantity, int product_id,String price) {
        this.product_name = product_name;
        this.units = units;
        this.quantity = quantity;
        this.product_id = product_id;
        this.price = price;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }
    public int getQuantity () {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getProduct_id () {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
    public String getPrice () {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @NotNull
    @Override
    public String toString() {
        return "Products{" +
                "product_name='" + product_name + '\'' +
                ", units='" + units + '\'' +
                ", quantity='" + quantity + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
