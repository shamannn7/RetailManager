package com.retail.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;
import com.google.maps.model.LatLng;
import com.retail.model.Shop;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.*;


@RunWith(PowerMockRunner.class)
@PrepareForTest({GeocodingApi.class, GeocodingApiRequest.class})
public class ShopServiceTest {
    private static final double LATITUDE = 51.5096832;
    private static final double LONGITUDE = -0.1362628;
    private static final double DELTA = 0.00001;

    private static final String NAME_LONDON = "Whittard";
    private static final String HOUSE_NUMBER = "67";
    private static final String POST_CODE = "W1B 4DZ";
    private static final String PLACE_ID = "ChIJKy-9oNYEdkgRcMIIq-Tu_no";

    @InjectMocks
    private ShopService uut;

    @org.mockito.Mock
    private GeoApiContext geoApiContext;


    @Before
    public void setUp() throws Exception {
        LatLng latLng = new LatLng(LATITUDE, LONGITUDE);
        GeocodingResult geocodingResult = new GeocodingResult();
        Geometry geometry = new Geometry();
        geometry.location = latLng;
        geocodingResult.geometry = geometry;
        geocodingResult.placeId = PLACE_ID;//London

        GeocodingResult geocodingResultBournemouth = new GeocodingResult();
        geometry.location = latLng;
        geocodingResultBournemouth.geometry = geometry;

        GeocodingResult[] geocodingResults = new GeocodingResult[]{geocodingResult};

        GeocodingApiRequest geocodingApiRequest = mock(GeocodingApiRequest.class);
        when(geocodingApiRequest.await()).thenReturn(geocodingResults);

        GeocodingApiRequest geocodingApiRequestBournemouth = mock(GeocodingApiRequest.class);
        when(geocodingApiRequestBournemouth.await()).thenReturn(geocodingResults);

        mockStatic(GeocodingApi.class);
        when(GeocodingApi.geocode(geoApiContext, POST_CODE)).thenReturn(geocodingApiRequest);
    }

    @Test
    public void add_shouldReturnLocation() throws Exception {
        Shop shop = new Shop(NAME_LONDON, HOUSE_NUMBER, POST_CODE);
        uut.add(shop);

        verifyStatic(times(1));
    }

    @Test
    public void add_shouldAddShop() throws Exception {
        Shop shop = new Shop(NAME_LONDON, HOUSE_NUMBER, POST_CODE);
        uut.add(shop);
        Shop actual = uut.getShops().get(0);

        assertEquals(NAME_LONDON, actual.getName());
        assertEquals(HOUSE_NUMBER, actual.getHouseNumber());
        assertEquals(POST_CODE, actual.getPostCode());
        assertEquals(LONGITUDE, actual.getLongitude(), DELTA);
        assertEquals(LATITUDE, actual.getLatitude(), DELTA);
        assertEquals(PLACE_ID, actual.getPlaceId());
    }

    @Test
    public void add_shouldNotAddShop() throws Exception {
        Shop shop = new Shop(NAME_LONDON, HOUSE_NUMBER, null);
        uut.add(shop);

        assertEquals(0, uut.getShops().size());
    }

    @Test
    public void add_shouldGetNearestShopWithOnlyShop() throws Exception {
        Shop shop = new Shop(NAME_LONDON, HOUSE_NUMBER, POST_CODE);
        uut.add(shop);
        Shop nearestShop = uut.getNearestShop(LONGITUDE, LATITUDE);

        assertEquals(shop, nearestShop);
    }
}
