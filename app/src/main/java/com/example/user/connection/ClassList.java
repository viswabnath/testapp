package com.example.user.connection;

public class ClassList {


    public String description;
    public String quantity;
    public String quantityin;
    public String price;
    public String quality;


    public ClassList(String description,String quantity,String quantityin,String price,String quality ){

        this.description=description;
        this.quantity=quantity;
        this.quantityin=quantityin;
        this.price=price;
        this.quality=quality;



    }
    public String getDescription() { return description; }
    public String getQuantity(){return  quantity;}
    public String getQuantityIn(){return quantityin;}
    public String getPrice(){return price;}
    public String getQuality(){return quality;}



}


