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
        Shop shop = new Shop("Whittard", "67", "W1B 4DZ");
        uut.add(shop);
        Shop actual = uut.getShops().get(0);
        assertEquals("Whittard", actual.getName());
        assertEquals("67", actual.getNumber());
        assertEquals("W1B 4DZ", actual.getPostCode());
        assertEquals(51.5096832, actual.getLatitude(), DELTA);
        assertEquals(-0.1362628, actual.getLongitude(), DELTA);
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
