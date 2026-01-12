package org.example.neosupply.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.neosupply.entity.RefreshToken;
import org.example.neosupply.repository.RefreshTokenRepository;
import org.example.neosupply.service.RefreshTokenService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService{

    private final RefreshTokenRepository repository;

    private final long REFRESH_EXPIRATION = 7 * 24 * 60 * 60;

    public RefreshToken create(String email) {

        RefreshToken token = new RefreshToken();
        token.setToken(UUID.randomUUID().toString());
        token.setEmail(email);
        token.setExpiryDate(Instant.now().plusSeconds(REFRESH_EXPIRATION));

        return repository.save(token);
    }

    public RefreshToken verify(String token) {
        RefreshToken refreshToken = repository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            repository.delete(refreshToken);
            throw new RuntimeException("Refresh token expired");
        }
        return refreshToken;
    }

    public void deleteByEmail(String email) {
        repository.deleteByEmail(email);
    }
}

