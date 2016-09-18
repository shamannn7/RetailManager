package com.retail.model;

public class Shop {
    private String name;
    private String houseNumber;
    private String postCode;
    private double longitude;
    private double latitude;
    private String placeId;

    public Shop() {
    }

    public Shop(String name, String houseNumber, String postCode) {
        this.name = name;
        this.houseNumber = houseNumber;
        this.postCode = postCode;
    }

    public String getName() {
        return name;
    }

    public String getHouseNumber() {
        return houseNumber;
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

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shop shop = (Shop) o;

        if (Double.compare(shop.longitude, longitude) != 0) return false;
        if (Double.compare(shop.latitude, latitude) != 0) return false;
        if (name != null ? !name.equals(shop.name) : shop.name != null) return false;
        if (houseNumber != null ? !houseNumber.equals(shop.houseNumber) : shop.houseNumber != null) return false;
        if (postCode != null ? !postCode.equals(shop.postCode) : shop.postCode != null) return false;
        return placeId != null ? placeId.equals(shop.placeId) : shop.placeId == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        result = 31 * result + (houseNumber != null ? houseNumber.hashCode() : 0);
        result = 31 * result + (postCode != null ? postCode.hashCode() : 0);
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (placeId != null ? placeId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "name='" + name + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", postCode='" + postCode + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", placeId='" + placeId + '\'' +
                '}';
    }
}
