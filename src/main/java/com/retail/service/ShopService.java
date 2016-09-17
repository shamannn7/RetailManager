package com.retail.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.model.GeocodingResult;
import com.retail.controller.ShopController;
import com.retail.model.Shop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopService {
    private static final Logger LOG = LoggerFactory.getLogger(ShopService.class);
    //TODO encrypt
    static final String GOOGLE_MAPS_KEY = "AIzaSyCO6JFHygl94qGWCaVQZJ8TTHcGXZlDsFE";


    private List<Shop> shops = new ArrayList<>();
    private GeocodingApiRequest geocode;
    private GeoApiContext context;

    public void add(Shop shop) {
        LOG.info("Adding shop " + shop);

        context = new GeoApiContext().setApiKey(GOOGLE_MAPS_KEY);
        try {
            geocode = GeocodingApi.geocode(context,
                    shop.getNumber() + " " + shop.getPostCode());
            GeocodingResult[] results = geocode.await();
            shop.setLatitude(results[0].geometry.location.lat);
            shop.setLongitude(results[0].geometry.location.lng);
        } catch (Exception e) {
            LOG.error("Exception while querying coordinates from Google Maps Api " + e.getMessage());
        }

        shops.add(shop);
    }

    List<Shop> getShops() {
        return shops;
    }
}
