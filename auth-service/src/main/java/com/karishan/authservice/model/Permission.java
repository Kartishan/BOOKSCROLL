package com.karishan.authservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    USER_READ("management:read"),
    USER_UPDATE("management:update"),
    USER_CREATE("management:create"),
    USER_DELETE("management:delete")
    ;

    private final String permission;
}
