package com.retail.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.retail.model.Shop;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.*;


@RunWith(PowerMockRunner.class)
@PrepareForTest({GeocodingApi.class, GeocodingApiRequest.class})
public class ShopServiceTest {
    private static final double LATITUDE = 51.5096832;
    private static final double LONGITUDE = -0.1362628;

    @InjectMocks
    private ShopService uut;

    @org.mockito.Mock
    private GeoApiContext geoApiContext;


    @Before
    public void setUp() throws Exception {
        LatLng latLng = new LatLng(LATITUDE, LONGITUDE);
        GeocodingResult geocodingResult = new GeocodingResult();
//        geocodingResult.types = new AddressType[]{AddressType.LOCALITY};
//        geocodingResult.formattedAddress = "Some address";

        GeocodingResult[] geocodingResults = new GeocodingResult[]{geocodingResult};

        GeocodingApiRequest geocodingApiRequest = mock(GeocodingApiRequest.class);
        when(geocodingApiRequest.latlng(latLng)).thenReturn(geocodingApiRequest);
        when(geocodingApiRequest.await()).thenReturn(geocodingResults);
        mockStatic(GeocodingApi.class);
        when(GeocodingApi.newRequest(eq(geoApiContext))).thenReturn(geocodingApiRequest);
    }

    @Test
    public void add_shouldReturnLocation() throws Exception {
        Shop shop = new Shop("Whittard", "67", "W1B 4DZ");
        uut.add(shop);

        verifyStatic(times(1));
    }


    @Test
    public void add_shouldAddShop() throws Exception {
        Shop shop = new Shop("Whittard", "67", "W1B 4DZ");
        uut.add(shop);
        Shop actual = uut.getShops().get(0);

        assertEquals("Whittard", actual.getName());
        assertEquals("67", actual.getHouseNumber());
        assertEquals("W1B 4DZ", actual.getPostCode());
    }


    @Test
    public void add_shouldGetNearestShopWithOnlyShop() throws Exception {
        Shop shop = new Shop("Whittard", "67", "W1B 4DZ");
        uut.add(shop);
        Shop nearestShop = uut.getNearestShop(LONGITUDE, LATITUDE);

        assertEquals(shop, nearestShop);
    }

    @Test//TODO is this for another level of testing
    public void add_shouldGetNearestShopWithSeveralShops() throws Exception {
        Shop shop = new Shop("Whittard", "67", "W1B 4DZ");//TODO expand
        Shop shop2 = new Shop("Whittard", "67", "W1C 4DZ");
        Shop shop3 = new Shop("Whittard", "67", "W1D 4DZ");
        uut.add(shop);
        uut.add(shop2);
        uut.add(shop3);
        Shop nearestShop = uut.getNearestShop(LONGITUDE, LATITUDE);

        assertEquals(shop, nearestShop);
    }
}
