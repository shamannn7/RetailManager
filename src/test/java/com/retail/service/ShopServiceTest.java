package com.retail.service;

import com.retail.model.Shop;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ShopServiceTest {
    private static final double DELTA = 0.0000001;

    private ShopService uut;

    @Before
    public void setUp() {
        uut = new ShopService();
    }

    @Test
    public void test_validAdd() {
        Shop shop = new Shop("Westfield", "22", "W12 7GF");
        uut.add(shop);
        Shop actual = uut.getShops().get(0);
        assertEquals("Westfield", actual.getName());
        assertEquals("22", actual.getNumber());
        assertEquals("W12 7GF", actual.getPostCode());
        assertEquals(51.5072023, actual.getLatitude(), DELTA);
        assertEquals(-0.2232423, actual.getLongitude(), DELTA);
    }

    @Test
    public void test_incorrectAdd() {
        Shop shop = new Shop("Whatever", "22", "161QQQ");
        uut.add(shop);

        Shop actual = uut.getShops().get(0);
        assertEquals("Whatever", actual.getName());
        assertEquals("22", actual.getNumber());
        assertEquals("161QQQ", actual.getPostCode());
        assertEquals(0, actual.getLatitude(), DELTA);//TODO default coordinates, needs clarification
        assertEquals(0, actual.getLongitude(), DELTA);
    }


}
