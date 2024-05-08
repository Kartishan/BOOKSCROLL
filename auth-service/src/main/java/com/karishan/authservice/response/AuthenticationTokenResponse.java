package com.karishan.authservice.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.karishan.authservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationTokenResponse {
    @JsonProperty("user")
    private User user;
}