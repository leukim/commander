package com.leukim.commander.clients;

import com.leukim.commander.infrastructure.controllers.model.CreateAdminUserRequest;
import com.leukim.commander.infrastructure.controllers.model.StatusDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(
    url = "${service.url:http://localhost:${local.server.port}}",
    name = "installationClient",
    configuration = FeignClientConfiguration.class
)
public interface InstallationClient {
    @RequestMapping(method = RequestMethod.GET, value = "/status")
    StatusDto getStatus();

    @RequestMapping(method = RequestMethod.POST, value = "/install")
    void install(CreateAdminUserRequest createAdminUserRequest);
}
