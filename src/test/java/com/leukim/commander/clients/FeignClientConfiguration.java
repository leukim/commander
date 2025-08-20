package com.leukim.commander.clients;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;

public final class FeignClientConfiguration {
    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("user", "password");
    }
}
