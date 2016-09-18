package com.retail.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;
import com.google.maps.model.LatLng;
import com.retail.model.Shop;
import org.junit.Before;
import org.junit.Ignore;
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
    private static final String HOUSE_NUMBER_LONDON = "67";
    private static final String POST_CODE_LONDON = "W1B 4DZ";
    private static final String PLACE_ID_LONDON = "ChIJKy-9oNYEdkgRcMIIq-Tu_no";

    private static final String NAME_BOURNEMOUTH = "Bournemouth Shop";
    private static final String HOUSE_NUMBER_BOURNEMOUTH = "22";
    private static final String POST_CODE_BOURNEMOUTH = "BH5 1EW";
    private static final String PLACE_ID_BOURNEMOUTH = "ChIJw9qjrG6fc0gR1hJ4VqchZdI";

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
        geocodingResult.placeId = PLACE_ID_LONDON;//London

        GeocodingResult geocodingResultBournemouth = new GeocodingResult();
//        Geometry geometry = new Geometry();
        geometry.location = latLng;
        geocodingResultBournemouth.geometry = geometry;
        geocodingResultBournemouth.placeId = PLACE_ID_BOURNEMOUTH;//Bournemouth

        GeocodingResult[] geocodingResults = new GeocodingResult[]{geocodingResult};

        GeocodingApiRequest geocodingApiRequest = mock(GeocodingApiRequest.class);
//        when(geocodingApiRequest.latlng(latLng)).thenReturn(geocodingApiRequest);
        when(geocodingApiRequest.await()).thenReturn(geocodingResults);

        GeocodingApiRequest geocodingApiRequestBournemouth = mock(GeocodingApiRequest.class);
//        when(geocodingApiRequestBournemouth.latlng(latLng)).thenReturn(geocodingApiRequest);
        when(geocodingApiRequestBournemouth.await()).thenReturn(geocodingResults);

        mockStatic(GeocodingApi.class);
//        when(GeocodingApi.newRequest(eq(geoApiContext))).thenReturn(geocodingApiRequest);
        when(GeocodingApi.geocode(geoApiContext, "67 W1B 4DZ")).thenReturn(geocodingApiRequest);
        when(GeocodingApi.geocode(geoApiContext, "22 BH5 1EW")).thenReturn(geocodingApiRequestBournemouth);
//        when(GeocodingApi.geocode(eq(geoApiContext), any())).thenReturn(geocodingApiRequest);
    }

    @Test
    public void add_shouldReturnLocation() throws Exception {
        Shop shop = new Shop(NAME_LONDON, HOUSE_NUMBER_LONDON, POST_CODE_LONDON);
        uut.add(shop);

        verifyStatic(times(1));
    }

    @Test
    public void add_shouldAddShop() throws Exception {
        Shop shop = new Shop(NAME_LONDON, HOUSE_NUMBER_LONDON, POST_CODE_LONDON);
        uut.add(shop);
        Shop actual = uut.getShops().get(0);

        assertEquals(NAME_LONDON, actual.getName());
        assertEquals(HOUSE_NUMBER_LONDON, actual.getHouseNumber());
        assertEquals(POST_CODE_LONDON, actual.getPostCode());
        assertEquals(LONGITUDE, actual.getLongitude(), DELTA);
        assertEquals(LATITUDE, actual.getLatitude(), DELTA);
        assertEquals(PLACE_ID_LONDON, actual.getPlaceId());
    }

    @Test
    public void add_shouldNotAddShop() throws Exception {
        Shop shop = new Shop(NAME_LONDON, null, POST_CODE_LONDON);
        uut.add(shop);

        assertEquals(0, uut.getShops().size());

        shop = new Shop(NAME_LONDON, HOUSE_NUMBER_BOURNEMOUTH, null);
        uut.add(shop);

        assertEquals(0, uut.getShops().size());
    }

    @Test
    public void add_shouldGetNearestShopWithOnlyShop() throws Exception {
        Shop shop = new Shop(NAME_LONDON, HOUSE_NUMBER_LONDON, POST_CODE_LONDON);
        uut.add(shop);
        Shop nearestShop = uut.getNearestShop(LONGITUDE, LATITUDE);

        assertEquals(shop, nearestShop);
    }

    @Test//TODO is this for another level of testing?
    @Ignore
    public void add_shouldGetNearestShopWithSeveralShops() throws Exception {
        Shop shop = new Shop(NAME_LONDON, HOUSE_NUMBER_LONDON, POST_CODE_LONDON);//TODO expand
        Shop shop2 = new Shop(NAME_BOURNEMOUTH, HOUSE_NUMBER_BOURNEMOUTH, POST_CODE_BOURNEMOUTH);
        uut.add(shop);
        uut.add(shop2);
        Shop nearestShop = uut.getNearestShop(LONGITUDE, LATITUDE);

        assertEquals(shop, nearestShop);
    }
}
