package com.generator.app.util;


import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@Configuration
class SpringConfig {

    private static final String MAX_SIZE = "1MB";

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.parse(MAX_SIZE));
        factory.setMaxRequestSize(DataSize.parse(MAX_SIZE));
        return factory.createMultipartConfig();
    }
}
