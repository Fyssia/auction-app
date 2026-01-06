package io.github.fysia.auction_backend.dto.auth;

public record MeResponse(Long id, String email, String displayName, String role) {
}
