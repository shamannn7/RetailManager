package com.retail;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retail.controller.ShopController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;

@Configuration
public class SpecConfig {

    @Bean
    public ObjectMapper objectMapper(){
        Jackson2ObjectMapperFactoryBean bean = new Jackson2ObjectMapperFactoryBean();
        bean.afterPropertiesSet();

        return bean.getObject();
    }

    @Bean
    public ShopController shopController(){
        return new ShopController();
    }
}
