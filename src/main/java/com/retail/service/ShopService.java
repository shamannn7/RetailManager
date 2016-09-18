package com.retail.service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
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
    private static final String GOOGLE_MAPS_KEY = "AIzaSyB6quqSj7dtBu4MGXRQMkKVHrHtEd5gvhQ";
    private static final int NOT_FOUND = -1;
    private static final String PLACE_ID = "place_id:";
    private static final String ZERO_RESULTS = "ZERO_RESULTS";


    private List<Shop> shops = new ArrayList<>();
    private GeoApiContext context = new GeoApiContext().setApiKey(GOOGLE_MAPS_KEY);

    public void add(Shop shop) {
        GeocodingResult[] results = getGeocodingResults(shop);
        //we don't store the shop if location wasn't found
        if (null != results) {
            //we always take the first result
            GeocodingResult result = results[0];
            shop.setLatitude(result.geometry.location.lat);
            shop.setLongitude(result.geometry.location.lng);
            shop.setPlaceId(result.placeId);

            LOG.info("Adding {} to the shop list ", shop);
            shops.add(shop);
        }
    }

    public Shop getNearestShop(double customerLongitude, double customerLatitude) {
        LOG.info("Getting nearest shop for customer with longitude = " + customerLongitude
                + " and latitude = " + customerLatitude);
        if (null == shops || 0 == shops.size()) {
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

    private GeocodingResult[] getGeocodingResults(Shop shop) {
        if (null == shop || null == shop.getPostCode()) {
            return null;
        }
        String address = shop.getPostCode();
        GeocodingApiRequest geocode;
        try {
            geocode = GeocodingApi.geocode(context, address);

            return geocode.await();
        } catch (Exception e) {
            LOG.error("Exception while querying coordinates from Google Maps Api: " + e);
        }
        return null;
    }

    List<Shop> getShops() {
        return shops;
    }

    private long getDistance(Shop shop, double customerLongitude, double customerLatitude) {
        LatLng latLng = new LatLng(customerLatitude, customerLongitude);
        DistanceMatrix distanceMatrix = null;
        try {
            distanceMatrix = DistanceMatrixApi.newRequest(context).origins(latLng).
                    destinations(PLACE_ID + shop.getPlaceId()).mode(TravelMode.WALKING).await();
            //we set walking as the default mode
        } catch (Exception e) {
            LOG.error("Exception while querying distance from Google Maps Api: " + e);
        }

        if (null != distanceMatrix &&
                !ZERO_RESULTS.equals(distanceMatrix.rows[0].elements[0].status.toString())) {
            return distanceMatrix.rows[0].elements[0].distance.inMeters;
        } else {
            return NOT_FOUND;
        }
    }
}
