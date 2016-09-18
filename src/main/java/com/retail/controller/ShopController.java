package com.retail.controller;

import com.retail.model.Shop;
import com.retail.service.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * RESTful Server API to add shops and find the nearest to the customer.
 */
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

    /**
     * Returns the shop nearest to the customer by its coordinates.
     *
     * @param customerLongitude customer's longitude
     * @param customerLatitude  customer's latitude
     * @return nearest shop
     */
    @RequestMapping(value = "/get-nearest", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Shop getNearest(@RequestParam Double customerLongitude, @RequestParam Double customerLatitude) {
        LOG.info("Getting nearest shop for customer with longitude = " + customerLongitude
                + " and latitude = " + customerLatitude);

        return shopService.getNearestShop(customerLongitude, customerLatitude);
    }
}
