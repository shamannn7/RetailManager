package com.retail.service;


import com.retail.model.Shop;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ShopServiceTest {
    private ShopService uut;

    @Before
    public void setUp(){
        uut = new ShopService();
    }

    @Test
    public void test_Add(){
        Shop shop = new Shop("Westfield","22", "E161QQ");
        uut.add(shop);
    }
}
