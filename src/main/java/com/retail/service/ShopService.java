package com.retail.service;

import com.retail.model.Shop;

import java.util.ArrayList;
import java.util.List;

public class ShopService {
    private List<Shop> shops = new ArrayList<>();

    public void add(Shop shop){
        shops.add(shop);
    }

    List<Shop> getShops() {
        return shops;
    }
}
