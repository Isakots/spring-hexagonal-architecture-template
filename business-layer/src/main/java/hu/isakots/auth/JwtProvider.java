package hu.isakots.auth;

import hu.isakots.auth.domain.AuthContext;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static hu.isakots.auth.domain.SecurityConstants.ACCESS_TOKEN_EXPIRATION_TIME;
import static hu.isakots.auth.domain.SecurityConstants.TOKEN_CUSTOM_PAYLOAD_KEY;

@Slf4j
@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String generateJwtToken(AuthContext authContext) {
        Map<String, Object> claims = assembleJwtPayload(authContext);

        return Jwts.builder()
            .setClaims(claims)
            .signWith(extractSecretSigningKey())
            .compact();
    }

    private Map<String, Object> assembleJwtPayload(AuthContext authContext) {
        Map<String, Object> claims = new HashMap<>();
        long expiration;
        claims.put("sub", authContext.userId());
        claims.put(TOKEN_CUSTOM_PAYLOAD_KEY, authContext);
        expiration = ACCESS_TOKEN_EXPIRATION_TIME;
        claims.put("iat", new Date());
        claims.put("exp", new Date(System.currentTimeMillis() + expiration));

        return claims;
    }

    public boolean validateJwtToken(String jwtToken) {
        try {
            Jwts.parserBuilder().setSigningKey(extractSecretSigningKey()).build().parseClaimsJws(jwtToken);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            log.error("Error occurred during jwt token validation: ", e);
        }

        return false;
    }

    public AuthContext getUserFromJwtToken(String token) {
        LinkedHashMap<String, Object> userData = (LinkedHashMap<String, Object>) Jwts.parserBuilder()
            .setSigningKey(extractSecretSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody().get(TOKEN_CUSTOM_PAYLOAD_KEY);
        // TODO it requires further generalization later
        return new AuthContext(((Integer) userData.get("userId")).longValue(), (String) userData.get("username"));
    }

    private Key extractSecretSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
}
