package com.retail.model;

public class Shop {
    private String name;
    private String number;//TODO consider separating address entity
    private String postCode;
    private double longitude;
    private double latitude;

    public Shop() {
    }

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

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shop shop = (Shop) o;

        if (Double.compare(shop.longitude, longitude) != 0) return false;
        if (Double.compare(shop.latitude, latitude) != 0) return false;
        if (!name.equals(shop.name)) return false;
        if (!number.equals(shop.number)) return false;
        return postCode.equals(shop.postCode);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name.hashCode();
        result = 31 * result + number.hashCode();
        result = 31 * result + postCode.hashCode();
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
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
