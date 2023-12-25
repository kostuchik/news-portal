package com.news.portal.controllers;

import com.news.portal.dto.LoginRequest;
import com.news.portal.dto.UserDataRequest;
import com.news.portal.services.AuthService;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/account")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> logIn(@RequestBody LoginRequest loginRequest) {
        return authService.createAuthToken(loginRequest);
    }

    @PostMapping("/singup")
    public ResponseEntity<?> singUp(@RequestBody UserDataRequest registrationRequest) {
        return ResponseEntity.ok(authService.register(registrationRequest));
    }
}
