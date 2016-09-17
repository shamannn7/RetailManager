package com.retail.model;

public class Shop {
    private String name;
    private String number;//TODO consider separating address entity
    private String postCode;
    private String longitude;
    private String latitude;

    public Shop(String name, String number, String postCode) {
        this.name = name;
        this.number = number;
        this.postCode = postCode;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
