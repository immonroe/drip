package com.drip.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebConfig is a configuration class for customizing Spring's web behavior.
 * Specifically, this sets up CORS so your frontend (localhost:3000)
 * can communicate with your backend (localhost:8080) without being blocked by
 * the browser.
 */
@Configuration // Marks this as a Spring configuration class
public class WebConfig {

    /**
     * Defines a bean that customizes the web MVC configuration.
     * In this case, we're overriding the default CORS settings.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            /**
             * Configure CORS mappings for the whole application.
             * This allows HTTP requests from the frontend (localhost:3000)
             * to access backend endpoints (localhost:8080).
             */
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Apply to all API endpoints
                        .allowedOrigins("http://localhost:3000") // Allow frontend origin
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow CRUD + preflight
                        .allowedHeaders("*"); // Allow all custom headers
            }
        };
    }
}
