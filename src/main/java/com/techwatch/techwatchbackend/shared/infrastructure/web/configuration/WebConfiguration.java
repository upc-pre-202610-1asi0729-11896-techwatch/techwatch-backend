package com.techwatch.techwatchbackend.shared.infrastructure.web.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Global web configuration.
 * <p>
 * Registers CORS rules so browser clients hosted on a different origin (the
 * TechWatch Angular frontend) can call the REST API of the deployed backend.
 * Allowed origins are configurable per environment through the
 * {@code cors.allowed-origins} property (env var {@code CORS_ALLOWED_ORIGINS});
 * it defaults to {@code *} (any origin).
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * Comma-separated list of allowed origin patterns. Defaults to {@code *}.
     */
    @Value("${cors.allowed-origins:*}")
    private String[] allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns(allowedOrigins)
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Location")
                .allowCredentials(false)
                .maxAge(3600);
    }
}
