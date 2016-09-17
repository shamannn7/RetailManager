package com.retail.service;

import com.retail.model.Shop;

import java.util.ArrayList;
import java.util.List;

class ShopService {
    private List<Shop> shops = new ArrayList<>();

    void add(Shop shop){
        shops.add(shop);
    }
}
