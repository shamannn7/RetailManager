package com.retail.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ShopControllerTest {

    private static final String SHOP_ADD_URL = "/shop/add";
    private static final String WRONG_SHOP_ADD_URL = "/shop/adds";
    private static final String NEW_SHOP_JSON = "{\"name\":\"Harrods\", \"number\": 7, \"postcode\":\"SW1X 7XL\"}";

    private ShopController uut;

    @Before
    public void setUp() {
        uut = new ShopController();
    }

    @Test
    public void add_shouldReturnOkStatus() throws Exception {
        MockMvcBuilders.standaloneSetup(uut).build().perform(
                post(SHOP_ADD_URL).content(NEW_SHOP_JSON).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void add_shouldReturn404Status() throws Exception {
        MockMvcBuilders.standaloneSetup(uut).build().perform(post(WRONG_SHOP_ADD_URL)).andExpect(status().is(404));
    }
}
