package com.leukim.commander.infrastructure.controllers;

import static com.leukim.commander.assertions.Assertions.assertThat;

import com.leukim.commander.clients.InstallationClient;
import com.leukim.commander.infrastructure.controllers.model.CreateAdminUserRequest;
import com.leukim.commander.infrastructure.controllers.model.StatusDto;
import com.leukim.commander.infrastructure.repositories.AuthoritiesRepository;
import com.leukim.commander.infrastructure.repositories.UsersRepository;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public final class InstallationIntegrationTest {
    @Autowired
    private InstallationClient installationClient;
    @Autowired
    private AuthoritiesRepository authoritiesRepository;
    @Autowired
    private UsersRepository usersRepository;

    @BeforeEach
    void setUp() {
        authoritiesRepository.deleteAll();
        usersRepository.deleteAll();
    }

    @Test
    void whenCallingStatusOnInitialState_thereIsNoAdminUser() {
        StatusDto status = installationClient.getStatus();

        assertThat(status).isNotNull();
        assertThat(status.admin()).isFalse();
    }

    @Test
    void afterCreatingAnAdminUser_thereIsAnAdminUser() {
        CreateAdminUserRequest request = new CreateAdminUserRequest("admin", "admin");
        installationClient.install(request);

        StatusDto status = installationClient.getStatus();

        assertThat(status).isNotNull();
        assertThat(status.admin()).isTrue();
    }

    @Test
    void afterCreatingAnAdminUser_youCannotCreateAnotherOne() {
        CreateAdminUserRequest request = new CreateAdminUserRequest("admin", "admin");
        installationClient.install(request);

        try {
            installationClient.install(request);
            throw new AssertionError("Should have thrown an exception");
        } catch (FeignException e) {
            assertThat(e).isNotNull();
            assertThat(e.status()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }
    }
}
