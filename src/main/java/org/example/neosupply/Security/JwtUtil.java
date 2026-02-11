package org.example.neosupply.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.example.neosupply.dto.response.UserDtoResponse;
import org.example.neosupply.entity.Users;
import org.example.neosupply.enumeration.Role;
import org.example.neosupply.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class JwtUtil {


    @Value("${jwt.secret-key}")
    private String secret_key;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secret_key.getBytes());
    }
    public  Claims exctractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public  String extractUsername(String token) {
        return exctractAllClaims(token).get("email", String.class);
    }

    public  Boolean isTokenExpired(String token) {
        return exctractAllClaims(token).getExpiration().before(new Date());
    }

    public String generateToken(String email, Set<Role> role,UserDtoResponse userDtoResponse) {
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",userDtoResponse.getId());
        claims.put("email",email);
        claims.put("role",role);

        long now = System.currentTimeMillis();
        long expirationTime = 1000 * 60 * 60;

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expirationTime))
                .signWith(getKey(),SignatureAlgorithm.HS256)
                .compact();


    }
}
