package com.dmadev.restjwt.controllers;

import com.dmadev.restjwt.service.UserService;
import com.dmadev.restjwt.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

public class AuthController {
    private final UserService userService;
    private  final JwtTokenUtils jwtTokenUtils;

    //присылайте пост запрос и мы вам токен отдадим
    @PostMapping
    public ResponseEntity<?> createAuthToken(){

    }
}
