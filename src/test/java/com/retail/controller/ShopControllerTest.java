package com.retail.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ShopControllerTest {

    private ShopController uut;

    @Before
    public void setUp(){
//        MockitoAnnotations.initMocks(this);
        uut = new ShopController();
    }

    @Test
    public void add_shouldReturnOkStatus() throws Exception{
        MockMvcBuilders.standaloneSetup(uut).build().perform(post("/shop/add")).andExpect(status().isOk());
    }
}
