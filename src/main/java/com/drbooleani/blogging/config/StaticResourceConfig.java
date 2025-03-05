package com.drbooleani.blogging.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String uploadsDir = "file:./uploads/";

        registry.addResourceHandler("/uploads/post-thumbnails/**")
                .addResourceLocations(uploadsDir + "post-thumbnails/");

        registry.addResourceHandler("/uploads/profile-photos/**")
                .addResourceLocations(uploadsDir + "profile-photos/");
    }
}
