package com.Krishi.krishikart;

public class PojoLinear {
    private String productimage;
    private String productname;
    private String username;
    private String phonenumber;
    private String productquantity;
    private String pricerange;
    private String date;

    public  PojoLinear() {
    }

    public PojoLinear(String productimage, String productname, String username, String phonenumber, String productquantity, String pricerange,String date) {
        this.productimage = productimage;
        this.productname = productname;
        this.username = username;
        this.phonenumber = phonenumber;
        this.productquantity = productquantity;
        this.pricerange = pricerange;
        this.date=date;
    }

    public String getProductimage() {
        return productimage;
    }

    public void setProductimage(String productimage) {
        this.productimage = productimage;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getProductquantity() {
        return productquantity;
    }

    public void setQuantity(String productquantity) {
        this.productquantity = productquantity;
    }

    public String getPricerange() {
        return pricerange;
    }

    public void setPricerange(String pricerange) {
        this.pricerange = pricerange;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
