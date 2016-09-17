package com.retail.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.model.AddressType;
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
        geocodingResult.types = new AddressType[]{AddressType.LOCALITY};
        geocodingResult.formattedAddress = "Some address";

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
        assertEquals("67", actual.getNumber());
        assertEquals("W1B 4DZ", actual.getPostCode());
    }
}
