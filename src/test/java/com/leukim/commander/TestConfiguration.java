package com.leukim.commander;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableFeignClients(basePackages = "com.leukim.commander.clients")
public class TestConfiguration {
    @Bean
    @Primary
    public static UserDetailsService userDetailsServiceTest(PasswordEncoder passwordEncoder) {
        UserDetails userDetails = User
            .withUsername("user")
            .password(passwordEncoder.encode("password"))
            .roles("USER")
            .build();

        return new InMemoryUserDetailsManager(userDetails);
    }
}
