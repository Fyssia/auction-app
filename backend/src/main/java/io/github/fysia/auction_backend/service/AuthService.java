package io.github.fysia.auction_backend.service;

import io.github.fysia.auction_backend.dto.auth.*;
import io.github.fysia.auction_backend.entity.*;
import io.github.fysia.auction_backend.exception.EmailAlreadyUsedException;
import io.github.fysia.auction_backend.repository.UserRepository;
import io.github.fysia.auction_backend.security.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        AuthenticationManager authenticationManager,
        JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public MeResponse register(RegisterRequest req) {
        String email = req.email().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyUsedException("Email is already used");
        }

        String passwordHash = passwordEncoder.encode(req.password());

        User user = new User(email, passwordHash, req.displayName().trim(), UserRole.USER);
        User saved = userRepository.save(user);

        return new MeResponse(saved.getId(), saved.getEmail(), saved.getDisplayName(), saved.getRole().name());
    }

    public AuthTokenResponse login(LoginRequest req) {
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.email().trim().toLowerCase(), req.password())
        );

        AuthUser principal = (AuthUser) auth.getPrincipal();

        User user = userRepository.findByEmail(principal.getUsername())
            .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

        String token = jwtService.createAccessToken(user);
        return new AuthTokenResponse(token, "Bearer");
    }

    public MeResponse me(AuthUser user) {
        return new MeResponse(user.getId(), user.getUsername(), user.getDisplayName(), user.getRole());
    }
}
