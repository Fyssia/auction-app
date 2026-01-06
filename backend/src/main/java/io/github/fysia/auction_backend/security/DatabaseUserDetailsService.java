package io.github.fysia.auction_backend.security;

import io.github.fysia.auction_backend.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public DatabaseUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new AuthUser(
            user.getId(),
            user.getEmail(),
            user.getDisplayName(),
            user.getPasswordHash(),
            user.getRole().name()
        );
    }
}
