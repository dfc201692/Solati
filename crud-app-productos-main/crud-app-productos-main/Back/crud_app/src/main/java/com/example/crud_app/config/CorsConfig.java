package com.example.crud_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // La ruta específica a la que deseas aplicar CORS
                        .allowedOrigins("http://localhost:3000") // Permitir solicitudes desde este origen
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Métodos HTTP permitidos
                        .allowedHeaders("*"); // Encabezados permitidos
            }
        };
    }
}

