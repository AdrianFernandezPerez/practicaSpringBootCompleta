package com.practica.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${empleosapp.ruta.imagenes}")
    private String rutaImagenes;

    public void addResourceHandlers(ResourceHandlerRegistry registry){
        //registry.addResourceHandler("/logos/**").addResourceLocations("file:/Users/adriancallejo/Desktop/empleos/img-vacantes/");
        registry.addResourceHandler("/logos/**").addResourceLocations("file:"+rutaImagenes);
    }
}
