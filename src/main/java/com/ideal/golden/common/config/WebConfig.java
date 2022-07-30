package com.ideal.golden.common.config;

import com.ideal.golden.common.prop.GoldenProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private GoldenProperties goldenProperties;


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(goldenProperties.getPropConfig().getCorsOrigins())
                .allowedMethods("POST GET")
                .allowCredentials(true);
    }
}
