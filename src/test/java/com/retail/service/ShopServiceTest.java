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
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static com.retail.service.ShopService.GOOGLE_MAPS_KEY;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;


@RunWith(PowerMockRunner.class)
@PrepareForTest({GeocodingApi.class, GeocodingApiRequest.class})
public class ShopServiceTest {
    private static final double DELTA = 0.0000001;
    private static final double LATITUDE = 51.5096832;
    private static final double LONGITUDE = -0.1362628;

    @InjectMocks
    private ShopService uut;

    @Mock
    private GeoApiContext context;

    @Mock
    private GeocodingApiRequest geocodingApiRequest;

    @Before
    public void setUp() {
        uut = new ShopService();
        context.setApiKey(GOOGLE_MAPS_KEY);
    }

    @Test
    public void test_validAdd() throws Exception {
        GeocodingResult geocodingResult = new GeocodingResult();
        geocodingResult.geometry = new Geometry();
        LatLng latLng = new LatLng(51.5096832, -0.1362628);
        geocodingResult.geometry.location = latLng;

        GeocodingApiRequest geocodingApiRequest = mock(GeocodingApiRequest.class);
        when(geocodingApiRequest.latlng(latLng)).thenReturn(geocodingApiRequest);
        when(geocodingApiRequest.await()).thenReturn(new GeocodingResult[]{geocodingResult});
        mockStatic(GeocodingApi.class);
        when(GeocodingApi.newRequest(eq(context))).thenReturn(geocodingApiRequest);

        Shop shop = new Shop("Whittard", "67", "W1B 4DZ");
        uut.add(shop);
        Shop actual = uut.getShops().get(0);

        assertEquals("Whittard", actual.getName());
        assertEquals("67", actual.getNumber());
        assertEquals("W1B 4DZ", actual.getPostCode());
        assertEquals(LATITUDE, actual.getLatitude(), DELTA);
        assertEquals(LONGITUDE, actual.getLongitude(), DELTA);
    }



    @Test
    public void test_incorrectAdd() throws Exception {
        //TODO
    }
}
