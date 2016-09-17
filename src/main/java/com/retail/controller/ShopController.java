package com.retail.controller;

import com.retail.model.Shop;
import com.retail.service.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/shop", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShopController {
    private static final Logger LOG = LoggerFactory.getLogger(ShopController.class);

    @Autowired
    private ShopService shopService;

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void add(@RequestBody Shop shop) {
        LOG.info("Add shop " + shop);

        shopService.add(shop);
    }
}
