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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shop shop = (Shop) o;

        if (!name.equals(shop.name)) return false;
        if (!number.equals(shop.number)) return false;
        if (!postCode.equals(shop.postCode)) return false;
        if (!longitude.equals(shop.longitude)) return false;
        return latitude.equals(shop.latitude);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + number.hashCode();
        result = 31 * result + postCode.hashCode();
        result = 31 * result + longitude.hashCode();
        result = 31 * result + latitude.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", postCode='" + postCode + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }
}
