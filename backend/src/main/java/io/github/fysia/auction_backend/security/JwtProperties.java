package io.github.fysia.auction_backend.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.jwt")
public record JwtProperties(
    String issuer,
    String secret,
    long accessTokenTtlMinutes
) {}
