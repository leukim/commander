package com.leukim.commander.infrastructure.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public final class LoginController {
    @GetMapping
    public String validateCredentials() {
        return "OK";
    }
}
