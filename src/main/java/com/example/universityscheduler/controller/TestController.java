package com.example.universityscheduler.controller;

import com.example.universityscheduler.mapper.AuthMapper;
import com.example.universityscheduler.mapper.rest.UserAccountRestMapper;
import com.example.universityscheduler.model.AuthRequest;
import com.example.universityscheduler.model.AuthResponse;
import com.example.universityscheduler.model.UserInfo;
import com.example.universityscheduler.service.impl.AuthService;
import com.example.universityscheduler.service.impl.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class TestController {

    private final AuthService authService;
    private final AuthMapper authMapper;
    private final UserAccountService userAccountService;
    private final UserAccountRestMapper userAccountRestMapper;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody  AuthRequest authRequest) {
        return authService.login(authRequest);
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody  AuthRequest authRequest) {
        authService.registerUser(authMapper.map(authRequest));
        return authService.login(authRequest);
    }

    @GetMapping("/me")
    public UserInfo getUser() {
        return userAccountRestMapper.toDto(userAccountService.getCurrentUser());
    }
}
