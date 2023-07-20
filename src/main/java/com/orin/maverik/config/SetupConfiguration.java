package com.orin.maverik.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SetupConfiguration {


    @Bean
    public RestService createRestService() {
        RestService restService = new RestService();
        return restService;
    }

}