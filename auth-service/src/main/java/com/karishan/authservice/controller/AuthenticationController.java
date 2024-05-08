package com.karishan.authservice.controller;


import com.karishan.authservice.model.User;
import com.karishan.authservice.request.AuthenticationRequest;
import com.karishan.authservice.request.RegisterRequest;
import com.karishan.authservice.response.AuthenticationResponse;
import com.karishan.authservice.service.jwt.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request, HttpServletResponse response) {
        AuthenticationResponse authResponse = service.register(request);

        Cookie refreshTokenCookie = new Cookie("refresh_token", authResponse.getRefreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");

        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok(new AuthenticationResponse(authResponse.getAccessToken(), null));
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request, HttpServletResponse response) {
        AuthenticationResponse authResponse = service.authenticate(request);

        Cookie refreshTokenCookie = new Cookie("refresh_token", authResponse.getRefreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");

        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok(new AuthenticationResponse(authResponse.getAccessToken(), null) );
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }

    @GetMapping("/authenticate-from-token")
    public ResponseEntity<User> authenticateFromToken(HttpServletRequest request) {
        User authenticatedUser = service.authenticateFromToken(request);
        if (authenticatedUser != null) {
            return ResponseEntity.ok(authenticatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
