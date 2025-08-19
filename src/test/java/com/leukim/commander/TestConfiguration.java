package com.leukim.commander;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.leukim.commander.clients")
public class TestConfiguration {
}
