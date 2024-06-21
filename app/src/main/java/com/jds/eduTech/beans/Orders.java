package com.jds.eduTech.beans;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Orders {
    @SerializedName("dist_name")
    private String dist_name;

    @SerializedName("village_name")
    private String village_name;
     @SerializedName("code")
    private String code;
    @SerializedName("school_name")
    private String school_name;
    @SerializedName("hm_contact_num")
    private String hm_contact_num;
    @SerializedName("UDISE_code")
    private String UDISE_code;
    @SerializedName("order_num")
    private String order_num;
    @SerializedName("hm_name")
    private String hm_name;
    @SerializedName("supplierName")
    private String supplierName;
    @SerializedName("supplierNumber")
    private String supplierNumber;
    @SerializedName("orderId")
    private int orderId;
    @SerializedName("invoice_status")
    private int invoice_status;
    @SerializedName("apc_approved_status")
    private int apc_approved_status;
    @SerializedName("products")
    private List<Products> products;

    public Orders() { }

    public Orders(String dist_name, String village_name, String code, String school_name, String hm_contact_num, String UDISE_code, String order_num, String hm_name, String supplierName, String supplierNumber, int orderId, int invoice_status, int apc_approved_status,List<Products>products) {
        this.dist_name = dist_name;
        this.village_name = village_name;
        this.code = code;
        this.school_name = school_name;
        this.hm_contact_num = hm_contact_num;
        this.UDISE_code = UDISE_code;
        this.order_num = order_num;
        this.hm_name = hm_name;
        this.supplierName = supplierName;
        this.supplierNumber = supplierNumber;
        this.orderId = orderId;
        this.invoice_status = invoice_status;
        this.apc_approved_status = apc_approved_status;
        this.products = products;
    }

    public String getDist_name() {
        return dist_name;
    }

    public void setDist_name(String dist_name) {
        this.dist_name = dist_name;
    }

    public String getVillage_name() {
        return village_name;
    }

    public void setVillage_name(String village_name) {
        this.village_name = village_name;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }
    public String getHm_contact_num() {
        return hm_contact_num;
    }

    public void setHm_contact_num(String hm_contact_num) {
        this.hm_contact_num = hm_contact_num;
    }
    public String getUDISE_code() {
        return UDISE_code;
    }

    public void setUDISE_code(String UDISE_code) {
        this.UDISE_code = UDISE_code;
    }
     public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }
    public String getHm_name() {
        return hm_name;
    }

    public void setHm_name(String hm_name) {
        this.hm_name = hm_name;
    }
      public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
   public String getSupplierNumber() {
        return supplierNumber;
    }

    public void setSupplierNumber(String supplierNumber) {
        this.supplierNumber = supplierNumber;
    }
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public int getApc_approved_status() {
        return apc_approved_status;
    }

    public void setApc_approved_status(int apc_approved_status) {
        this.apc_approved_status = apc_approved_status;
    }
    public int getInvoice_status() {
        return invoice_status;
    }

    public void setInvoice_status(int invoice_status) {
        this.invoice_status = invoice_status;
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }

    @NotNull
    @Override
    public String toString() {
        return "Orders{" +
                "dist_name='" + dist_name + '\'' +
                ", village_name='" + village_name + '\'' +
                ", code='" + code + '\'' +
                ", school_name='" + school_name + '\'' +
                ", hm_contact_num='" + hm_contact_num + '\'' +
                ", UDISE_code='" + UDISE_code + '\'' +
                ", order_num='" + order_num + '\'' +
                ", hm_name='" + hm_name + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", supplierNumber='" + supplierNumber + '\'' +
                ", orderId='" + orderId + '\'' +
                ", invoice_status='" + invoice_status + '\'' +
                ", apc_approved_status='" + invoice_status + '\'' +
                ", products='" + products + '\'' +
                '}';
    }
}
