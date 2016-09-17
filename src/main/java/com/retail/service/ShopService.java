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
    private static final String GOOGLE_MAPS_KEY = "AIzaSyCO6JFHygl94qGWCaVQZJ8TTHcGXZlDsFE";


    private List<Shop> shops = new ArrayList<>();
    private GeocodingApiRequest geocode;
    private GeoApiContext context;

    public void add(Shop shop) {
        LOG.info("Adding shop " + shop);

        try {
            GeocodingResult[] results = getGeocodingResults(shop);

            shop.setLatitude(results[0].geometry.location.lat);
            shop.setLongitude(results[0].geometry.location.lng);
        } catch (Exception e) {
            LOG.error("Exception while querying coordinates from Google Maps Api " + e.getMessage());
        }

        shops.add(shop);
    }

    public Shop getNearestShop(){
        //TODO implement
        return new Shop();
//        return shops.getNearest();
    }

    private GeocodingResult[] getGeocodingResults(Shop shop) throws Exception {
        context = new GeoApiContext().setApiKey(GOOGLE_MAPS_KEY);
        geocode = GeocodingApi.geocode(context,
                shop.getNumber() + " " + shop.getPostCode());
        return geocode.await();
    }

    List<Shop> getShops() {
        return shops;
    }
}
