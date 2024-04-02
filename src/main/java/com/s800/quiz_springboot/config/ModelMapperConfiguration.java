package com.s800.quiz_springboot.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {

    @Bean
    public static ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
