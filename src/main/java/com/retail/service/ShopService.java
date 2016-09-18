package com.retail.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.model.GeocodingResult;
import com.retail.model.Shop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopService {
    private static final Logger LOG = LoggerFactory.getLogger(ShopService.class);
    //TODO encrypt and don't store as a hardcoded constant in code
    private static final String GOOGLE_MAPS_KEY = "AIzaSyCO6JFHygl94qGWCaVQZJ8TTHcGXZlDsFE";
    private static final int NOT_FOUND = -1;


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

    public Shop getNearestShop(double customerLongitude, double customerLatitude) {
        LOG.info("Getting nearest shop for customer with longitude = " + customerLongitude
                + " and latitude = " + customerLatitude);
        if (null == shops) {
            return null;
        }

        double minDistance = NOT_FOUND;
        int minIndex = NOT_FOUND;
        for (int i = 0; i < shops.size(); i++) {
            Shop shop = shops.get(i);
            double distance = getDistance(shop, customerLongitude, customerLatitude);
            if (distance < minDistance || minDistance < 0) {
                minDistance = distance;
                minIndex = i;
            }
        }
        return shops.get(minIndex);
    }

    private GeocodingResult[] getGeocodingResults(Shop shop) throws Exception {
        context = new GeoApiContext().setApiKey(GOOGLE_MAPS_KEY);
        geocode = GeocodingApi.geocode(context,
                shop.getHouseNumber() + " " + shop.getPostCode());//TODO check
        return geocode.await();
    }

    List<Shop> getShops() {
        return shops;
    }

    private double getDistance(Shop shop, double customerLongitude, double customerLatitude) {
        return 0;//TODO implement
    }
}
