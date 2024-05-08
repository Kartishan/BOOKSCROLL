package com.kartishan.eurekagateway.config;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidator {

    // Список путей, которые не требуют JWT аутентификации
    public static final List<String> openApiEndpoints = List.of(
            "/api/auth/**",
            "/api/book/**"
    );

    // Предикат, который определяет, требует ли путь аутентификации
    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints.stream()
                    .noneMatch(uri -> request.getURI().getPath().matches(uri.replace("**", ".*")));

}
