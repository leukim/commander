package com.leukim.commander.infrastructure.controllers;

import com.leukim.commander.infrastructure.controllers.model.CreateAdminUserRequest;
import com.leukim.commander.infrastructure.repositories.AuthoritiesRepository;
import com.leukim.commander.infrastructure.repositories.UsersRepository;
import com.leukim.commander.infrastructure.repositories.model.Authority;
import com.leukim.commander.infrastructure.repositories.model.Role;
import com.leukim.commander.infrastructure.repositories.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/install")
public final class InstallationController {
    private final UsersRepository usersRepository;
    private final AuthoritiesRepository authoritiesRepository;
    private final PasswordEncoder passwordEncoder;

    public InstallationController(UsersRepository usersRepository, AuthoritiesRepository authoritiesRepository,
                                  PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.authoritiesRepository = authoritiesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<Void> createAdminUser(@RequestBody CreateAdminUserRequest request) {
        boolean alreadyInstalled = authoritiesRepository.existsByAuthority(Role.ADMIN.getName());
        if (alreadyInstalled) {
            return ResponseEntity.badRequest().build();
        }

        String encodedPassword = passwordEncoder.encode(request.password());

        User admin = new User(request.username(), encodedPassword, true);
        usersRepository.save(admin);

        Authority adminRole = new Authority(request.username(), Role.ADMIN.getName());
        authoritiesRepository.save(adminRole);

        return ResponseEntity.ok().build();
    }
}
