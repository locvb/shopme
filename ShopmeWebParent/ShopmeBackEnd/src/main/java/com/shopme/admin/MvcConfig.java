package com.shopme.admin;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String dirName = "user-photos";
        Path userPhotoDirs = Paths.get(dirName);
        String userPhotoPath = userPhotoDirs.toFile().getAbsolutePath();
        registry.addResourceHandler("/" + dirName + "/**")
                .addResourceLocations("file:/" + userPhotoPath + "/");


        String categoryImagesName = "../category-images";
        Path categoryImagesDir = Paths.get(categoryImagesName);
        String categoryImagesPath = categoryImagesDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/category-images/**")
                .addResourceLocations("file:/" + categoryImagesPath + "/");
    }
}
