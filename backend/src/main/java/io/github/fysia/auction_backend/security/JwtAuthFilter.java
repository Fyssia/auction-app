package io.github.fysia.auction_backend.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final DatabaseUserDetailsService userDetailsService;

    public JwtAuthFilter(JwtService jwtService, DatabaseUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, java.io.IOException {

        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.substring("Bearer ".length()).trim();

        try {
            DecodedJWT jwt = jwtService.verify(token);
            String email = jwt.getClaim("email").asString();

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            var auth = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
            );
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (Exception ex) {
            // Если токен плохой — просто не аутентифицируем пользователя
            SecurityContextHolder.clearContext();
        }

        chain.doFilter(request, response);
    }
}
