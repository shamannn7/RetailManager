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

    /**
     * Adds shop to the Retail Manager shop list.
     *
     * @param shop JSON object with name, house number and postcode. {@see Shop}
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void add(@RequestBody Shop shop) {
        LOG.info("Adding shop " + shop);

        shopService.add(shop);
    }

    @RequestMapping(value = "/get-nearest", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Shop getNearest(@RequestParam Double customerLongitude, @RequestParam Double customerLatitude) {
        LOG.info("Getting nearest shop for customerLongitude = " + customerLongitude
                + " and customerLatitude = " + customerLatitude);

        return shopService.getNearestShop();
    }
}
