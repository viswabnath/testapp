package com.example.user.connection;

public class OnlyOneList {
    public  byte[] img;
    public String name;
    public Integer orderid;
    public Integer organizationid;
    public  String organization;
public OnlyOneList(String name,byte[] img,int  orderid,int organizationid,String organization){
    this.img=img;
    this.name=name;
    this.orderid=orderid;
    this.organizationid=organizationid;
    this.organization=organization;
}
public byte[] getImg(){return img;}

    public String getName() { return name; }

    public Integer getOrderid() { return orderid; }

    public Integer getOrganizationid() { return organizationid; }

    public String getOrganization() {
        return organization;
    }
}
