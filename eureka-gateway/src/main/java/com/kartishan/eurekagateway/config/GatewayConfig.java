package com.kartishan.eurekagateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private final AuthenticationFilter authenticationFilter;

    public GatewayConfig(AuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("book-service", r -> r.path("/api/book/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://book-service"))
                .route("view-history-service", r -> r.path("/api/history/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://view-history-service"))
                .route("rating-service", r -> r.path("/api/rating/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://rating-service"))
                .route("book-file-service", r -> r.path("/api/file/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://book-file-service"))
                .route("scroll-service", r -> r.path("/api/scroll/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://scroll-service"))
                .route("scroll-recommendation-service", r -> r.path("/api/user-scroll-recommendation/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://scroll-recommendation-service"))
                .route("scroll-user-like-service", r -> r.path("/api/user-scroll-like/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://scroll-user-like-service"))
                .route("book-mark-service", r -> r.path("/api/book-mark/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://book-mark-service"))
                .build();
    }
}