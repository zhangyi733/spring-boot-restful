package com.restful.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    private static ModelMapper mapper() {
        return new ModelMapper();
    }
}
