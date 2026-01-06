package io.github.fysia.auction_backend.security;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.fysia.auction_backend.entity.User;

import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    private final JwtProperties props;
    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    public JwtService(JwtProperties props) {
        this.props = props;
        this.algorithm = Algorithm.HMAC256(props.secret());
        this.verifier = JWT.require(algorithm)
            .withIssuer(props.issuer())
            .build();
    }

    public String createAccessToken(User user) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(props.accessTokenTtlMinutes() * 60);

        return JWT.create()
            .withIssuer(props.issuer())
            .withSubject(String.valueOf(user.getId()))
            .withClaim("email", user.getEmail())
            .withClaim("role", user.getRole().name())
            .withIssuedAt(Date.from(now))
            .withExpiresAt(Date.from(exp))
            .sign(algorithm);
    }

    public DecodedJWT verify(String token) {
        return verifier.verify(token);
    }
}
