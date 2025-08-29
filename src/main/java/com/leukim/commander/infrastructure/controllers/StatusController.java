package com.leukim.commander.infrastructure.controllers;

import com.leukim.commander.infrastructure.controllers.model.StatusDto;
import com.leukim.commander.infrastructure.repositories.AuthoritiesRepository;
import com.leukim.commander.infrastructure.repositories.model.Role;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
public final class StatusController {
    private final AuthoritiesRepository authoritiesRepository;

    public StatusController(AuthoritiesRepository authoritiesRepository) {
        this.authoritiesRepository = authoritiesRepository;
    }

    @GetMapping
    public StatusDto status() {
        boolean admin = authoritiesRepository.existsByAuthority(Role.ADMIN.getName());
        return new StatusDto(admin);
    }
}
