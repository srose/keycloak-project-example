package com.acme.backend.springboot.consent.web.consent;

import org.springframework.security.core.Authentication;

import java.util.Optional;

public class AuthenticationHelper {

    static String getName(Authentication authentication) {
        return Optional.ofNullable(authentication).map(a -> a.getName()).orElse("unknown");
    }
}