package com.example.user.connection;

public class ClassListItems {

    public  byte[] image;
    public String name;

    public ClassListItems(String name,byte[] img){
        this.image=img;
        this.name=name;


    }

    public byte[] getImg() { return image; }

    public String getName() { return name; }
}
